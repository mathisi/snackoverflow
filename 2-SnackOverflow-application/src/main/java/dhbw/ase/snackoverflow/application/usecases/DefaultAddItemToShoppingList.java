package dhbw.ase.snackoverflow.application.usecases;

import dhbw.ase.snackoverflow.domain.ingredients.Ingredient;
import dhbw.ase.snackoverflow.domain.shoppingllists.ShoppingList;
import dhbw.ase.snackoverflow.domain.users.User;
import dhbw.ase.snackoverflow.domain.users.UserRepository;
import dhbw.ase.snackoverflow.domain.shoppingllists.usecases.AddItemToShoppingList;

import java.util.Optional;

public class DefaultAddItemToShoppingList implements AddItemToShoppingList {

    private final UserRepository userRepository;

    public DefaultAddItemToShoppingList(UserRepository userRepository) {
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
