package tests.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import api.model.Movie;
import api.model.UserServiceModel;

import com.fasterxml.jackson.core.JsonProcessingException;

import base.APIBaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetUserTest extends APIBaseTest
{		
	
	@Test
	public String getUserToken() {
		Response response =  given()
		    .log().all()
		    .contentType(ContentType.JSON)
		    .body("{\"email\":\"pkPartner@gmail.com\",\"password\":\"14036\"}")
		.when()
		    .log().all()
		    .post("/user/login");
		
		return response.jsonPath().getString("token");
	}
	
	@Test
	public void getUser() {
		String token=getUserToken();
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.header("Authorization","Bearer " + token)
		.when()
			.get("/user/get-currentUser")
		.then()
			.log().all()
			.statusCode(200)
			.body("success",equalTo(true));
	}
	
	@Test
	public void postUser() {
		String token=getUserToken();
		
		UserServiceModel model = new UserServiceModel();
		Movie model1 = new Movie();
		model1.setMovieName("Avengers: Endgame");
		model1.set_id("6888d1ad9e7f9c58df68dc4b");
		
		model.setDate("2026-01-29");
		model.setMovie(model1);
		model.setShowId("68d201d2ec4b9e9967412d66");
		String bodyJSON=null;
		
		ObjectMapper  mapper = new ObjectMapper();
		try {
			bodyJSON = mapper.writeValueAsString(model);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.header("Authorization","Bearer " + token)
			.body(bodyJSON)
		.when()
		    .post("/show/update-show")
		.then()
		    .log().all()
		    .statusCode(200)
		    .body("message",equalTo("The show has been updated!"));
	}
}  