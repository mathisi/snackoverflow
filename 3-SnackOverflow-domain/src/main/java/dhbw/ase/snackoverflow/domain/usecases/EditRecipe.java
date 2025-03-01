package dhbw.ase.snackoverflow.domain.usecases;

import dhbw.ase.snackoverflow.domain.entities.Recipe;
import dhbw.ase.snackoverflow.domain.entities.ProcessStep;
import dhbw.ase.snackoverflow.domain.entities.Ingredient;
import java.util.List;

public interface EditRecipe {
    Recipe updateRecipeName(int recipeId, String newName);

    Recipe updateRecipeIngredients(int recipeId, List<Ingredient> newIngredients);

    Recipe updateRecipeInstructions(int recipeId, List<ProcessStep> newInstructions);

    Recipe updateRecipe(int recipeId, Recipe updatedRecipe);
}
