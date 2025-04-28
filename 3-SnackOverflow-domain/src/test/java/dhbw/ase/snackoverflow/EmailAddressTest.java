package dhbw.ase.snackoverflow;

import dhbw.ase.snackoverflow.domain.users.InvalidEmailAddressException;
import dhbw.ase.snackoverflow.domain.users.EmailAddress;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmailAddressTest {

    @Test
    void createValidAddress() {
        EmailAddress email = new EmailAddress("test@test.de");
        assertEquals("test@test.de", email.getAddress());
    }

    @Test
    void createInvalidAddress() {
        assertThrows(InvalidEmailAddressException.class, () -> new EmailAddress("test"));
    }

    @Test
    void returnDomain() {
        EmailAddress email = new EmailAddress("test@domain.de");
        assertEquals("domain.de", email.getDomain());
    }

    @Test
    void validateEquality() {
        EmailAddress email1 = new EmailAddress("test@test.de");
        EmailAddress email2 = new EmailAddress("test@test.de");
        assertEquals(email1, email2);
    }
}
