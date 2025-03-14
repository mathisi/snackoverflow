package dhbw.ase.snackoverflow.domain.usecases;

import dhbw.ase.snackoverflow.domain.entities.User;
import dhbw.ase.snackoverflow.domain.valueobjects.EmailAddress;

public interface LoginUser {
    User login(EmailAddress email, String password) throws Exception;
}
