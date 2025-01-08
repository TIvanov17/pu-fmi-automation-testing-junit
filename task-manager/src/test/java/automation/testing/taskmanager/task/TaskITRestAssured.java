package automation.testing.taskmanager.task;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import automation.testing.taskmanager.user.User;
import automation.testing.taskmanager.user.UserDBStorage;
import io.restassured.http.ContentType;
import io.restassured.http.Method;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class TaskITRestAssured {

	@LocalServerPort
	private int localPort;
	
	@Autowired
	private TaskDBStorage taskDBStorage;
	
	@Autowired
	private UserDBStorage userDBStorage;
	
	@BeforeEach
	void setUp() {
		baseURI = "http://localhost";
		port = localPort;
	}
	
	@AfterEach
	void tearDown() {
		taskDBStorage.deleteAllTasks();
		userDBStorage.deleteAllUsers();
	}
	
	@Test
	void createTask() {
		
		User user = new User();
		user.setFirstName("Ivan");
		user.setLastName("Ivanov");
		user.setEmail("ivan_ivanov12@email.com");
		
		User createdUser = given()
			.log().all()
			.header("content-type", ContentType.JSON)
			.body(user)
		.when()
			.request(Method.POST, "/users")
		.then()
			.log().all()
			.statusCode(200)
			.extract().as(User.class);
		
		Long userId = createdUser.getId();
		
		Task task = new Task();
		task.setDescription("Test task");
		task.setIsCompleted(false);
		task.setUserId(userId);
		
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(task)
		.post("/tasks") // .request(Method.POST, "/users")
		.then()
			.log().all()
			.statusCode(200);
	}
	
    @Test
    void testGetAllTasks() {
    	User user = new User();
		user.setFirstName("Ivan");
		user.setLastName("Ivanov");
		user.setEmail("ivan_ivanov12@email.com");
		
        User user1 = given()
            .contentType(ContentType.JSON)
            .body(user)
        .when()
            .post("/users")
        .then()
            .statusCode(200)
            .body("firstName", equalTo("Ivan"))
            .body("lastName", equalTo("Ivanov"))
            .body("email", equalTo("ivan_ivanov12@email.com"))
            .extract().as(User.class);
        
        Task task = new Task("Test task description", false,  user1.getId());
		
        given()
			.contentType(ContentType.JSON)
			.body(task)
		.when()
			.post("/tasks")
		.then()
			.statusCode(200)
			.body(equalTo("Task created successfully"));

        Task task2 = new Task("Test task description", false, user1.getId());
		given()
			.contentType(ContentType.JSON)
			.body(task2)
		.when()
			.post("/tasks")
		.then()
			.statusCode(200)
			.body(equalTo("Task created successfully"));

        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/tasks")
        .then()
            .statusCode(200)
            .body("size()", greaterThan(1)); 
    }

    @Test
    void testGetTaskByIdNotFound() {
        Long taskId = 999L;

        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/tasks/{id}", taskId)
        .then()
            .statusCode(404)
            .body(equalTo("Task not found"));
    }

}
