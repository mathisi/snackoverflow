package dhbw.ase.snackoverflow.domain.ingredients;

public class IngredientNotFoundException extends RuntimeException{
    public IngredientNotFoundException(String msg) {
        super(msg);
    }
}
