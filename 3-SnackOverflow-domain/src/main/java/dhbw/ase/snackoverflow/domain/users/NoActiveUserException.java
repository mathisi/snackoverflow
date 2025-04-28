package dhbw.ase.snackoverflow.domain.users;

public class NoActiveUserException extends RuntimeException{
    public NoActiveUserException() {
        super("No active user logged in");
    }
}
