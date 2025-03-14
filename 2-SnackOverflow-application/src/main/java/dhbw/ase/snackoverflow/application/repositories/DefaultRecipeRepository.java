package dhbw.ase.snackoverflow.application.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import dhbw.ase.snackoverflow.domain.entities.Recipe;
import dhbw.ase.snackoverflow.domain.entities.User;
import dhbw.ase.snackoverflow.domain.exceptions.RecipeNotFoundException;
import dhbw.ase.snackoverflow.domain.repositories.RecipeRepository;

public class DefaultRecipeRepository implements RecipeRepository {
    private final Map<Integer, Recipe> recipes = new HashMap<>();
    private final AtomicInteger idHandler = new AtomicInteger(0);

    public DefaultRecipeRepository() {
        // to-do: add sample data?
    }

    @Override
    public Recipe create(Recipe recipe) {
        if (recipe.getId() == 0) {
            int id = idHandler.incrementAndGet();
            Recipe newRecipe = new Recipe(id, recipe.getName(), recipe.getPortions(), recipe.getPreparationTime(),
                    recipe.getProcessSteps(), recipe.getCreator());
            this.recipes.put(id, newRecipe);
            return newRecipe;
        } else {
            this.recipes.put(recipe.getId(), recipe);
            return recipe;
        }
    }

    @Override
    public Optional<Recipe> searchByID(int id) {
        return Optional.ofNullable(this.recipes.get(id));
    }

    @Override
    public List<Recipe> searchAll() {
        return new ArrayList<>(recipes.values());
    }

    @Override
    public void delete(int id) {
        Optional<Recipe> recipeToDelete = this.searchByID(id);
        if (recipeToDelete.isPresent()) {
            this.recipes.remove(id);
        } else {
            throw new RecipeNotFoundException("Recipe with id " + id + " not found");
        }
    }

    @Override
    public List<Recipe> findByUser(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByUser'");
    }

}
