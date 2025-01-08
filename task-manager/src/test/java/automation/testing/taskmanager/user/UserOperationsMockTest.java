package automation.testing.taskmanager.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserOperationsMockTest {
	
	@Mock private UserDBStorage userDBStorage;
	
	@Mock private User mockedUser;
	
	@InjectMocks private UserOperations userOperations;

	private User user;
	
	@BeforeEach
	void setUp() {
		user = new User();
		user.setFirstName("Pesho");
		user.setLastName("Ivanov");
		user.setEmail("pesho.ivanov@email.com");
	}
	
	@Test
	void testGetAllUsers() {
		User firstUser = new User();
		firstUser.setId(666L);
		firstUser.setFirstName("Pesho");
		
		User secondUser = new User();
		secondUser.setId(111l);
		secondUser.setFirstName("Stoyan");
		
		ArrayList<User> usersFromDb = new ArrayList<>();
		usersFromDb.add(firstUser);
		usersFromDb.add(secondUser);
		
		when(userDBStorage.getAllUsers()).thenReturn(usersFromDb);
		
		List<User> actualUsers = userOperations.getAllUsers();
		
		assertEquals(
				usersFromDb.size(),
				actualUsers.size()
		);
		
		assertEquals(
				firstUser.getFirstName(), 
				actualUsers.get(0).getFirstName()
		);
		
		assertEquals(
				usersFromDb.get(1).getFirstName(), 
				actualUsers.get(1).getFirstName()
		);
	}

	@Test
	void testGetUserById() {
		User user = new User();
		user.setId(666L);
		user.setFirstName("Pesho");
		
//		when(userDBStorage.getUserById(user.getId())).thenReturn(user);

		when(userDBStorage.getUserById(anyLong()))
		.thenReturn(user);
	
		
		User theUserThatIsFetchedFromDB = 
				userOperations.getUserById(user.getId());
	
		assertEquals(user.getId(), theUserThatIsFetchedFromDB.getId());
	}
	
	
	@Test
	void testGetUserByEmail() {
		User user = new User();
		user.setId(666L);
		user.setFirstName("Pesho");
		user.setEmail("test@email.com");
		
//		when(userDBStorage.getUserByEmail("test@email.com"))
//			.thenReturn(user);
//	
		when(userDBStorage.getUserByEmail(anyString()))
			.thenReturn(user);
		
		User fetchedUserFromDb = 
				userOperations.getUserByEmail(user.getEmail());
		
		assertEquals(user.getEmail(), fetchedUserFromDb.getEmail());
	}
	
	
	@Test
	void testRegisterUserWithMock() {
		User user = new User();
		user.setEmail("test@email.com");
		user.setLastName("Ivanov");
		
		when(userDBStorage.getUserByEmail(user.getEmail()))
			.thenReturn(null)
			.thenReturn(user);
		
		User createdUser = userOperations.registerUser(user);
		
		verify(userDBStorage, times(2)).getUserByEmail(user.getEmail());
		verify(userDBStorage, times(1)).saveUser(user);
		assertEquals(user.getEmail(), createdUser.getEmail());
	}
	
	
	@Test 
	void testRegisterUserValidation(){
		when(mockedUser.getEmail()).thenReturn("someEmail.com");
		when(mockedUser.getLastName()).thenReturn("Petrov");
		
		when(userDBStorage.getUserByEmail("someEmail.com"))
			.thenReturn(null) // Първи път на извиквaнe на методa - искам да ми върне null
			.thenReturn(mockedUser); // Втори път на извиквaнe на методa - искам да ми върне mockedUser
		
		User createdUser = userOperations.registerUser(mockedUser);
		
		verify(userDBStorage, times(2)).getUserByEmail("someEmail.com");
		assertEquals("someEmail.com", createdUser.getEmail());
	}
	
	@Test
	void testGetUserByEmailSuccess() {
		when(userDBStorage.getUserByEmail(user.getEmail())).thenReturn(user);
		
		User retrievedUser = userOperations.getUserByEmail(user.getEmail());
		
		assertNotNull(retrievedUser);
		assertEquals(user.getEmail(), retrievedUser.getEmail());
		verify(userDBStorage, times(1)).getUserByEmail(user.getEmail());
	}
	
	@Test
	void testGetUserByEmailNotFound() {
		when(userDBStorage.getUserByEmail(user.getEmail())).thenReturn(null);
		
		User retrievedUser = userOperations.getUserByEmail(user.getEmail());
		
		assertNull(retrievedUser);
		verify(userDBStorage, times(1)).getUserByEmail(user.getEmail());
	}
}
