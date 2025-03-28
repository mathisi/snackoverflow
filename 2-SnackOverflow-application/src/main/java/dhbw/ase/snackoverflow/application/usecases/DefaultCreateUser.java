package dhbw.ase.snackoverflow.application.usecases;

import dhbw.ase.snackoverflow.domain.entities.User;
import dhbw.ase.snackoverflow.domain.repositories.UserRepository;
import dhbw.ase.snackoverflow.domain.usecases.CreateUser;

public class DefaultCreateUser implements CreateUser {
    private final UserRepository userRepository;

    public DefaultCreateUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        return this.userRepository.create(user);
    }
}
