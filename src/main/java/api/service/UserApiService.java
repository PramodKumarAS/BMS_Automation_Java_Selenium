package api.service;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import api.model.Movie;
import api.model.UserServiceModel;
import base.BaseApi;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserApiService extends BaseApi  {


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
	
	public void postUser() {
		String token=getUserToken();
		
		Movie movieModel = new Movie();
		movieModel.set_id("697c1c300476ba2e220e7464");
		movieModel.setMovieName("Avengers: Endgame");
		
		UserServiceModel userServiceModel = new UserServiceModel();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String todayShowDate = LocalDate.now().format(formatter);
		userServiceModel.setDate(todayShowDate);
		userServiceModel.setShowId("697c1c7f0476ba2e220e7476");
		userServiceModel.setMovie(movieModel);
		
		String bodyJSON =null;
		 
		ObjectMapper mapper = new ObjectMapper();
		try {
			bodyJSON = mapper.writeValueAsString(userServiceModel);
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