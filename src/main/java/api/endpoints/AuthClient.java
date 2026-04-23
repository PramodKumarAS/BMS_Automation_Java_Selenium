package api.endpoints;

import static io.restassured.RestAssured.given;

import api.client.RequestSpecFactory;
import api.model.ForgetPasswordRequest;
import api.model.LoginRequest;
import api.model.RegisterRequest;
import api.model.ResetPasswordRequest;
import api.response.ApiResponse;
import constants.AppConstants;
import io.restassured.response.Response;

public class AuthClient {
	public ApiResponse login(LoginRequest request) {		
		Response response =  given()
			  .spec(RequestSpecFactory.getBaseSpec())
			  .body(request)
			.when()
			  .post(AppConstants.LOGIN);
		
		return new ApiResponse(response);
	}
	
	public ApiResponse register(RegisterRequest request) {
		Response response =  given()
				.spec(RequestSpecFactory.getBaseSpec())				
				.body(request)
			.when()
			   .post(AppConstants.REGISTER);
		
		return new ApiResponse(response);
	}
	
	public ApiResponse forgetPassword(ForgetPasswordRequest request) {
		Response response =   given()
				.spec(RequestSpecFactory.getBaseSpec())				
				.body(request)
			.when()
		    	.post(AppConstants.FORGET_PASSWORD);
				
		return new ApiResponse(response);
	}
	
	public ApiResponse resetPassword(ResetPasswordRequest request) {
		Response response =   given()
				.spec(RequestSpecFactory.getBaseSpec())								
				.body(request)
			.when()
			    .post(AppConstants.RESET_PASSWORD);
				
		return new ApiResponse(response);
 	}	
	
	public ApiResponse getCurrentUser(String role) {
		Response response =   given()
				.spec(RequestSpecFactory.getAuthSpec(role))
			.when()
			    .get(AppConstants.GET_CURRENTUSER);
				
		return new ApiResponse(response);		
	}
}