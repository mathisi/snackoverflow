package dhbw.ase.snackoverflow.domain.shoppingllists;

import dhbw.ase.snackoverflow.domain.ingredients.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class ShoppingList {
    private int id;
    private List<Ingredient> ingredients;

    public ShoppingList(int id) {
        this.id = id;
        this.ingredients = new ArrayList<Ingredient>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "ShoppingList{" +
                "id=" + id +
                ", ingredients=" + ingredients +
                '}';
    }
}
