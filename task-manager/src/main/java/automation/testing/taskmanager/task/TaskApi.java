package automation.testing.taskmanager.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import automation.testing.taskmanager.comment.Comment;

@RestController
public class TaskApi {

	@Autowired
	private TaskOperations taskOperations;

	@PostMapping("/tasks")
	public ResponseEntity<String> createTask(@RequestBody Task task) {
		boolean result = taskOperations.createTask(task);
		return result ? ResponseEntity.status(200).body("Task created successfully")
				: ResponseEntity.status(400).body("Task creation failed");
	}

	@GetMapping("/tasks")
	public List<Task> getAllUsers() {
		return taskOperations.getAllTasks();
	}

	@GetMapping("/tasks/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Long id) {
		Task task = taskOperations.getTaskById(id);
		return task == null ? ResponseEntity.status(404).body("Task not found") : ResponseEntity.ok(task);
	}

	@PostMapping("/tasks/{id}/comments")
	public ResponseEntity<?> addCommentToTask(@PathVariable Long id, @RequestBody Comment comment) {
		boolean result = taskOperations.addCommentToATask(id, comment);
		return result ? ResponseEntity.ok("Comment added successfully")
				: ResponseEntity.status(400).body("Failed to add comment");
	}

	@GetMapping("/tasks/{id}/comments")
	public ResponseEntity<List<Comment>> getCommentsOfTask(@PathVariable Long id) {
		return ResponseEntity.ok(taskOperations.getCommentsOfTask(id));
	}
}
