package dhbw.ase.snackoverflow.domain.recipes.usecases;

import dhbw.ase.snackoverflow.domain.ingredients.Ingredient;
import dhbw.ase.snackoverflow.domain.recipes.ProcessStep;
import dhbw.ase.snackoverflow.domain.recipes.Recipe;

import java.util.List;

public interface EditRecipe {
    Recipe updateRecipeName(int recipeId, String newName);

    Recipe updateRecipeIngredients(int recipeId, List<Ingredient> newIngredients);

    Recipe updateRecipeInstructions(int recipeId, List<ProcessStep> newInstructions);

    Recipe updateRecipe(int recipeId, Recipe updatedRecipe);
}
