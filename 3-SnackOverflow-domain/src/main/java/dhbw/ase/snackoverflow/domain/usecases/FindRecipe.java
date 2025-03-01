package dhbw.ase.snackoverflow.domain.usecases;

import dhbw.ase.snackoverflow.domain.entities.Ingredient;
import dhbw.ase.snackoverflow.domain.entities.Recipe;
import dhbw.ase.snackoverflow.domain.entities.User;

import java.util.List;

public interface FindRecipe {
    List<Recipe> findByName(String name);

    List<Recipe> findByIngredients(List<Ingredient> ingredients);

    List<Recipe> findByUser(User user);
}
