package dhbw.ase.snackoverflow.application.users.usecases;

import dhbw.ase.snackoverflow.domain.users.User;
import dhbw.ase.snackoverflow.domain.users.InvalidPasswordException;
import dhbw.ase.snackoverflow.domain.users.UserNotFoundException;
import dhbw.ase.snackoverflow.domain.users.UserRepository;
import dhbw.ase.snackoverflow.domain.users.usecases.LoginUser;
import dhbw.ase.snackoverflow.domain.users.EmailAddress;

import java.util.Objects;
import java.util.Optional;

public class DefaultLoginUser implements LoginUser {

    private final UserRepository userRepository;

    public DefaultLoginUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(EmailAddress email, String password) throws Exception {

        Optional<User> user = userRepository.searchByMail(email);
        if (user.isPresent()) {
            if (Objects.equals(user.get().getPassword(), password)) {
                return this.userRepository.setActiveUser(user.get());
            } else {
                throw new InvalidPasswordException(email.toString());
            }
        } else {
            throw new UserNotFoundException(email.toString());
        }

    }
}
