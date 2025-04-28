package dhbw.ase.snackoverflow.domain.users.usecases;

import dhbw.ase.snackoverflow.domain.users.EmailAddress;
import dhbw.ase.snackoverflow.domain.users.User;

public interface LoginUser {
    User login(EmailAddress email, String password) throws Exception;
}
