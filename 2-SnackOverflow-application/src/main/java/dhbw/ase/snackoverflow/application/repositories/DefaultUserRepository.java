package dhbw.ase.snackoverflow.application.repositories;

import dhbw.ase.snackoverflow.domain.entities.Recipe;
import dhbw.ase.snackoverflow.domain.entities.User;
import dhbw.ase.snackoverflow.domain.repositories.UserRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultUserRepository implements UserRepository {
    private final Map<Integer, User> users = new HashMap<>();
    private final AtomicInteger idHandler = new AtomicInteger(0);

    public DefaultUserRepository() {
        // to-do: add sample data?
    }

    @Override
    public User create(User user) {
        if(user.getId() == 0) {
            int id = idHandler.incrementAndGet();
            User newUser = new User(id, user.getEmail(), user.getUserName(), user.getPassword(), user.getShoppingList());
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
}
