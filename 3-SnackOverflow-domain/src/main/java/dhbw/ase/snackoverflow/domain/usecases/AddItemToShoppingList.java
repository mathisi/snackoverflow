package dhbw.ase.snackoverflow.domain.usecases;

import dhbw.ase.snackoverflow.domain.entities.Ingredient;
import dhbw.ase.snackoverflow.domain.entities.ShoppingList;

public interface AddItemToShoppingList {
    ShoppingList addItem(int userId, Ingredient ingredient);
}
