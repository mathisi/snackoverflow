package dhbw.ase.snackoverflow.application.usecases;

import dhbw.ase.snackoverflow.domain.entities.Recipe;
import dhbw.ase.snackoverflow.domain.entities.User;
import dhbw.ase.snackoverflow.domain.exceptions.RecipeNotFoundException;
import dhbw.ase.snackoverflow.domain.exceptions.UserNotFoundException;
import dhbw.ase.snackoverflow.domain.repositories.RecipeRepository;
import dhbw.ase.snackoverflow.domain.repositories.UserRepository;
import dhbw.ase.snackoverflow.domain.usecases.DuplicateRecipe;

public class DefaultDuplicateRecipe implements DuplicateRecipe {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    public DefaultDuplicateRecipe(RecipeRepository recipeRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Recipe duplicateRecipe(int recipeId, int userId) {
        Recipe recipe = this.recipeRepository.searchByID(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found"));
        recipe.setId(0);
        User user = this.userRepository.searchByID(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));
        recipe.setCreator(user);
        return this.recipeRepository.create(recipe);
    }

}
