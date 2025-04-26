package dhbw.ase.snackoverflow.domain.exceptions;

public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException() {
        super("Recipe not found");
    }
}
