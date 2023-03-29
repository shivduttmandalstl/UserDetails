package com.employee.management.system.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static io.restassured.RestAssured.given;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.employee.management.system.UserDetails.jwtAuthentication.AuthRequest;
import com.employee.management.system.UserDetails.jwtAuthentication.JwtResponse;
import com.employee.management.system.UserDetails.model.Users;
import io.restassured.http.ContentType;


@SpringBootTest()
class UserDetailsApplicationTests {
	
	@Test
	void contextLoads() {
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
		String token = "Bearer " + response.getToken();
		
		Users user = new Users();
		user.setEmail("test@gmail.com");
		user.setPassword("test");
		user.setFirstName("test");
		user.setRole("EMPLOYEE");
		
			given()
                  .header("Authorization", token).contentType(ContentType.JSON).accept(ContentType.JSON)
                  .body(user)
                  .when()
                  .post("http://localhost:9000/home/add")
                  .then()
                  .assertThat().statusCode(201)
                  .extract().response();
		  
		 
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
		 

		
	}
	
	
//	Test for checking Updating user functionality
	@Test
	@Order(3)
	void updateUserTest() {
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
		String token = "Bearer " + response.getToken();
		
		 Users resultSaved = given()
                 .header("Authorization", token).contentType(ContentType.JSON).accept(ContentType.JSON)
                 .when()
                 .get("http://localhost:9000/home/all/test@gmail.com")
                 .then()
                 .assertThat().statusCode(200)
                 .extract().response().getBody().as(Users.class);
		 resultSaved.setLastName("Updated");
		
		 given()
         .header("Authorization", token).contentType(ContentType.JSON).accept(ContentType.JSON)
         .body(resultSaved)
         .when()
         .put("http://localhost:9000/home/update")
         .then()
         .assertThat().statusCode(200)
         .extract().response();
		 
		
	}
	
	
//	Test for checking deletion of user
	@Test
	@Order(4)
	void deleteUserTest() {
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
		String token = "Bearer " + response.getToken();
		
		 given()
         .header("Authorization", token).contentType(ContentType.JSON).accept(ContentType.JSON)
         .when()
         .delete("http://localhost:9000/home/delete/test@gmail.com")
         .then()
         .assertThat().statusCode(200)
         .extract().response();
		 
		
	}
	
	
	
}
