package dhbw.ase.snackoverflow.application.strategies;

import dhbw.ase.snackoverflow.domain.entities.Recipe;

import java.util.List;

public interface RecipeSearchStrategy {
    List<Recipe> search(RecipeSearchContext context);
}