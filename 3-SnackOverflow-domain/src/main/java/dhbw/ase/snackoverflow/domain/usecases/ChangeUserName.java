package dhbw.ase.snackoverflow.domain.usecases;

import dhbw.ase.snackoverflow.domain.entities.User;

public interface ChangeUserName {

    User changeName(int userId, String userName);
}
