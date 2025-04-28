package dhbw.ase.snackoverflow.domain.users;

import java.util.Optional;

public interface UserRepository {

    User create(User user);
    Optional<User> searchByID(int id);
    Optional<User> searchByMail(EmailAddress emailAddress);

    User getActiveUser();
    User setActiveUser(User newActiveUser) throws Exception;

    void logoutActiveUser();
}