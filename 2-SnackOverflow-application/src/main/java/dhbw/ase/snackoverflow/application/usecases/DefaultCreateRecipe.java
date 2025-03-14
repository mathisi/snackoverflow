package dhbw.ase.snackoverflow.application.usecases;

import dhbw.ase.snackoverflow.domain.entities.Recipe;
import dhbw.ase.snackoverflow.domain.entities.User;
import dhbw.ase.snackoverflow.domain.exceptions.UserNotFoundException;
import dhbw.ase.snackoverflow.domain.repositories.RecipeRepository;
import dhbw.ase.snackoverflow.domain.repositories.UserRepository;
import dhbw.ase.snackoverflow.domain.usecases.CreateRecipe;

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
                .orElseThrow(() -> new UserNotFoundException("User with id " + creator.getId() + " not found"));
        user.addRecipe(recipe);
        return this.recipeRepository.create(recipe);
    }
}
