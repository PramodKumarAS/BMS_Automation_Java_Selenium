package api.client;

import static io.restassured.RestAssured.given;

import api.model.ForgetPasswordRequest;
import api.model.ForgetPasswordResponse;
import api.model.LoginRequest;
import api.model.LoginResponse;
import api.model.RegisterRequest;
import api.model.RegisterResponse;
import api.model.ResetPasswordRequest;
import api.model.ResetPasswordResponse;

public class AuthClient {
	public LoginResponse login(LoginRequest request) {		
		return given()
			  .body(request)
			.when()
			  .post("/user/login")
			.then()
			  .extract()
			  .as(LoginResponse.class);
	}
	
	public RegisterResponse register(RegisterRequest request) {
		return given()
				.body(request)
			.when()
			   .post("/user/register")
			.then()
			   .statusCode(200)
			   .extract()
			   .as(RegisterResponse.class);
	}
	
	public ForgetPasswordResponse forgetPassword(ForgetPasswordRequest request) {
		return given()
				.body(request)
			.when()
		    	.post("/user/forgetpassword")
		    .then()
		        .extract()
		        .as(ForgetPasswordResponse.class);
	}
	
	public ResetPasswordResponse resetPassword(ResetPasswordRequest request) {
		return given()
				.body(request)
			.when()
			    .post("/user/resetpassword")
			.then()
			    .statusCode(200)
			    .extract()
			    .as(ResetPasswordResponse.class);
	}	
}