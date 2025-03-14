package dhbw.ase.snackoverflow.application.usecases;

import java.util.List;
import java.util.Optional;

import dhbw.ase.snackoverflow.domain.entities.Ingredient;
import dhbw.ase.snackoverflow.domain.entities.ProcessStep;
import dhbw.ase.snackoverflow.domain.entities.Recipe;
import dhbw.ase.snackoverflow.domain.repositories.RecipeRepository;
import dhbw.ase.snackoverflow.domain.usecases.EditRecipe;

public class DefaultEditRecipe implements EditRecipe {

    private final RecipeRepository recipeRepository;

    public DefaultEditRecipe(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Recipe updateRecipeName(int recipeId, String newName) {
        Optional<Recipe> recipe = recipeRepository.searchByID(recipeId);
        if (!recipe.isPresent()) {
            throw new IllegalArgumentException("Recipe not found");
        }
        recipe.get().setName(newName);

        return recipe.get();
    }

    @Override
    public Recipe updateRecipeIngredients(int recipeId, List<Ingredient> newIngredients) {
        Optional<Recipe> recipe = recipeRepository.searchByID(recipeId);
        if (!recipe.isPresent()) {
            throw new IllegalArgumentException("Recipe not found");
        }
        recipe.get().setIngredients(newIngredients);

        return recipe.get();
    }

    @Override
    public Recipe updateRecipeInstructions(int recipeId, List<ProcessStep> newInstructions) {
        Optional<Recipe> recipe = recipeRepository.searchByID(recipeId);
        if (!recipe.isPresent()) {
            throw new IllegalArgumentException("Recipe not found");
        }
        recipe.get().setProcessSteps(newInstructions);

        return recipe.get();
    }

    @Override
    public Recipe updateRecipe(int recipeId, Recipe updatedRecipe) {
        Optional<Recipe> recipe = recipeRepository.searchByID(recipeId);
        if (!recipe.isPresent()) {
            throw new IllegalArgumentException("Recipe not found");
        }
        recipe.get().setName(updatedRecipe.getName());

        return recipe.get();
    }
}
