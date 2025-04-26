package dhbw.ase.snackoverflow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import dhbw.ase.snackoverflow.application.usecases.DefaultFindRecipe;
import dhbw.ase.snackoverflow.domain.entities.Ingredient;
import dhbw.ase.snackoverflow.domain.entities.ProcessStep;
import dhbw.ase.snackoverflow.domain.entities.Recipe;
import dhbw.ase.snackoverflow.domain.entities.User;
import dhbw.ase.snackoverflow.domain.repositories.RecipeRepository;
import dhbw.ase.snackoverflow.domain.repositories.UserRepository;

public class DefaultFindRecipeTest {

    private RecipeRepository recipeRepository;
    private UserRepository userRepository;
    private DefaultFindRecipe defaultFindRecipe;

    @BeforeEach
    public void setUp() {
        recipeRepository = Mockito.mock(RecipeRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        defaultFindRecipe = new DefaultFindRecipe(recipeRepository, userRepository);
    }

    @Test
    void testFindByNameSuccessfully() {
        Recipe recipe = new Recipe(1, "Pasta", 4, 30, new ArrayList<>(), null);
        Mockito.when(recipeRepository.searchAll()).thenReturn(List.of(recipe));

        List<Recipe> result = defaultFindRecipe.findByName("Pasta");

        assertEquals(1, result.size());
        assertEquals("Pasta", result.get(0).getName());
    }

    @Test
    void testFindByNameThrowsWhenNoMatch() {
        Mockito.when(recipeRepository.searchAll()).thenReturn(new ArrayList<>());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            defaultFindRecipe.findByName("Pizza");
        });

        assertEquals("No recipe found containing name Pizza", exception.getMessage());
    }

    @Test
    void testFindByIngredientsSuccessfully() {
        Ingredient tomato = new Ingredient(1, null, "Tomato", null);
        ProcessStep prepareSalad = new ProcessStep("Chop Tomato", List.of(tomato));
        Recipe recipe = new Recipe(1, "Salad", 2, 10, List.of(prepareSalad), null);
        Mockito.when(recipeRepository.searchAll()).thenReturn(List.of(recipe));

        List<Recipe> result = defaultFindRecipe.findByIngredients(List.of(tomato));

        assertEquals(1, result.size());
        assertEquals("Salad", result.get(0).getName());
    }

    @Test
    void testFindByIngredientsThrowsWhenNoMatch() {
        Mockito.when(recipeRepository.searchAll()).thenReturn(new ArrayList<>());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            defaultFindRecipe.findByIngredients(List.of(new Ingredient(1, null, "Tomato", null)));
        });

        assertEquals("No recipe found with ingredients [Ingredient{id=1, metric=null, name='Tomato'}]",
                exception.getMessage());
    }

    @Test
    void testFindByUserSuccessfully() {
        User user = new User(1, null, "John", "password");
        Recipe recipe = new Recipe(1, "Soup", 1, 15, new ArrayList<>(), user);
        user.addRecipe(recipe);
        Mockito.when(userRepository.searchByID(1)).thenReturn(Optional.of(user));

        List<Recipe> result = defaultFindRecipe.findByUser(user);

        assertEquals(1, result.size());
        assertEquals("Soup", result.get(0).getName());
    }

    @Test
    void testFindByUserThrowsWhenUserNotFound() {
        User user = new User(1, null, "John", "password");
        Mockito.when(userRepository.searchByID(1)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            defaultFindRecipe.findByUser(user);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testFindByUserThrowsWhenNoRecipes() {
        User user = new User(1, null, "John", "password");
        Mockito.when(userRepository.searchByID(1)).thenReturn(Optional.of(user));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            defaultFindRecipe.findByUser(user);
        });

        assertEquals(
                "No recipes found for user User{id=1, email=null, userName='John', password='password', recipes=[], shoppingList='ShoppingList{id=0, ingredients=[]}'}",
                exception.getMessage());
    }
}
