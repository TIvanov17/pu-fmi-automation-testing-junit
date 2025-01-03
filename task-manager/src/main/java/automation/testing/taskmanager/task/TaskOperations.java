package automation.testing.taskmanager.task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import automation.testing.taskmanager.comment.Comment;
import automation.testing.taskmanager.comment.CommentDBStorage;
import automation.testing.taskmanager.user.User;
import automation.testing.taskmanager.user.UserOperations;

@Component
public class TaskOperations {

	@Autowired
	private TaskDBStorage takDbStorage;

	@Autowired
	private CommentDBStorage commentDBStorage;

	@Autowired
	private UserOperations userOperations;

	public boolean createTask(Task task) {

		if (task.getDescription() == null || task.getDescription().isEmpty()) {
			return false;
		}

		User user = userOperations.getUserById(task.getUserId());

		if (user == null) {
			return false;
		}

		task.setCreatedOn(LocalDateTime.now());
		takDbStorage.saveTask(task);
		return true;
	}

	public List<Task> getAllTasks() {
		return takDbStorage.getAllTasks();
	}

	public Task getTaskById(Long id) {
		return takDbStorage.getTaskById(id);
	}

	public boolean addCommentToATask(Long id, Comment comment) {

		if (comment.getContent() == null || comment.getContent().isEmpty()) {
			return false;
		}

		Task task = takDbStorage.getTaskById(id);
		if (task == null) {
			return false;
		}

		User user = userOperations.getUserById(comment.getUserId());
		if (user == null) {
			return false;
		}

		comment.setTaskId(id);
		comment.setCreatedOn(LocalDateTime.now());
		comment.setUpdatedOn(LocalDateTime.now());
		commentDBStorage.saveComment(comment);
		return true;
	}

	public List<Comment> getCommentsOfTask(Long id) {

		Task task = takDbStorage.getTaskById(id);
		if (task == null) {
			return null;
		}
		
		return commentDBStorage.getCommentsByTaskId(id);
	}
}
