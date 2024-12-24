package task;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import user.User;
import exceptions.GeneralApplicationException;

public class TaskStorageTest {

	private TaskStorage taskStorage;
	private User user;
	
	@BeforeEach
	void setUp() {
		taskStorage = new TaskStorage();
		// Arrange
		user = new User();
		user.setEmail("neshto@email.com");
		taskStorage.registerUser(user);
	}
	
	@Test
	void testRegisterUser() {
		boolean actualIsExisting = taskStorage.existUser(user);
		
		// Assert
		assertTrue(actualIsExisting);
	}
	
	@Test
	void testAddTaskToValidUser() {
		
		Task task = new Task();
		task.setDescription("Task 1");
		task.setIsCompleted(false);
		
		// When 
		taskStorage.addTaskToUser(user, task);
		List<Task> tasks = taskStorage.getTasks(user);
		
		// Then
		Assertions.assertThat(
				tasks.get(0).getDescription()
		).isEqualTo("Task 1");
	}
	
	@Test
	void testAddTask() {
		User userTwo = new User();
		userTwo.setEmail("ivan@email.com");
		
		Task task = new Task();
		task.setDescription("Task 1");
		task.setIsCompleted(false);
		
		
		assertThrows(GeneralApplicationException.class, 
				() -> {
					taskStorage.addTaskToUser(userTwo, task);
					List<Task> tasks = taskStorage.getTasks(userTwo);
				});
		
	}

	@Test
	void testAddTwoTasksToValidUser() {
		Task task = new Task();
		task.setDescription("Task 1");
		task.setIsCompleted(false);
		
		Task taskTwo = new Task();
		taskTwo.setDescription("Task 2");
		taskTwo.setIsCompleted(true);
		
		taskStorage.addTaskToUser(user, task);
		taskStorage.addTaskToUser(user, taskTwo);
		
		List<Task> tasks = taskStorage.getTasks(user);
		Assertions.assertThat(
				tasks.get(0).getDescription()
		).isEqualTo("Task 1");
		
		Assertions.assertThat(
				tasks.get(1).getDescription()
		).isEqualTo("Task 2");
	}
}
