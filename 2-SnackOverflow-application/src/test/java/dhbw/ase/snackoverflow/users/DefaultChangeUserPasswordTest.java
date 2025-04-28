package dhbw.ase.snackoverflow.users;

import dhbw.ase.snackoverflow.application.users.DefaultUserRepository;
import dhbw.ase.snackoverflow.application.users.usecases.DefaultChangeUserPassword;
import dhbw.ase.snackoverflow.domain.users.User;
import dhbw.ase.snackoverflow.domain.users.UserNotFoundException;
import dhbw.ase.snackoverflow.domain.users.EmailAddress;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import java.util.Optional;
public class DefaultChangeUserPasswordTest {

    private DefaultUserRepository userRepository = Mockito.mock(DefaultUserRepository.class);
    private DefaultChangeUserPassword changeUserPassword = new DefaultChangeUserPassword(userRepository);

    @Test
    void changePasswordSuccessful() {
        User user = new User.Builder()
                .id(1)
                .email(new EmailAddress("test@mail.de"))
                .userName("name")
                .password("oldPw")
                .build();
        Mockito.when(userRepository.searchByID(1)).thenReturn(Optional.of(user));

        User updatedUser = changeUserPassword.changePassword(1, "newPw");

        assertNotNull(updatedUser);
        assertEquals("newPw", updatedUser.getPassword());
        Mockito.verify(userRepository, Mockito.times(1)).searchByID(1);
    }

    @Test
    void changePasswordUserNotFound() {
        Mockito.when(userRepository.searchByID(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () ->
                changeUserPassword.changePassword(1, "newPw"));

        assertEquals("User with id 1 not found", exception.getMessage());
        Mockito.verify(userRepository, Mockito.times(1)).searchByID(1);
    }
}
