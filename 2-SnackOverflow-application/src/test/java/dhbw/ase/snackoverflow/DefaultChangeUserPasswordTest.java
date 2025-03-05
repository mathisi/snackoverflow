package dhbw.ase.snackoverflow;

import dhbw.ase.snackoverflow.application.repositories.DefaultUserRepository;
import dhbw.ase.snackoverflow.application.usecases.DefaultChangeUserName;
import dhbw.ase.snackoverflow.application.usecases.DefaultChangeUserPassword;
import dhbw.ase.snackoverflow.domain.entities.User;
import dhbw.ase.snackoverflow.domain.valueobjects.EmailAddress;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import java.util.Optional;
public class DefaultChangeUserPasswordTest {

    private DefaultUserRepository userRepository = Mockito.mock(DefaultUserRepository.class);
    private DefaultChangeUserPassword changeUserPassword = new DefaultChangeUserPassword(userRepository);

    @Test
    void changePasswordSuccessful() {
        User user = new User(1, new EmailAddress("test@mail.de"), "name", "oldPw");
        Mockito.when(userRepository.searchByID(1)).thenReturn(Optional.of(user));

        User updatedUser = changeUserPassword.changePassword(1, "newPw");

        assertNotNull(updatedUser);
        assertEquals("newPw", updatedUser.getPassword());
        Mockito.verify(userRepository, Mockito.times(1)).searchByID(1);
    }

    @Test
    void changePasswordUserNotFound() {
        Mockito.when(userRepository.searchByID(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                changeUserPassword.changePassword(1, "newPw"));

        assertEquals("User not found", exception.getMessage());
        Mockito.verify(userRepository, Mockito.times(1)).searchByID(1);
    }
}
