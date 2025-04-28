package dhbw.ase.snackoverflow.application.usecases;

import dhbw.ase.snackoverflow.application.repositories.DefaultUserRepository;
import dhbw.ase.snackoverflow.domain.shoppingllists.ShoppingList;
import dhbw.ase.snackoverflow.domain.users.User;
import dhbw.ase.snackoverflow.domain.shoppingllists.usecases.GetShoppingList;

import java.util.Optional;

public class DefaultGetShoppingList implements GetShoppingList {
    private final DefaultUserRepository userRepository;

    public DefaultGetShoppingList(DefaultUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ShoppingList getShoppingList(int userId) {
        Optional<User> user = userRepository.searchByID(userId);
        if(!user.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }
        return user.get().getShoppingList();
    }
}
