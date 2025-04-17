package dhbw.ase.snackoverflow.application.repositories;

import dhbw.ase.snackoverflow.domain.entities.Recipe;
import dhbw.ase.snackoverflow.domain.entities.User;
import dhbw.ase.snackoverflow.domain.exceptions.UserNotFoundException;
import dhbw.ase.snackoverflow.domain.repositories.UserRepository;
import dhbw.ase.snackoverflow.domain.valueobjects.EmailAddress;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultUserRepository implements UserRepository {
    private final Map<Integer, User> users = new HashMap<>();
    private final AtomicInteger idHandler = new AtomicInteger(0);
    private User activeUser;

    public DefaultUserRepository() {
        this.mockRepository();
    }

    private void mockRepository() {
        // TODO: change mock data to have connection to recipes
        this.create(new User(0, new EmailAddress("til@til.de"), "Til", "1234"));
        this.create(new User(0, new EmailAddress("alex@alex.com"), "Alex", "1234"));
        this.create(new User(0, new EmailAddress("jane@doe.com"), "Jane", "1234"));
        this.create(new User(0, new EmailAddress("john@doe.com"), "John", "1234"));
    }

    @Override
    public User create(User user) {
        if (user.getId() == 0) {
            int id = idHandler.incrementAndGet();
            User newUser = new User(id, user.getEmail(), user.getUserName(), user.getPassword());
            this.users.put(id, newUser);
            return newUser;
        } else {
            this.users.put(user.getId(), user);
            return user;
        }
    }

    @Override
    public Optional<User> searchByID(int id) {
        return Optional.ofNullable(this.users.get(id));
    }

    @Override
    public Optional<User> searchByMail(EmailAddress emailAddress) {
        return users.values().stream()
                .filter(user -> user.getEmail().equals(emailAddress))
                .findFirst();
    }

    @Override
    public List<User> searchAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public User findByRecipe(Recipe recipe) {
        return null;

    }

    @Override
    public User getActiveUser() {
        return this.activeUser;
    }

    @Override
    public User setActiveUser(User newActiveUser) throws UserNotFoundException {
        if (this.users.get(newActiveUser.getId()) == null) {
            throw new UserNotFoundException(newActiveUser.getEmail().toString());
        }
        this.activeUser = newActiveUser;
        return this.activeUser;
    }

    @Override
    public void logoutActiveUser() {
        this.activeUser = null;
    }
}
