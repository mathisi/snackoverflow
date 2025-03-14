package dhbw.ase.snackoverflow.domain.exceptions;

public class NoActiveUserException extends RuntimeException{
    public NoActiveUserException() {
        super("No active user logged in");
    }
}
