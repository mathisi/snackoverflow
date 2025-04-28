package dhbw.ase.snackoverflow.domain.recipes;

import java.util.List;
import java.util.Optional;

import dhbw.ase.snackoverflow.domain.users.User;

public interface RecipeRepository {
    Recipe create(Recipe recipe);

    Optional<Recipe> searchByID(int id);

    List<Recipe> searchAll();

    void delete(int id);

    List<Recipe> findByUser(User user);
}
