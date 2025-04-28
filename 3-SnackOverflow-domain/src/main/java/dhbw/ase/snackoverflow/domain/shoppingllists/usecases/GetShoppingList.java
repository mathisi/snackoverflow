package dhbw.ase.snackoverflow.domain.shoppingllists.usecases;

import dhbw.ase.snackoverflow.domain.shoppingllists.ShoppingList;

public interface GetShoppingList {
    ShoppingList getShoppingList(int userId);
}
