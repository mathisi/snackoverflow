package dhbw.ase.snackoverflow.application.recipes.usecases;

import dhbw.ase.snackoverflow.domain.recipes.Recipe;
import dhbw.ase.snackoverflow.domain.users.User;
import dhbw.ase.snackoverflow.domain.recipes.RecipeRepository;
import dhbw.ase.snackoverflow.domain.users.UserRepository;
import dhbw.ase.snackoverflow.domain.recipes.usecases.CreateRecipe;

public class DefaultCreateRecipe implements CreateRecipe {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    public DefaultCreateRecipe(RecipeRepository recipeRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Recipe createRecipe(Recipe recipe) {
        final User creator = recipe.getCreator();
        final User user = this.userRepository.searchByID(creator.getId())
                .orElseThrow(() -> new IllegalArgumentException("Creator not found"));
        user.addRecipe(recipe);
        return this.recipeRepository.create(recipe);
    }
}
