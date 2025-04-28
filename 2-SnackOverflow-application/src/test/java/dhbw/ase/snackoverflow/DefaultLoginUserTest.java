package dhbw.ase.snackoverflow;

import dhbw.ase.snackoverflow.application.users.usecases.DefaultLoginUser;
import dhbw.ase.snackoverflow.domain.users.User;
import dhbw.ase.snackoverflow.domain.users.InvalidPasswordException;
import dhbw.ase.snackoverflow.domain.users.UserNotFoundException;
import dhbw.ase.snackoverflow.domain.users.UserRepository;
import dhbw.ase.snackoverflow.domain.users.EmailAddress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class DefaultLoginUserTest {

    private UserRepository userRepository;
    private DefaultLoginUser defaultLoginUser;
    private User testUser;
    private EmailAddress testEmail;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        defaultLoginUser = new DefaultLoginUser(userRepository);
        testEmail = new EmailAddress("test@example.com");
        testUser = new User.Builder().id(1).email(testEmail).userName("TestUser").password("password123").build();
    }

    @Test
    void testLoginSuccessful() throws Exception {
        Mockito.when(userRepository.searchByMail(testEmail)).thenReturn(Optional.of(testUser));
        Mockito.when(userRepository.setActiveUser(testUser)).thenReturn(testUser);

        User result = defaultLoginUser.login(testEmail, "password123");

        assertNotNull(result, "The returned user should not be null.");
        assertEquals(testUser, result, "The returned user should match the test user.");

        Mockito.verify(userRepository).searchByMail(testEmail);
        Mockito.verify(userRepository).setActiveUser(testUser);
    }

    @Test
    void testLoginUserNotFound() throws Exception {
        Mockito.when(userRepository.searchByMail(testEmail)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            defaultLoginUser.login(testEmail, "password123");
        });

        assertEquals(testEmail.toString(), exception.getMessage(),
                "Exception message should match the email provided");

        Mockito.verify(userRepository).searchByMail(testEmail);
        Mockito.verify(userRepository, Mockito.never()).setActiveUser(Mockito.any());
    }

    @Test
    void testLoginInvalidPassword() throws Exception {
        Mockito.when(userRepository.searchByMail(testEmail)).thenReturn(Optional.of(testUser));

        Exception exception = assertThrows(InvalidPasswordException.class, () -> {
            defaultLoginUser.login(testEmail, "wrongPassword");
        });

        assertEquals("Password for user test@example.com invalid", exception.getMessage(),
                "Exception message should match the email provided");

        Mockito.verify(userRepository).searchByMail(testEmail);
        Mockito.verify(userRepository, Mockito.never()).setActiveUser(Mockito.any());
    }
}