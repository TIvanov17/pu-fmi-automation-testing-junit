package automation.testing.taskmanager.user;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class UserITRestAssured {

	@LocalServerPort
	private int port;
	
	@Autowired
	private UserDBStorage userDBStorage;
	
	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}
	
	@AfterEach
	void tearDown() {
		userDBStorage.deleteAllUsers();
	}
	
	@Test
	void testGetAllUsers() {
		
		User user = new User();
		user.setFirstName("Ivan");
		user.setLastName("Ivanov");
		user.setEmail("ivan_ivanov@email.com");
		
		User user1 = new User();
		user1.setFirstName("Pesho");
		user1.setLastName("Ivanov");
		user1.setEmail("ivan_ivanov1@email.com");
		
		// Създаваме първият user
		
		RestAssured.given()
			.log().all()
			.header("content-type", ContentType.JSON)
			.body(user)
		.when()
			.request(Method.POST, "/users")
		.then()
			.log().all()
			.statusCode(200)
			.extract().as(User.class);
		

		// Създаваме вторият user
		
		RestAssured.given()
			.log().all()
			.header("content-type", ContentType.JSON)
			.body(user1)
		.when()
			.request(Method.POST, "/users")
		.then()
			.log().all()
			.statusCode(200)
			.extract().as(User.class);
		
		// Вземаме всички user-и
		RestAssured.given()
			.log().all()
			.header("content-type", ContentType.JSON)
		.when()
			.request(Method.GET, "/users")
		.then()
			.statusCode(200)
			.body("[0].firstName", equalTo("Ivan"))
			.body("[1].firstName", equalTo(user1.getFirstName()));
	}
	
	@Test
	void testGetUserByIdOfNotInsertedInDb() {
		
		RestAssured.given()
			.log().all()
			.header("content-type", ContentType.JSON)
		.when()
			.request(Method.GET, "/users/{id}", 123321)
		.then()
			.statusCode(404);
	}
	
	@Test
	void testGetUserByIdAfterCreated() {
		
		User user = new User();
		user.setFirstName("Ivan");
		user.setLastName("Ivanov");
		user.setEmail("ivan_ivanov@email.com");
		
		User createdUser = RestAssured.given()
			.log().all()
			.header("content-type", ContentType.JSON)
			.body(user)
		.when()
			.request(Method.POST, "/users")
		.then()
			.log().all()
			.statusCode(200)
			.extract().as(User.class);
		
		
		RestAssured.given()
			.log().all()
			.header("content-type", ContentType.JSON)
		.when()
			.request(Method.GET, "/users/{id}", createdUser.getId())
		.then()
			.log().all()
			.statusCode(200)
			.body("firstName", equalTo("Ivan"));
	}

	@Test
	void testCreateUserFromHTTPEndpoint() {
		
		User user = new User();
		user.setFirstName("Ivan");
		user.setLastName("Ivanov");
		user.setEmail("ivan_ivanov@email.com");
		
		User createdUser = RestAssured.given()
				.log().all()
				.header("content-type", ContentType.JSON)
				.body(user)
			.when()
				.request(Method.POST, "/users")
			.then()
				.log().all()
				.statusCode(200)
				.body("firstName", equalTo("Ivan"))
				.body("lastName", equalTo("Ivanov"))
				.body("email", equalTo("ivan_ivanov@email.com"))
				.extract().as(User.class);
	}

	@Test
    void testCreateUserFailure() {
		User user = new User();
		user.setFirstName("Ivan");
		user.setLastName("Ivanov");
		user.setEmail("ivan_ivanov@email.com");
		
        User createdUser = RestAssured.given()
            .contentType(ContentType.JSON)
            .body(user)
        .when()
            .post("/users")
        .then()
        	.extract().as(User.class);

        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(user)
        .when()
            .post("/users")
        .then()
            .statusCode(400)
            .body(equalTo("User creation failed"));
    }
	
	@Test
    void testGetUserByIdNotFound() {
		
		RestAssured.given()
        .when()
            .get("/users/{id}", 9999)
        .then()
            .statusCode(404)
            .body(equalTo("User not found"));
    }
}
