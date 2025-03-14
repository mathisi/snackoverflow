package dhbw.ase.snackoverflow.application.usecases;

import dhbw.ase.snackoverflow.application.repositories.DefaultUserRepository;
import dhbw.ase.snackoverflow.domain.entities.Ingredient;
import dhbw.ase.snackoverflow.domain.entities.ShoppingList;
import dhbw.ase.snackoverflow.domain.entities.User;
import dhbw.ase.snackoverflow.domain.exceptions.IngredientNotFoundException;
import dhbw.ase.snackoverflow.domain.exceptions.UserNotFoundException;
import dhbw.ase.snackoverflow.domain.usecases.RemoveItemFromShoppingList;

import java.util.List;
import java.util.Optional;

public class DefaultRemoveItemFromShoppingList implements RemoveItemFromShoppingList {

    private final DefaultUserRepository userRepository;

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
