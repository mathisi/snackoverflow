package dhbw.ase.snackoverflow.users;

import dhbw.ase.snackoverflow.application.users.DefaultUserRepository;
import dhbw.ase.snackoverflow.application.users.usecases.DefaultChangeUserName;
import dhbw.ase.snackoverflow.domain.users.User;
import dhbw.ase.snackoverflow.domain.users.EmailAddress;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import java.util.Optional;

public class DefaultChangeUserNameTest {

    private DefaultUserRepository userRepository = Mockito.mock(DefaultUserRepository.class);
    private DefaultChangeUserName changeUserName = new DefaultChangeUserName(userRepository);

    @Test
    void changeNameSuccessful() {
        User user = new User.Builder().id(1).email(new EmailAddress("test@mail.de")).userName("OldName").password("pw").build();

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
