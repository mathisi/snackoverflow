package dhbw.ase.snackoverflow.application.shoppinglists.usecases;

import dhbw.ase.snackoverflow.application.users.DefaultUserRepository;
import dhbw.ase.snackoverflow.domain.ingredients.Ingredient;
import dhbw.ase.snackoverflow.domain.shoppingllists.ShoppingList;
import dhbw.ase.snackoverflow.domain.users.User;
import dhbw.ase.snackoverflow.domain.ingredients.IngredientNotFoundException;
import dhbw.ase.snackoverflow.domain.users.UserNotFoundException;
import dhbw.ase.snackoverflow.domain.shoppingllists.usecases.RemoveItemFromShoppingList;
import dhbw.ase.snackoverflow.domain.users.UserRepository;

import java.util.List;
import java.util.Optional;

public class DefaultRemoveItemFromShoppingList implements RemoveItemFromShoppingList {

    private final UserRepository userRepository;

    public DefaultRemoveItemFromShoppingList(DefaultUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ShoppingList removeItem(int userId, String name) {
        Optional<User> user = userRepository.searchByID(userId);
        if(!user.isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        ShoppingList shoppingList = user.get().getShoppingList();
        List<Ingredient> ingredientList = shoppingList.getIngredients();
        boolean removed = ingredientList.removeIf(ingredient -> ingredient.getName().equals(name));
        if(!removed) {
            throw new IngredientNotFoundException("Ingredient " + name + " not found!");
        }
        shoppingList.setIngredients(ingredientList);

        return shoppingList;

    }
}
