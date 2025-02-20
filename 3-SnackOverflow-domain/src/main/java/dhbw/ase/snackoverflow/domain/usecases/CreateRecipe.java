package dhbw.ase.snackoverflow.domain.usecases;

import dhbw.ase.snackoverflow.domain.entities.Recipe;

public interface CreateRecipe {
    Recipe createRecipe(Recipe recipe);

}