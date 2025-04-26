package dhbw.ase.snackoverflow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import dhbw.ase.snackoverflow.application.usecases.DefaultCreateRecipe;
import dhbw.ase.snackoverflow.domain.entities.ProcessStep;
import dhbw.ase.snackoverflow.domain.entities.Recipe;
import dhbw.ase.snackoverflow.domain.entities.User;
import dhbw.ase.snackoverflow.domain.repositories.RecipeRepository;
import dhbw.ase.snackoverflow.domain.repositories.UserRepository;
import dhbw.ase.snackoverflow.domain.valueobjects.EmailAddress;

public class DefaultCreateRecipeTest {

    private RecipeRepository recipeRepository;
    private UserRepository userRepository;
    private DefaultCreateRecipe defaultCreateRecipe;
    private User creator;

    @BeforeEach
    void setUp() {
        recipeRepository = Mockito.mock(RecipeRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        defaultCreateRecipe = new DefaultCreateRecipe(recipeRepository, userRepository);
        creator = new User(0, new EmailAddress("til@til.de"), "Til", "1234");
    }

    @Test
    void testCreateRecipeSuccessfully() {
        Recipe recipe = new Recipe(0, "Pasta", 4, 45, new ArrayList<ProcessStep>(), creator);
        Mockito.when(userRepository.searchByID(creator.getId())).thenReturn(Optional.of(creator));
        Mockito.when(recipeRepository.create(recipe)).thenReturn(recipe);

        Recipe createdRecipe = defaultCreateRecipe.createRecipe(recipe);

        Mockito.verify(userRepository).searchByID(creator.getId());
        Mockito.verify(recipeRepository).create(recipe);
        assertEquals(recipe, createdRecipe);
    }

    @Test
    void testCreateRecipeThrowsWhenCreatorNotFound() {
        Recipe recipe = new Recipe(0, "Pasta", 4, 45, new ArrayList<ProcessStep>(), creator);
        Mockito.when(userRepository.searchByID(creator.getId())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            defaultCreateRecipe.createRecipe(recipe);
        });
        assertEquals("Creator not found", exception.getMessage());
        Mockito.verify(userRepository).searchByID(creator.getId());
        Mockito.verify(recipeRepository, Mockito.never()).create(Mockito.any());
    }
}
