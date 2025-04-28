package dhbw.ase.snackoverflow.domain.repositories;

import dhbw.ase.snackoverflow.domain.entities.Recipe;
import dhbw.ase.snackoverflow.domain.entities.ShoppingList;
import dhbw.ase.snackoverflow.domain.entities.User;
import dhbw.ase.snackoverflow.domain.valueobjects.EmailAddress;

import java.util.Optional;
import java.util.List;

public interface UserRepository {

    User create(User user);
    Optional<User> searchByID(int id);
    Optional<User> searchByMail(EmailAddress emailAddress);

    User getActiveUser();
    User setActiveUser(User newActiveUser) throws Exception;

    void logoutActiveUser();
}