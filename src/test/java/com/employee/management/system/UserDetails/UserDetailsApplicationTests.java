package com.employee.management.system.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.json.JSONException;
import static io.restassured.RestAssured.given;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.test.context.SpringBootTest;
import com.employee.management.system.UserDetails.jwtAuthentication.AuthRequest;
import com.employee.management.system.UserDetails.jwtAuthentication.JwtResponse;
import com.employee.management.system.UserDetails.model.Users;
import io.restassured.http.ContentType;


@SpringBootTest()
@TestMethodOrder(OrderAnnotation.class)
class UserDetailsApplicationTests {
	
	@Test
	void contextLoads() {
	}
	
	String TokenGenerator() {
		AuthRequest authCheck = new AuthRequest();
		authCheck.setEmail("shivdutt.ckp@gmail.com");
		authCheck.setPassword("Password");
		authCheck.setRole("ADMIN");
		
		JwtResponse response = given().header("Content-type", "application/json").contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(authCheck)
                .when()
                .post("http://localhost:9000/home/authenticate")
                .then()
                .assertThat().statusCode(200)
                .extract().response().getBody().as(JwtResponse.class);
		String authToken = "Bearer " + response.getToken();
		
		return authToken;
		
	}

	
//	Test for checking Login Functionality
	@Test
	@Order(1)
	void LoginTest () {
		AuthRequest authCheck = new AuthRequest();
		authCheck.setEmail("shivdutt.ckp@gmail.com");
		authCheck.setPassword("Password");
		authCheck.setRole("ADMIN");
		
		given().header("Content-type", "application/json").contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(authCheck)
                .when()
                .post("http://localhost:9000/home/authenticate")
                .then()
                .assertThat().statusCode(200);
                	
	}
	
//	Test for user Registration and check by getting data from database
	@Test
	@Order(2)
	void AddUserTest() {
		
		String token = TokenGenerator();
		
		Users user = new Users();
		user.setEmail("test@gmail.com");
		user.setPassword("test");
		user.setFirstName("test");
		user.setRole("EMPLOYEE");
		user.setManagerEmail("manager@gmail.com");
		user.setManagerName("manager");
		
		Users responsePost = given()
                  .header("Authorization", token).contentType(ContentType.JSON).accept(ContentType.JSON)
                  .body(user)
                  .when()
                  .post("http://localhost:9000/home/add")
                  .then()
                  .assertThat().statusCode(201)
                  .extract().response().getBody().as(Users.class);
		
		 assertEquals(user.getEmail(), responsePost.getEmail());
		 assertEquals(user.getFirstName(), responsePost.getFirstName());
		 assertEquals(user.getRole(), responsePost.getRole());
		 assertEquals(user.getManagerEmail(), responsePost.getManagerEmail());
		 assertEquals(user.getManagerName(), responsePost.getManagerName());
		 
		
		
	}
	
//	Get User Details by Email Id
	@Test
	@Order(3)
	void GetUserDetailsTest() {
		String token = TokenGenerator();
		
		Users user = new Users();
		user.setEmail("test@gmail.com");
		user.setPassword("test");
		user.setFirstName("test");
		user.setRole("EMPLOYEE");
		user.setManagerEmail("manager@gmail.com");
		user.setManagerName("manager");
		
		 Users resultSaved = given()
                 .header("Authorization", token).contentType(ContentType.JSON).accept(ContentType.JSON)
                 .when()
                 .get("http://localhost:9000/home/all/"+user.getEmail())
                 .then()
                 .assertThat().statusCode(200)
                 .extract().response().getBody().as(Users.class);
		 
		 assertEquals(user.getEmail(), resultSaved.getEmail());
		 assertEquals(user.getFirstName(), resultSaved.getFirstName());
		 assertEquals(user.getRole(), resultSaved.getRole());
		 assertEquals(user.getManagerEmail(), resultSaved.getManagerEmail());
		 assertEquals(user.getManagerName(), resultSaved.getManagerName());
		
	}

	
//	Get User Details by Manager Email Test
	@Test
	@Order(4)
	void GetUserDetailsByManagerEmailTest() throws JSONException {
	String token = TokenGenerator();
	String managerEmail = "manager@gmail.com";
	Users[] responseUsers = given()
     .header("Authorization", token).contentType(ContentType.JSON).accept(ContentType.JSON)
     .when()
     .get("http://localhost:9000/home/manager/"+managerEmail)
     .then()
     .assertThat().statusCode(200)
     .extract().response().as(Users[].class);
	
	for(int i =0; i<responseUsers.length;i++) {
		assertEquals(managerEmail,responseUsers[i].getManagerEmail());
	}
		 
}
	
//	Get Users by Role Test
	@Test
	@Order(5)
	void GetUsersByRoleTest() throws JSONException {
	String token = TokenGenerator();
	String role = "EMPLOYEE";
	Users[] responseUsers = given()
     .header("Authorization", token).contentType(ContentType.JSON).accept(ContentType.JSON)
     .when()
     .get("http://localhost:9000/home/all/role/"+role)
     .then()
     .assertThat().statusCode(200)
     .extract().response().as(Users[].class);
	
	for(int i =0; i<responseUsers.length;i++) {
		assertEquals(role,responseUsers[i].getRole());
	}
		 
}

	
//	Test for checking Updating user functionality
	@Test
	@Order(6)
	void UpdateUserTest() {
		String token = TokenGenerator();
		
		 Users resultSaved = given()
                 .header("Authorization", token).contentType(ContentType.JSON).accept(ContentType.JSON)
                 .when()
                 .get("http://localhost:9000/home/all/test@gmail.com")
                 .then()
                 .assertThat().statusCode(200)
                 .extract().response().getBody().as(Users.class);
		 
		 resultSaved.setLastName("Updated");
		
		 Users updatedUser = given()
         .header("Authorization", token).contentType(ContentType.JSON).accept(ContentType.JSON)
         .body(resultSaved)
         .when()
         .put("http://localhost:9000/home/update")
         .then()
         .assertThat().statusCode(200)
         .extract().response().getBody().as(Users.class);
		 
//		 Checking Last Name is updated or not		 
		 assertEquals(resultSaved.getLastName(),updatedUser.getLastName());	 
		
	}
	
	
//	Test for checking deletion of user
	@Test
	@Order(7)
	void DeleteUserTest() {
		String token = TokenGenerator();
		
		 given()
         .header("Authorization", token).contentType(ContentType.JSON).accept(ContentType.JSON)
         .when()
         .delete("http://localhost:9000/home/delete/test@gmail.com")
         .then()
         .assertThat().statusCode(200)
         .extract().response();
		 
		
	}
	
	
	
}
