package dhbw.ase.snackoverflow.domain.users.usecases;

import dhbw.ase.snackoverflow.domain.users.User;

public interface ChangeUserPassword {
    User changePassword(int userId, String password);
}
