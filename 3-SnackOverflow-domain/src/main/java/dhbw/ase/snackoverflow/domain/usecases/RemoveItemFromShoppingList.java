package dhbw.ase.snackoverflow.domain.usecases;

import dhbw.ase.snackoverflow.domain.entities.Ingredient;
import dhbw.ase.snackoverflow.domain.entities.ShoppingList;

public interface RemoveItemFromShoppingList {

    ShoppingList removeItem(int userId, String name);
}
