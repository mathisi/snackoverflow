package dhbw.ase.snackoverflow.application.strategies;

import dhbw.ase.snackoverflow.domain.entities.Recipe;
import dhbw.ase.snackoverflow.domain.repositories.RecipeRepository;

import java.util.List;

public class SearchByNameStrategy implements RecipeSearchStrategy {

    private final RecipeRepository recipeRepository;

    public SearchByNameStrategy(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> search(RecipeSearchContext context) {
        String name = context.getName();
        String lowerCaseName = name.toLowerCase();
        List<Recipe> allRecipes = recipeRepository.searchAll();
        List<Recipe> matchingRecipes = allRecipes.stream()
                .filter(recipe -> recipe.getName().toLowerCase().contains(lowerCaseName))
                .toList();
        if (matchingRecipes.isEmpty()) {
            throw new IllegalArgumentException("No recipe found containing name " + name);
        }
        return matchingRecipes;
    }
}
