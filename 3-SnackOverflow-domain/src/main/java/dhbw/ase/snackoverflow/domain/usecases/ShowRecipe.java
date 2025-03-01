package dhbw.ase.snackoverflow.domain.usecases;

import dhbw.ase.snackoverflow.domain.entities.Recipe;

public interface ShowRecipe {
    Recipe showRecipe(int recipeId);
}
