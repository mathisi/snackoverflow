package dhbw.ase.snackoverflow.domain.shoppingllists.usecases;

import dhbw.ase.snackoverflow.domain.ingredients.Ingredient;
import dhbw.ase.snackoverflow.domain.shoppingllists.ShoppingList;

public interface AddItemToShoppingList {
    ShoppingList addItem(int userId, Ingredient ingredient);
}
