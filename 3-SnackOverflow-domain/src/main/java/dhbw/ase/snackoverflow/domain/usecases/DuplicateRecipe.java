package dhbw.ase.snackoverflow.domain.usecases;

import dhbw.ase.snackoverflow.domain.entities.Recipe;

public interface DuplicateRecipe {
    Recipe duplicateRecipe(int recipeId, int userId);
}
