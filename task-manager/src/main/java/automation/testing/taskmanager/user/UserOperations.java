package automation.testing.taskmanager.user;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserOperations {

	@Autowired
	private UserDBStorage userDBStorage;

	public User registerUser(User user) {
		
		if(user.getEmail() == null || user.getEmail().isEmpty()) {
			return null;
		}
		
		User userWithEmail = userDBStorage.getUserByEmail(user.getEmail());
		
		if(userWithEmail != null) {
			return null;
		}
		
		user.setCreatedOn(LocalDateTime.now());
		userDBStorage.saveUser(user);
		return userDBStorage.getUserByEmail(user.getEmail());
	}

	public List<User> getAllUsers() {
		return userDBStorage.getAllUsers();
	}

	public User getUserById(Long id) {
		return userDBStorage.getUserById(id);
	}
	
	public User getUserByEmail(String email) {
		return userDBStorage.getUserByEmail(email);
	}

}
