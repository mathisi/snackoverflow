package dhbw.ase.snackoverflow.domain.recipes.usecases;

import dhbw.ase.snackoverflow.domain.recipes.Recipe;

public interface DuplicateRecipe {
    Recipe duplicateRecipe(int recipeId, int userId);
}
