package apiTests;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import base.APIBaseTest;

public class GetUserTest extends APIBaseTest
{
	@Test
	public void getUser() {
		String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2N2Y5Yzk1ZGRmNWUyMTM4NjkyZGEzNTUiLCJyb2xlIjoiVXNlciIsImlhdCI6MTc2OTA0ODA2MSwiZXhwIjoxNzY5MDUxNjYxfQ.dT-tFEGAXl0P31dwSlpHsZ8HN-a4PqPcYjBPMecJlTs";
		
		given()
			.log().all()
			.header("Authorization","Bearer " + token)
		.when()
			.get("/user/get-currentUser")
		.then()
			.log().all()
			.statusCode(200)
			.body("success",equalTo(true));
	}
}  