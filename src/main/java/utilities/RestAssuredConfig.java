package utilities;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class RestAssuredConfig {
	
	public static void setUp() {
		RestAssured.baseURI="https://bookmyshow-ihmd.onrender.com/api";
		
		RestAssured.requestSpecification = RestAssured.given()
													.contentType(ContentType.JSON)
													.accept(ContentType.JSON);
		
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();		
	}
}