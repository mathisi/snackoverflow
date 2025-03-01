package dhbw.ase.snackoverflow.domain.usecases;

import dhbw.ase.snackoverflow.domain.entities.Recipe;
import dhbw.ase.snackoverflow.domain.entities.ProcessStep;
import dhbw.ase.snackoverflow.domain.entities.Ingredient;
import java.util.List;

public interface EditRecipe {
    void updateRecipeName(Long recipeId, String newName);

    void updateRecipeIngredients(Long recipeId, List<Ingredient> newIngredients);

    void updateRecipeInstructions(Long recipeId, List<ProcessStep> newInstructions);

    void updateRecipe(Long recipeId, Recipe updatedRecipe);
}
