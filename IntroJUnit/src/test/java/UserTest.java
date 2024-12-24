	
import static org.junit.jupiter.api.Assertions.*;

// AssertJ
import org.assertj.core.api.Assertions;

//Harmcrest
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;



import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import account.User;

@TestInstance(Lifecycle.PER_CLASS)
public class UserTest {
	
	private User testUser;
	
	private User ivan;
	
	@BeforeAll
	void setUpForAllTests() {
		ivan = new User();
	}
	
	@AfterAll
	void deleteIvanAfterAllTests() {
		ivan = null;
	}
	
	@BeforeEach
	void setUp() {
		testUser = new User("Ivan", "Petrov", "ivan.petrov@email.com");
	}
	
	@AfterEach
	void tearDown() {
		testUser = null;
	}

	@Test
	void testCreateUser() {
		assertEquals("Ivan", testUser.getFirstName());
		assertEquals("Petrov", testUser.getLastName());
	}
	
	@Test
	void testSettersOfUser() {
		// 1. Arrange
		User user = new User();
		user.setFirstName("Ivan");
		user.setLastName("Petrov");
		user.setEmail("ivan.petrov@email.com");
		
		// 2. Act
		String actualFirstName = user.getFirstName();
		String actualLastName = user.getLastName();
		
		// 3. Assertion
		assertEquals("Ivan", actualFirstName);
		assertEquals("Petrov", actualLastName);
	}
	
	@Test
	void testCreateUserHarmcrest() {
		// testUser
		// Harmcrest 
		assertThat(testUser.getFirstName(), is("Ivan"));
		assertThat(testUser.getLastName(), equalTo("Petrov"));
	}

	@Test
	void testSetterOfUserHarmcrest() {
		User user = new User();
		user.setEmail("ivan.petrov@email.com");
		
		assertThat(user.getEmail(), 
				startsWith("ivan.")
		);
		assertThat(user.getEmail(), 
				endsWith(".com")
		);
		assertThat(user.getEmail(), 
				containsString("petrov")
		);
	}
	
	@Test
	void testSettersOfUserAssertJ() {
		ivan.setFirstName("Ivan");
		ivan.setEmail("ivan.petrov@email.com");
		
		// AssertJ
		Assertions.assertThat(ivan.getFirstName())
					.isNotEmpty()
					.isNotNull()
					.isEqualTo("Ivan");
		
		Assertions.assertThat(ivan.getEmail())
					.isNotBlank()
					.isNotEqualTo("ivan")
					.containsIgnoringCase("@EMAIL.cOm")
					.startsWith("ivan.petrov")
					.endsWith(".com");
	}
}
