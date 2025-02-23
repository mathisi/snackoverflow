package dhbw.ase.snackoverflow.application.usecases;

import dhbw.ase.snackoverflow.application.repositories.DefaultUserRepository;
import dhbw.ase.snackoverflow.domain.entities.ShoppingList;
import dhbw.ase.snackoverflow.domain.entities.User;
import dhbw.ase.snackoverflow.domain.usecases.ShowShoppingList;

import java.util.Optional;

public class DefaultShowShoppingList implements ShowShoppingList {
    private final DefaultUserRepository userRepository;

    public DefaultShowShoppingList(DefaultUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ShoppingList showShoppingList(int userId) {
        Optional<User> user = userRepository.searchByID(userId);
        if(!user.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }
        return user.get().getShoppingList();
    }
}
