package dhbw.ase.snackoverflow.application.usecases;

import dhbw.ase.snackoverflow.domain.entities.User;
import dhbw.ase.snackoverflow.domain.repositories.UserRepository;
import dhbw.ase.snackoverflow.domain.usecases.LogoutUser;

public class DefaultLogoutUser implements LogoutUser {
    private final UserRepository userRepository;

    public DefaultLogoutUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void logout() {
        this.userRepository.logoutActiveUser();
    }
}
