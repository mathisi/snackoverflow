package dhbw.ase.snackoverflow.application.users.usecases;

import dhbw.ase.snackoverflow.application.users.DefaultUserRepository;
import dhbw.ase.snackoverflow.domain.users.User;
import dhbw.ase.snackoverflow.domain.users.UserNotFoundException;
import dhbw.ase.snackoverflow.domain.users.usecases.ChangeUserPassword;

import java.util.Optional;

public class DefaultChangeUserPassword implements ChangeUserPassword {

    private final DefaultUserRepository userRepository;

    public DefaultChangeUserPassword(DefaultUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User changePassword(int userId, String password) {

        Optional<User> user = userRepository.searchByID(userId);
        if(!user.isPresent()) {
            throw new UserNotFoundException("User with id " + userId + " not found");
        }
        else {
            user.get().setPassword(password);
        }
        return user.get();
    }

}
