import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import account.User;

public class UserTest {

	@Test
	void testCreateUser() {
		User user = new User("Ivan", "Petrov", "ivan.petrov@email.com");
		assertEquals("Ivan", user.getFirstName());
		assertEquals("Petrov", user.getLastName());
	}

}
