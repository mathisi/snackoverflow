package dhbw.ase.snackoverflow.application.usecases;

import dhbw.ase.snackoverflow.application.repositories.DefaultUserRepository;
import dhbw.ase.snackoverflow.domain.entities.ShoppingList;
import dhbw.ase.snackoverflow.domain.entities.User;
import dhbw.ase.snackoverflow.domain.exceptions.UserNotFoundException;
import dhbw.ase.snackoverflow.domain.usecases.GetShoppingList;

import java.util.Optional;

public class DefaultGetShoppingList implements GetShoppingList {
    private final DefaultUserRepository userRepository;

    public DefaultGetShoppingList(DefaultUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ShoppingList getShoppingList(int userId) {
        Optional<User> user = userRepository.searchByID(userId);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        return user.get().getShoppingList();
    }
}
