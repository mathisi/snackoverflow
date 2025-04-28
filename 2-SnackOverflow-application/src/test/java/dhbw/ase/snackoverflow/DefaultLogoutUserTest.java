package dhbw.ase.snackoverflow;

import dhbw.ase.snackoverflow.application.usecases.DefaultLogoutUser;
import dhbw.ase.snackoverflow.domain.users.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DefaultLogoutUserTest {

    private UserRepository userRepository;
    private DefaultLogoutUser defaultLogoutUser;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        defaultLogoutUser = new DefaultLogoutUser(userRepository);
    }

    @Test
    void testLogout() {
        defaultLogoutUser.logout();

        Mockito.verify(userRepository, Mockito.times(1)).logoutActiveUser();
    }
}