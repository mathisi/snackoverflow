package dhbw.ase.snackoverflow.domain.recipes;

import dhbw.ase.snackoverflow.domain.ingredients.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class ProcessStep {
    private List<Ingredient> ingredients;
    private String description;

    public ProcessStep(String description) {
        this.ingredients = new ArrayList<Ingredient>();
        this.description = description;
    }

    public ProcessStep(String description, List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        this.description = description;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ProcessStep{" +
                "ingredients=" + ingredients +
                ", description='" + description + '\'' +
                '}';
    }
}
