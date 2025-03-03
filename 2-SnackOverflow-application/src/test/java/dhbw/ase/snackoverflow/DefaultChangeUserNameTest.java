package dhbw.ase.snackoverflow;

import dhbw.ase.snackoverflow.application.repositories.DefaultUserRepository;
import dhbw.ase.snackoverflow.application.usecases.DefaultChangeUserName;
import dhbw.ase.snackoverflow.domain.entities.User;
import dhbw.ase.snackoverflow.domain.valueobjects.EmailAddress;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import java.util.Optional;

public class DefaultChangeUserNameTest {

    private DefaultUserRepository userRepository = Mockito.mock(DefaultUserRepository.class);
    private DefaultChangeUserName changeUserName = new DefaultChangeUserName(userRepository);

    @Test
    void changeNameSuccessful() {
        User user = new User(1, new EmailAddress("test@mail.de"), "OldName", "pw");
        Mockito.when(userRepository.searchByID(1)).thenReturn(Optional.of(user));

        User updatedUser = changeUserName.changeName(1, "NewName");

        assertNotNull(updatedUser);
        assertEquals("NewName", updatedUser.getUserName());
        Mockito.verify(userRepository, Mockito.times(1)).searchByID(1);
    }

    @Test
    void changeNameUserNotFound() {
        Mockito.when(userRepository.searchByID(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                changeUserName.changeName(1, "NewName"));

        assertEquals("User not found", exception.getMessage());
        Mockito.verify(userRepository, Mockito.times(1)).searchByID(1);
    }
}
