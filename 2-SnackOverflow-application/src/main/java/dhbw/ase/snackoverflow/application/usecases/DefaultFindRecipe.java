package dhbw.ase.snackoverflow.application.usecases;

import java.util.List;
import java.util.Optional;

import dhbw.ase.snackoverflow.domain.entities.Ingredient;
import dhbw.ase.snackoverflow.domain.entities.Recipe;
import dhbw.ase.snackoverflow.domain.entities.User;
import dhbw.ase.snackoverflow.domain.exceptions.RecipeNotFoundException;
import dhbw.ase.snackoverflow.domain.exceptions.UserNotFoundException;
import dhbw.ase.snackoverflow.domain.repositories.RecipeRepository;
import dhbw.ase.snackoverflow.domain.repositories.UserRepository;
import dhbw.ase.snackoverflow.domain.usecases.FindRecipe;

public class DefaultFindRecipe implements FindRecipe {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    public DefaultFindRecipe(RecipeRepository recipeRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Recipe> findByName(String name) {
        List<Recipe> allRecipes = recipeRepository.searchAll();
        List<Recipe> matchingRecipes = allRecipes.stream().filter(recipe -> recipe.getName().equals(name)).toList();
        if (matchingRecipes.isEmpty()) {
            throw new RecipeNotFoundException("No recipe found with name " + name);
        }
        return matchingRecipes;
    }

    @Override
    public List<Recipe> findByIngredients(List<Ingredient> ingredients) {
        List<Recipe> allRecipes = recipeRepository.searchAll();
        List<Recipe> matchingRecipes = allRecipes.stream()
                .filter(recipe -> recipe.getIngredients().containsAll(ingredients)).toList();
        if (matchingRecipes.isEmpty()) {
            throw new RecipeNotFoundException("No recipe found with ingredients " + ingredients);
        }
        return matchingRecipes;
    }

    @Override
    public List<Recipe> findByUser(User user) {
        Optional<User> foundUser = userRepository.searchByID(user.getId());
        if (!foundUser.isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        List<Recipe> userRecipes = foundUser.get().getRecipes();
        if (userRecipes.isEmpty()) {
            throw new RecipeNotFoundException("No recipes found for user " + user);
        }
        return userRecipes;
    }
}
