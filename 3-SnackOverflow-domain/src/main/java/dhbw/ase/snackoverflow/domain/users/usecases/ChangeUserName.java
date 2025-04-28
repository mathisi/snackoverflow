package dhbw.ase.snackoverflow.domain.users.usecases;

import dhbw.ase.snackoverflow.domain.users.User;

public interface ChangeUserName {

    User changeName(int userId, String userName);
}
