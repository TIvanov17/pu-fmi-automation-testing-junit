package automation.testing.taskmanager.task;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import automation.testing.taskmanager.comment.Comment;
import automation.testing.taskmanager.comment.CommentDBStorage;
import automation.testing.taskmanager.user.User;
import automation.testing.taskmanager.user.UserOperations;

@ExtendWith(MockitoExtension.class)
public class TaskOperationMockTests {

	@Mock
	private TaskDBStorage taskDbStorage;

	@Mock
	private CommentDBStorage commentDbStorage;

	@Mock
	private UserOperations userOperations;

	@InjectMocks
	private TaskOperations taskOperations;

	private Task testTask;
	private User testUser;
	private Comment testComment;

	@BeforeEach
	void setUp() {

		testUser = new User();
		testUser.setId(1L);
		testUser.setFirstName("Ivan");
		testUser.setLastName("Ivanov");
		testUser.setEmail("john.doe@example.com");
		testUser.setCreatedOn(LocalDateTime.now());

		testTask = new Task();
		testTask.setId(1L);
		testTask.setDescription("Test Task");
		testTask.setIsCompleted(false);
		testTask.setCreatedOn(LocalDateTime.now());
		testTask.setUserId(testUser.getId());

		testComment = new Comment();
		testComment.setId(1L);
		testComment.setContent("Test comment");
		testComment.setUserId(testUser.getId());
		testComment.setTaskId(testTask.getId());
		testComment.setCreatedOn(LocalDateTime.now());
	}

	@Test
	void testCreateTaskSuccess() {
		when(userOperations.getUserById(testTask.getUserId())).thenReturn(testUser);
		doNothing().when(taskDbStorage).saveTask(any(Task.class));

		boolean result = taskOperations.createTask(testTask);

		assertTrue(result);
		verify(userOperations, times(1)).getUserById(testTask.getUserId());
		verify(taskDbStorage, times(1)).saveTask(testTask);
	}
	
	@Test
	void testCreateTaskFailureNullDescription() {
		testTask.setDescription(null);

		boolean result = taskOperations.createTask(testTask);

		assertFalse(result);
		verify(userOperations, never()).getUserById(anyLong());
		verify(taskDbStorage, never()).saveTask(any(Task.class));
	}

	@Test
	void testCreateTaskFailureInvalidUser() {
		when(userOperations.getUserById(testTask.getUserId())).thenReturn(null);

		boolean result = taskOperations.createTask(testTask);

		assertFalse(result);
		verify(userOperations, times(1)).getUserById(testTask.getUserId());
		verify(taskDbStorage, never()).saveTask(any(Task.class));
	}

	@Test
	void testGetAllTasks() {
		when(taskDbStorage.getAllTasks()).thenReturn(Arrays.asList(testTask));

		List<Task> tasks = taskOperations.getAllTasks();

		assertNotNull(tasks);
		assertEquals(1, tasks.size());
		assertEquals(testTask.getDescription(), tasks.get(0).getDescription());
		verify(taskDbStorage, times(1)).getAllTasks();
	}

	@Test
	void testAddCommentToTaskSuccess() {
		when(taskDbStorage.getTaskById(testTask.getId())).thenReturn(testTask);
		when(userOperations.getUserById(testComment.getUserId())).thenReturn(testUser);
		doNothing().when(commentDbStorage).saveComment(any(Comment.class));

		boolean result = taskOperations.addCommentToATask(testTask.getId(), testComment);

		assertTrue(result);
		verify(taskDbStorage, times(1)).getTaskById(testTask.getId());
		verify(userOperations, times(1)).getUserById(testComment.getUserId());
		verify(commentDbStorage, times(1)).saveComment(testComment);
	}

	@Test
	void testAddCommentToTaskFailureNullContent() {
		testComment.setContent(null);

		boolean result = taskOperations.addCommentToATask(testTask.getId(), testComment);

		assertFalse(result);
		verify(taskDbStorage, never()).getTaskById(anyLong());
		verify(userOperations, never()).getUserById(anyLong());
		verify(commentDbStorage, never()).saveComment(any(Comment.class));
	}

	@Test
	void testAddCommentToTaskFailureTaskNotFound() {
		when(taskDbStorage.getTaskById(testTask.getId())).thenReturn(null);

		boolean result = taskOperations.addCommentToATask(testTask.getId(), testComment);

		assertFalse(result);
		verify(taskDbStorage, times(1)).getTaskById(testTask.getId());
		verify(userOperations, never()).getUserById(anyLong());
		verify(commentDbStorage, never()).saveComment(any(Comment.class));
	}

	@Test
	void testGetCommentsOfTaskSuccess() {
		when(taskDbStorage.getTaskById(testTask.getId())).thenReturn(testTask);
		when(commentDbStorage.getCommentsByTaskId(testTask.getId())).thenReturn(Arrays.asList(testComment));

		List<Comment> comments = taskOperations.getCommentsOfTask(testTask.getId());

		assertNotNull(comments);
		assertEquals(1, comments.size(), "Should return one comment");
		verify(taskDbStorage, times(1)).getTaskById(testTask.getId());
		verify(commentDbStorage, times(1)).getCommentsByTaskId(testTask.getId());
	}

	@Test
	void testGetCommentsOfTaskFailureTaskNotFound() {
		when(taskDbStorage.getTaskById(testTask.getId())).thenReturn(null);

		List<Comment> comments = taskOperations.getCommentsOfTask(testTask.getId());

		assertNull(comments);
		verify(taskDbStorage, times(1)).getTaskById(testTask.getId());
		verify(commentDbStorage, never()).getCommentsByTaskId(anyLong());
	}
	
}
