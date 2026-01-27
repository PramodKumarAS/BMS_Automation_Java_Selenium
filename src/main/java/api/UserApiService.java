package api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

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
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.header("Authorization","Bearer " + token)
			.body("{\r\n"
					+ "    \"date\": \"2026-01-27\",\r\n"
					+ "    \"movie\": {\r\n"
					+ "        \"_id\": \"6888d1ad9e7f9c58df68dc4b\",\r\n"
					+ "        \"movieName\": \"Avengers: Endgame\"\r\n"
					+ "    },\r\n"
					+ "    \"showId\": \"68d201d2ec4b9e9967412d66\"\r\n"
					+ "}")
		.when()
		    .post("/show/update-show")
		.then()
		    .log().all()
		    .statusCode(200)
		    .body("message",equalTo("The show has been updated!"));
	}
}  