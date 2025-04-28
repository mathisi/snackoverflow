package dhbw.ase.snackoverflow.application.strategies;

import dhbw.ase.snackoverflow.domain.ingredients.Ingredient;
import dhbw.ase.snackoverflow.domain.recipes.Recipe;
import dhbw.ase.snackoverflow.domain.recipes.RecipeRepository;

import java.util.List;
import java.util.stream.Collectors;

public class SearchByIngredientsStrategy implements RecipeSearchStrategy {

    private final RecipeRepository recipeRepository;

    public SearchByIngredientsStrategy(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> search(RecipeSearchContext context) {
        List<Ingredient> ingredients = context.getIngredients();
        List<Recipe> allRecipes = recipeRepository.searchAll();
        final List<String> ingredientNames = ingredients.stream()
                .map(Ingredient::getName)
                .toList();
        List<Recipe> matchingRecipes = allRecipes.stream()
                .filter(recipe -> recipe.getIngredients().stream()
                        .map(Ingredient::getName)
                        .collect(Collectors.toList())
                        .containsAll(ingredientNames))
                .toList();
        if (matchingRecipes.isEmpty()) {
            throw new IllegalArgumentException("No recipe found with ingredients " + ingredients);
        }
        return matchingRecipes;
    }
}
