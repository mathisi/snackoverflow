package dhbw.ase.snackoverflow.application.usecases;

import dhbw.ase.snackoverflow.domain.entities.User;
import dhbw.ase.snackoverflow.domain.exceptions.NoActiveUserException;
import dhbw.ase.snackoverflow.domain.repositories.UserRepository;
import dhbw.ase.snackoverflow.domain.usecases.GetActiveUser;

public class DefaultGetActiveUser implements GetActiveUser {

    private final UserRepository userRepository;

    public DefaultGetActiveUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getActiveUser() throws NoActiveUserException {
        User activeUser = this.userRepository.getActiveUser();
        if(activeUser == null) {
            throw new NoActiveUserException();
        }

        return activeUser;
    }
}
