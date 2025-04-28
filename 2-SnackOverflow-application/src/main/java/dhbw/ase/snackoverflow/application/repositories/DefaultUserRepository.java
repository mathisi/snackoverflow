package dhbw.ase.snackoverflow.application.repositories;

import dhbw.ase.snackoverflow.domain.users.User;
import dhbw.ase.snackoverflow.domain.users.UserNotFoundException;
import dhbw.ase.snackoverflow.domain.users.UserRepository;
import dhbw.ase.snackoverflow.domain.users.EmailAddress;

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

        this.create(new User.Builder()
                .id(1)
                .email(new EmailAddress("til@til.de"))
                .userName("Til")
                .password("1234")
                .build());
        this.create(new User.Builder()
                .id(2)
                .email(new EmailAddress("alex@alex.com"))
                .userName("Alex")
                .password("1234")
                .build());
        this.create(new User.Builder()
                .id(3)
                .email(new EmailAddress("jane@doe.com"))
                .userName("Jane")
                .password("1234")
                .build());
        this.create(new User.Builder()
                .id(4)
                .email(new EmailAddress("john@doe.com"))
                .userName("John")
                .password("1234")
                .build());
    }

    @Override
    public User create(User user) {
        if (user.getId() == 0) {
            int id = idHandler.incrementAndGet();
            User newUser = new User.Builder()
                    .id(id)
                    .email(user.getEmail())
                    .userName(user.getUserName())
                    .password(user.getPassword())
                    .build();
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
