package dhbw.ase.snackoverflow.application.users.usecases;

import dhbw.ase.snackoverflow.domain.users.UserRepository;
import dhbw.ase.snackoverflow.domain.users.usecases.LogoutUser;

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
