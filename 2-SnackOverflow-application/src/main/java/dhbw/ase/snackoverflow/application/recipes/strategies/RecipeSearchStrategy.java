package dhbw.ase.snackoverflow.application.recipes.strategies;

import dhbw.ase.snackoverflow.domain.recipes.Recipe;

import java.util.List;

public interface RecipeSearchStrategy {
    List<Recipe> search(RecipeSearchContext context);
}