package dhbw.ase.snackoverflow.application.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import dhbw.ase.snackoverflow.domain.ingredients.Ingredient;
import dhbw.ase.snackoverflow.domain.recipes.ProcessStep;
import dhbw.ase.snackoverflow.domain.recipes.Recipe;
import dhbw.ase.snackoverflow.domain.users.User;
import dhbw.ase.snackoverflow.domain.recipes.RecipeRepository;
import dhbw.ase.snackoverflow.domain.metrics.VolumeMetric;
import dhbw.ase.snackoverflow.domain.metrics.VolumeUnit;
import dhbw.ase.snackoverflow.domain.metrics.WeightMetric;
import dhbw.ase.snackoverflow.domain.metrics.WeightUnit;

public class DefaultRecipeRepository implements RecipeRepository {
    private final Map<Integer, Recipe> recipes = new HashMap<>();
    private final AtomicInteger idHandler = new AtomicInteger(0);

    public DefaultRecipeRepository() {
        this.mockRepository();
    }

    private void mockRepository() {
        this.create(new Recipe(0, "Spaghetti Bolognese", 4, 30,
                List.of(
                        new ProcessStep("Boil pasta", List.of(
                                new Ingredient(0, new WeightMetric(500, WeightUnit.GRAM), "Pasta", null),
                                new Ingredient(0, new VolumeMetric(1, VolumeUnit.TEASPOON), "Salt", null))),
                        new ProcessStep("Cook sauce", List.of(
                                new Ingredient(0, new VolumeMetric(200, VolumeUnit.MILLILITER), "Tomato sauce", null),
                                new Ingredient(0, new WeightMetric(300, WeightUnit.GRAM), "Ground beef", null),
                                new Ingredient(0, new VolumeMetric(1, VolumeUnit.PIECE), "Onion", null)))),
                new User.Builder().id(1).email(null).userName("Til").password(null).build()));
        this.create(new Recipe(0, "Caesar Salad", 2, 15,
                List.of(
                        new ProcessStep("Chop lettuce", List.of(
                                new Ingredient(0, new VolumeMetric(1, VolumeUnit.PIECE), "Lettuce", null))),
                        new ProcessStep("Mix dressing", List.of(
                                new Ingredient(0, new VolumeMetric(2, VolumeUnit.TABLESPOON), "Olive oil", null),
                                new Ingredient(0, new VolumeMetric(1, VolumeUnit.TABLESPOON), "Lemon juice", null),
                                new Ingredient(0, new WeightMetric(50, WeightUnit.GRAM), "Parmesan", null))),
                        new ProcessStep("Add croutons", List.of(
                                new Ingredient(0, new WeightMetric(100, WeightUnit.GRAM), "Croutons", null)))),
                new User.Builder().id(1).email(null).userName("Til").password(null).build()));
        this.create(new Recipe(0, "Pancakes", 3, 20,
                List.of(
                        new ProcessStep("Mix batter", List.of(
                                new Ingredient(0, new WeightMetric(200, WeightUnit.GRAM), "Flour", null),
                                new Ingredient(0, new VolumeMetric(300, VolumeUnit.MILLILITER), "Milk", null),
                                new Ingredient(0, new VolumeMetric(2, VolumeUnit.PIECE), "Eggs", null))),
                        new ProcessStep("Cook on skillet", List.of(
                                new Ingredient(0, new VolumeMetric(1, VolumeUnit.TABLESPOON), "Butter", null)))),
                new User.Builder().id(2).email(null).userName("Alex").password(null).build()));
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
            throw new IllegalArgumentException("Recipe with id " + id + " not found");
        }
    }

    @Override
    public List<Recipe> findByUser(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByUser'");
    }

}
