package dhbw.ase.snackoverflow.domain.entities;

/**
 * Recipe
 */
public class Recipe {
    private int id;
    private String name;
    private int portions;
    private int preparationTime;
    private Ingredient[] ingredients;
    private ProcessStep[] processSteps;

    public Recipe(int id, String name, int portions, int preparationTime, Ingredient[] ingredients,
            ProcessStep[] processSteps) {
        this.id = id;
        this.name = name;
        this.portions = portions;
        this.preparationTime = preparationTime;
        this.ingredients = ingredients;
        this.processSteps = processSteps;
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

    public Ingredient[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredient[] ingredients) {
        this.ingredients = ingredients;
    }

    public ProcessStep[] getProcessSteps() {
        return processSteps;
    }

    public void setProcessSteps(ProcessStep[] processSteps) {
        this.processSteps = processSteps;
    }

    @Override
    public String toString() {
        return "Recipe [id=" + id + ", ingredients=" + ingredients + ", name=" + name + ", portions=" + portions
                + ", preparationTime=" + preparationTime + ", processSteps=" + processSteps + "]";
    }

}
