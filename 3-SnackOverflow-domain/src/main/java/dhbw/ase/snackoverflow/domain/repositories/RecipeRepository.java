package dhbw.ase.snackoverflow.domain.repositories;

import java.util.List;
import java.util.Optional;

import dhbw.ase.snackoverflow.domain.entities.Recipe;
import dhbw.ase.snackoverflow.domain.entities.User;

public interface RecipeRepository {
    Recipe create(Recipe recipe);

    Optional<Recipe> searchByID(int id);

    List<Recipe> searchAll();

    void delete(int id);

    List<Recipe> findByUser(User user);
}
