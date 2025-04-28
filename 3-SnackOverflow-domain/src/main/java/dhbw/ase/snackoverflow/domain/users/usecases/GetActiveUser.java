package dhbw.ase.snackoverflow.domain.users.usecases;

import dhbw.ase.snackoverflow.domain.users.User;

public interface GetActiveUser {
    User getActiveUser() throws Exception;
}
