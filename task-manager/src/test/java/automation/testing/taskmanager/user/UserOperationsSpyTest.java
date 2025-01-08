package automation.testing.taskmanager.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserOperationsSpyTest {

	@Spy
	private ArrayList<User> users;
	
	@Mock
	private ArrayList<User> usersMocked;
	
	@Test
	void testSpyArrayList() {
		
		User user = new User();
		user.setEmail("sss");
		
		User userTwo = new User();
		userTwo.setEmail("testEmail");
		
		User userThree = new User();
		userThree.setEmail("secondMail");
		
		users.add(user);	
		users.add(userTwo);	
		
		when(users.get(1)).thenReturn(userThree);
		
		assertEquals("sss",  users.get(0).getEmail());
		assertEquals(userThree.getEmail(),  users.get(1).getEmail());
	}
	
	@Test
	void testMockedArrayList() {
		User user = new User();
		user.setEmail("email");
		usersMocked.add(user);
		usersMocked.add(user);
		usersMocked.add(user);
		
		
		when(usersMocked.get(0)).thenReturn(user);
		assertNotNull(usersMocked.get(0));
		assertEquals("email", usersMocked.get(0).getEmail());
		assertNull(usersMocked.get(1));
		assertNull(usersMocked.get(2));	
	}
}
