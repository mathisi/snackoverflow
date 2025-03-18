package dhbw.ase.snackoverflow.domain.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
