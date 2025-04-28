package dhbw.ase.snackoverflow.domain.shoppingllists.usecases;

import dhbw.ase.snackoverflow.domain.shoppingllists.ShoppingList;

public interface RemoveItemFromShoppingList {

    ShoppingList removeItem(int userId, String name);
}
