package dhbw.ase.snackoverflow.application.users.usecases;

import dhbw.ase.snackoverflow.domain.users.User;
import dhbw.ase.snackoverflow.domain.users.NoActiveUserException;
import dhbw.ase.snackoverflow.domain.users.UserRepository;
import dhbw.ase.snackoverflow.domain.users.usecases.GetActiveUser;

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
