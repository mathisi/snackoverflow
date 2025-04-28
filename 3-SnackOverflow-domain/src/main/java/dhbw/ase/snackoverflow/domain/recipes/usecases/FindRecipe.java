package dhbw.ase.snackoverflow.domain.recipes.usecases;

import dhbw.ase.snackoverflow.domain.ingredients.Ingredient;
import dhbw.ase.snackoverflow.domain.recipes.Recipe;
import dhbw.ase.snackoverflow.domain.users.User;

import java.util.List;

public interface FindRecipe {
    List<Recipe> findByName(String name);

    List<Recipe> findByIngredients(List<Ingredient> ingredients);

    List<Recipe> findByUser(User user);
}
