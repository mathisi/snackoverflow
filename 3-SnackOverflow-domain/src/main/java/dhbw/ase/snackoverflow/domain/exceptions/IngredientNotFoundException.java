package dhbw.ase.snackoverflow.domain.exceptions;

public class IngredientNotFoundException extends RuntimeException{
    public IngredientNotFoundException(String msg) {
        super(msg);
    }
}
