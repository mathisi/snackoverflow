package dhbw.ase.snackoverflow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import dhbw.ase.snackoverflow.application.usecases.DefaultEditRecipe;
import dhbw.ase.snackoverflow.domain.ingredients.Ingredient;
import dhbw.ase.snackoverflow.domain.recipes.ProcessStep;
import dhbw.ase.snackoverflow.domain.recipes.Recipe;
import dhbw.ase.snackoverflow.domain.recipes.RecipeNotFoundException;
import dhbw.ase.snackoverflow.application.repositories.DefaultRecipeRepository;

public class DefaultEditRecipeTest {

    private DefaultRecipeRepository recipeRepository;
    private DefaultEditRecipe defaultEditRecipe;

    @BeforeEach
    void setUp() {
        recipeRepository = Mockito.mock(DefaultRecipeRepository.class);
        defaultEditRecipe = new DefaultEditRecipe(recipeRepository);
    }

    @Test
    void testUpdateRecipeNameSuccessfully() {
        Recipe recipe = new Recipe(1, "Old Name", 4, 30, new ArrayList<>(), null);
        Mockito.when(recipeRepository.searchByID(1)).thenReturn(Optional.of(recipe));

        Recipe updatedRecipe = defaultEditRecipe.updateRecipeName(1, "New Name");

        Mockito.verify(recipeRepository).searchByID(1);
        assertEquals("New Name", updatedRecipe.getName());
    }

    @Test
    void testUpdateRecipeNameThrowsWhenRecipeNotFound() {
        Mockito.when(recipeRepository.searchByID(1)).thenReturn(Optional.empty());

        RecipeNotFoundException exception = assertThrows(RecipeNotFoundException.class, () -> {
            defaultEditRecipe.updateRecipeName(1, "New Name");
        });

        assertEquals("Recipe not found", exception.getMessage());
        Mockito.verify(recipeRepository).searchByID(1);
    }

    @Test
    void testUpdateRecipeIngredientsSuccessfully() {
        Recipe recipe = new Recipe(1, "Recipe", 4, 30, new ArrayList<>(), null);
        List<Ingredient> newIngredients = List.of(new Ingredient(1, null, "Tomato", null));
        Mockito.when(recipeRepository.searchByID(1)).thenReturn(Optional.of(recipe));

        Recipe updatedRecipe = defaultEditRecipe.updateRecipeIngredients(1, newIngredients);

        Mockito.verify(recipeRepository).searchByID(1);
        assertEquals(newIngredients, updatedRecipe.getIngredients());
    }

    @Test
    void testUpdateRecipeInstructionsSuccessfully() {
        Recipe recipe = new Recipe(1, "Recipe", 4, 30, new ArrayList<>(), null);
        List<ProcessStep> newInstructions = List.of(new ProcessStep("Step 1", new ArrayList<>()));
        Mockito.when(recipeRepository.searchByID(1)).thenReturn(Optional.of(recipe));

        Recipe updatedRecipe = defaultEditRecipe.updateRecipeInstructions(1, newInstructions);

        Mockito.verify(recipeRepository).searchByID(1);
        assertEquals(newInstructions, updatedRecipe.getProcessSteps());
    }
}
