package dhbw.ase.snackoverflow.application.usecases;

import dhbw.ase.snackoverflow.application.repositories.DefaultUserRepository;
import dhbw.ase.snackoverflow.domain.entities.Ingredient;
import dhbw.ase.snackoverflow.domain.entities.ShoppingList;
import dhbw.ase.snackoverflow.domain.entities.User;
import dhbw.ase.snackoverflow.domain.usecases.AddItemToShoppingList;

import java.util.List;
import java.util.Optional;

public class DefaultAddItemToShoppingList implements AddItemToShoppingList {

    private final DefaultUserRepository userRepository;

    public DefaultAddItemToShoppingList(DefaultUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ShoppingList addItem(int userId, Ingredient ingredient) {
        Optional<User> user = userRepository.searchByID(userId);
        if(!user.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }
        user.get().getShoppingList().getIngredients().add(ingredient);
        return user.get().getShoppingList();
    }
}
