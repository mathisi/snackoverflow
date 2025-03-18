package dhbw.ase.snackoverflow.domain.usecases;

import dhbw.ase.snackoverflow.domain.entities.User;

public interface GetActiveUser {
    User getActiveUser() throws Exception;
}
