package automation.testing.taskmanager.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApi {

	@Autowired
	private UserOperations userOperations;

	@PostMapping("/users")
	public ResponseEntity<?> createUser(@RequestBody User user) {
		User createdUser = userOperations.registerUser(user);
		return createdUser != null ? ResponseEntity.status(200).body(createdUser) 
				: ResponseEntity.status(400).body("User creation failed");
	}

	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userOperations.getAllUsers();
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Long id) {
		User user = userOperations.getUserById(id);
		return user == null ? ResponseEntity.status(404).body("User not found") : ResponseEntity.ok(user);

	}

}
