package dhbw.ase.snackoverflow.application.usecases;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import dhbw.ase.snackoverflow.domain.entities.Ingredient;
import dhbw.ase.snackoverflow.domain.entities.Recipe;
import dhbw.ase.snackoverflow.domain.entities.User;
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

    @Override
    public List<Recipe> findByIngredients(List<Ingredient> ingredients) {
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

    @Override
    public List<Recipe> findByUser(User user) {
        Optional<User> foundUser = userRepository.searchByID(user.getId());
        if (!foundUser.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }
        List<Recipe> userRecipes = foundUser.get().getRecipes();
        if (userRecipes.isEmpty()) {
            throw new IllegalArgumentException("No recipes found for user " + user);
        }
        return userRecipes;
    }
}
