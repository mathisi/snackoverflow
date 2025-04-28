package dhbw.ase.snackoverflow.domain.users;

public class InvalidEmailAddressException extends RuntimeException{
    public InvalidEmailAddressException(String email) {
        super("Email adress " + email + " is not a valid email address");
    }
}
