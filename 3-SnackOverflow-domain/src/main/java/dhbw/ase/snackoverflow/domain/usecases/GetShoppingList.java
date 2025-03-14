package dhbw.ase.snackoverflow.domain.usecases;

import dhbw.ase.snackoverflow.domain.entities.ShoppingList;

public interface GetShoppingList {
    ShoppingList getShoppingList(int userId);
}
