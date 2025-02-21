package dhbw.ase.snackoverflow.domain.usecases;

import dhbw.ase.snackoverflow.domain.entities.User;

public interface ChangeUserPassword {
    public User changePassword(int userId, String password);
}
