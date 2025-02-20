package dhbw.ase.snackoverflow.domain.entities;

import java.util.Arrays;

public class ProcessStep {
    private Ingredient[] ingredients;
    private String description;

    public ProcessStep(Ingredient[] ingredients, String description) {
        this.ingredients = ingredients;
        this.description = description;
    }

    public Ingredient[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredient[] ingredients) {
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
                "ingredients=" + Arrays.toString(ingredients) +
                ", description='" + description + '\'' +
                '}';
    }
}
