package dhbw.ase.snackoverflow.domain.repositories;

import dhbw.ase.snackoverflow.domain.entities.Recipe;
import dhbw.ase.snackoverflow.domain.entities.ShoppingList;
import dhbw.ase.snackoverflow.domain.entities.User;

import java.util.Optional;
import java.util.List;

public interface UserRepository {

    User create(User user);
    Optional<User> searchByID(int id);
    List<User> searchAll();
    void delete(int id);
    User findByRecipe(Recipe recipe);
}