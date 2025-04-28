package dhbw.ase.snackoverflow.application.usecases;

import dhbw.ase.snackoverflow.application.repositories.DefaultUserRepository;
import dhbw.ase.snackoverflow.domain.users.User;
import dhbw.ase.snackoverflow.domain.users.usecases.ChangeUserName;

import java.util.Optional;

public class DefaultChangeUserName implements ChangeUserName {

    private final DefaultUserRepository userRepository;

    public DefaultChangeUserName(DefaultUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User changeName(int userId, String userName) {
        Optional<User> user = userRepository.searchByID(userId);
        if(!user.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }
        user.get().setUserName(userName);

        return user.get();
    }
}
