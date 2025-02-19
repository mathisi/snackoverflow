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
}
