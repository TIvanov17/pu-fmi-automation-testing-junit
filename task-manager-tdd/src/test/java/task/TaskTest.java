package task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


// Test First Aproach
public class TaskTest {

	@Test
	void testCreateTask() {
		Task task = new Task();
		task.setDescription("Task 1");
		task.setIsCompleted(true);
		
		assertEquals("Task 1", task.getDescription());
		assertTrue(task.getIsCompleted());
	}
	
}
