package dhbw.ase.snackoverflow.domain.recipes;

import dhbw.ase.snackoverflow.domain.users.User;
import dhbw.ase.snackoverflow.domain.ingredients.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private int id;
    private String name;
    private int portions;
    private int preparationTime;
    private List<Ingredient> ingredients;
    private List<ProcessStep> processSteps;
    private User creator;

    public Recipe(int id, String name, int portions, int preparationTime,
            List<ProcessStep> processSteps, User creator) {
        this.id = id;
        this.name = name;
        this.portions = portions;
        this.preparationTime = preparationTime;
        this.ingredients = new ArrayList<Ingredient>();
        this.processSteps = processSteps;
        this.creator = creator;

        for (ProcessStep step : processSteps) {
            for (Ingredient ingredient : step.getIngredients()) {
                if (!ingredients.contains(ingredient)) {
                    ingredients.add(ingredient);
                }
            }
        }
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPortions() {
        return portions;
    }

    public void setPortions(int portions) {
        this.portions = portions;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<ProcessStep> getProcessSteps() {
        return processSteps;
    }

    public void setProcessSteps(List<ProcessStep> processSteps) {
        this.processSteps = processSteps;
    }

    @Override
    public String toString() {
        return "Recipe [id=" + id + ", ingredients=" + ingredients + ", name=" + name + ", portions=" + portions
                + ", preparationTime=" + preparationTime + ", processSteps=" + processSteps + "]";
    }

}
