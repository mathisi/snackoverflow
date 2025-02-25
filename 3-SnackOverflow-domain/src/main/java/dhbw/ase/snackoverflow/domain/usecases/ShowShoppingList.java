package dhbw.ase.snackoverflow.domain.usecases;

import dhbw.ase.snackoverflow.domain.entities.ShoppingList;

public interface ShowShoppingList {
    ShoppingList showShoppingList(int userId);
}
