package api.client;

import static io.restassured.RestAssured.given;

import api.model.ForgetPasswordRequest;
import api.model.ForgetPasswordResponse;
import api.model.LoginRequest;
import api.model.RegisterRequest;
import api.model.RegisterResponse;
import api.model.ResetPasswordRequest;
import api.model.ResetPasswordResponse;
import api.wrapper.ApiResponse;
import io.restassured.response.Response;
import validators.ResponseValidator;

public class AuthClient {
	public ApiResponse login(LoginRequest request) {		
		Response response =  given()
			  .body(request)
			.when()
			  .post("/user/login");
		
		return new ApiResponse(response);
	}
	
	public RegisterResponse register(RegisterRequest request,boolean validSchema,int expectedStatusCode) {
		Response response =  given()
				.body(request)
			.when()
			   .post("/user/register");
		
		response.then().statusCode(expectedStatusCode).assertThat();
		
		if(validSchema) {					
			ResponseValidator.validateSchema(response, "schema/register-schema.json");
		}
		
		return response.as(RegisterResponse.class);
	}
	
	public ForgetPasswordResponse forgetPassword(ForgetPasswordRequest request) {
		Response response =   given()
				.body(request)
			.when()
		    	.post("/user/forgetpassword");
		
		ResponseValidator.validateSchema(response, "schema/forget-password-schema.json");
		
	    return response.as(ForgetPasswordResponse.class);
	}
	
	public ResetPasswordResponse resetPassword(ResetPasswordRequest request) {
		Response response =   given()
				.body(request)
			.when()
			    .post("/user/resetpassword");
		
		ResponseValidator.validateSchema(response, "schema/reset-password-schema.json");
		
   	    return response.as(ResetPasswordResponse.class);
 	}	
}