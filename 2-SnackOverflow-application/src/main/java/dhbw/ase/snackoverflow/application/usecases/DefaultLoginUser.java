package dhbw.ase.snackoverflow.application.usecases;

import dhbw.ase.snackoverflow.domain.entities.User;
import dhbw.ase.snackoverflow.domain.exceptions.InvalidPasswordException;
import dhbw.ase.snackoverflow.domain.exceptions.UserNotFoundException;
import dhbw.ase.snackoverflow.domain.repositories.UserRepository;
import dhbw.ase.snackoverflow.domain.usecases.LoginUser;
import dhbw.ase.snackoverflow.domain.valueobjects.EmailAddress;

import java.util.Objects;
import java.util.Optional;

public class DefaultLoginUser implements LoginUser {

    private final UserRepository userRepository;

    public DefaultLoginUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(EmailAddress email, String password) throws Exception {
        try {
            Optional<User> user = userRepository.searchByMail(email);
            if(user.isPresent()) {
                    if(Objects.equals(user.get().getPassword(), password)) {
                        return this.userRepository.setActiveUser(user.get());
                    }
                    else {
                        throw new InvalidPasswordException(email.toString());
                    }
            } else {
                throw new UserNotFoundException(email.toString());
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
