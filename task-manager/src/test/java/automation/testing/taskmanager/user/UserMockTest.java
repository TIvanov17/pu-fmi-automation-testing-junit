package automation.testing.taskmanager.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserMockTest {
	
	@Mock private User user;
	
	@BeforeEach
	void setUp() {
		user = Mockito.mock(User.class);
	}
	
	@Test
	void testUserWithMockVerifyProcess() {

		user.setEmail("sometest@email.com");
		
		verify(user, times(1)).setEmail("sometest@email.com");
		verify(user, times(0)).setCreatedOn(LocalDateTime.now());
		verify(user, atLeast(1)).setEmail("sometest@email.com");
	}
	
	@Test
	void testUserWithMockStubbing() {

		LocalDateTime date = LocalDateTime.now();
		
		when(user.getFirstName()).thenReturn("Ivan");
		when(user.getCreatedOn()).thenReturn(date);
		
		assertEquals("Ivan", user.getFirstName());
		assertEquals(date, user.getCreatedOn());
		assertEquals(0, user.getId());
		assertEquals(null, user.getLastName());
	}
	
}
