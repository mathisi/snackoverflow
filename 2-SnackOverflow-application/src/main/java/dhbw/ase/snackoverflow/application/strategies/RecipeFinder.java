package dhbw.ase.snackoverflow.application.strategies;

import dhbw.ase.snackoverflow.domain.entities.Recipe;
import dhbw.ase.snackoverflow.domain.repositories.RecipeRepository;
import dhbw.ase.snackoverflow.domain.repositories.UserRepository;


import java.util.List;

public class RecipeFinder {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private RecipeSearchStrategy strategy;

    public RecipeFinder(RecipeSearchStrategy strategy, RecipeRepository recipeRepository, UserRepository userRepository) {
        this.strategy = strategy;
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    public void setStrategy(RecipeSearchStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Recipe> find(RecipeSearchContext context) {
        return strategy.search(context);
    }
    public RecipeRepository getRecipeRepository() {
        return this.recipeRepository;
    }

    public UserRepository getUserRepository() {
        return this.userRepository;
    }
}
