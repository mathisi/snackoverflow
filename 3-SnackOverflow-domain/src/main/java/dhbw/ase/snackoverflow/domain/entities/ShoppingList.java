package dhbw.ase.snackoverflow.domain.entities;

public class ShoppingList {
    private int id;
    private Ingredient[] ingredients;

    public ShoppingList(int id, Ingredient[] ingredients) {
        this.id = id;
        this.ingredients = ingredients;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ingredient[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredient[] ingredients) {
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
