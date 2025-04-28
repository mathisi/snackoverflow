package dhbw.ase.snackoverflow.domain.recipes.usecases;

import dhbw.ase.snackoverflow.domain.recipes.Recipe;

public interface CreateRecipe {
    Recipe createRecipe(Recipe recipe);

}