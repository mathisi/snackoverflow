package dhbw.ase.snackoverflow.domain.users;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException(String email) {
        super("Password for user " + email + " invalid");
    }
}
