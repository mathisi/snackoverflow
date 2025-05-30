package dhbw.ase.snackoverflow.application.recipes.strategies;

import dhbw.ase.snackoverflow.domain.ingredients.Ingredient;
import dhbw.ase.snackoverflow.domain.users.User;

import java.util.List;

public class RecipeSearchContext {
    private String name;
    private List<Ingredient> ingredients;
    private User user;

    public RecipeSearchContext name(String name) {
        this.name = name;
        return this;
    }

    public RecipeSearchContext ingredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public RecipeSearchContext user(User user) {
        this.user = user;
        return this;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public User getUser() {
        return user;
    }
}
