package tests.api;

import java.util.Base64;

import org.bson.Document;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.mongodb.client.MongoCollection;

import api.builder.RequestBuilder;
import api.model.CurrentUserResponse;
import api.model.ForgetPasswordRequest;
import api.model.ForgetPasswordResponse;
import api.model.LoginRequest;
import api.model.LoginResponse;
import api.model.RegisterRequest;
import api.model.RegisterResponse;
import api.model.ResetPasswordRequest;
import api.model.ResetPasswordResponse;
import base.APIBaseTest;
import constants.AppConstants;
import data.MongoConnection;
import data.MongoHelper;
import dataproviders.LoginDataProvider;
import io.restassured.path.json.JsonPath;

public class AuthApiTests extends APIBaseTest  {
	
	@Test(groups = {"smoke"}, description = "POST /login: Should return 200 with valid JWT token, correct role and success message when valid credentials are provided")
	public void shouldLoginSuccessfully_whenValidCredentialsProvided() {
		LoginRequest request = RequestBuilder.buildLoginRequest(user.getEmail(), user.getPassword());
		LoginResponse apiLoginResponse = authClient.login(request)
				.assertStatus(200)
				.validateSchema("schema/login-schema.json")
				.as(LoginResponse.class);
		
		Assert.assertTrue(apiLoginResponse.isSuccess());
		Assert.assertNotNull(apiLoginResponse.getToken());
		Assert.assertEquals(apiLoginResponse.getMessage(),AppConstants.LOGIN_SUCCESS_MESSAGE);
		Assert.assertEquals(apiLoginResponse.getRole(), AppConstants.ROLE_USER);
	}
	
	@Test(groups = {"regression"}, description = "POST /login: Should return a JWT token with 3 parts (header.payload.signature) when login is successful")
	public void shouldHaveValidJWTStructure_whenLoginSuccessful() {
		LoginRequest request = RequestBuilder.buildLoginRequest(user.getEmail(), user.getPassword());
		
		LoginResponse apiLoginResponse = authClient.login(request)
				.assertStatus(200)
				.as(LoginResponse.class);
		
		String token = apiLoginResponse.getToken();
		String[] parts = token.split("\\.");
		
		Assert.assertEquals(parts.length, 3);	
	}
	
	@Test(groups = {"regression"}, description = "POST /login: Should return a JWT payload containing userId, role and exp fields when Base64 decoded")
	public void shouldDecodeJWTPayload_whenBase64Decoded() {
		LoginRequest request = RequestBuilder.buildLoginRequest(user.getEmail(), user.getPassword());
		
		LoginResponse apiLoginResponse = authClient.login(request)
				.assertStatus(200)
				.as(LoginResponse.class);
		
		String token = apiLoginResponse.getToken();
		String[] parts = token.split("\\.");
		
		String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
				
		Assert.assertTrue(payload.contains("userId"));
		Assert.assertTrue(payload.contains("role"));
		Assert.assertTrue(payload.contains("exp"));
	}
	
	@Test(groups = {"regression"}, description = "POST /login: Should return a JWT token with expiry (exp) greater than current epoch time when login is successful")
	public void shouldHaveValidTokenExpiry_whenLoginSuccessful() {
		LoginRequest request = RequestBuilder.buildLoginRequest(user.getEmail(), user.getPassword());
		
		LoginResponse apiLoginResponse = authClient.login(request)
				.assertStatus(200)
				.as(LoginResponse.class);
		
		String token = apiLoginResponse.getToken();
		String[] parts = token.split("\\.");
		
		String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
		
		JsonPath jsonPath = new JsonPath(payload);
		long exp = jsonPath.getLong("exp");
		long currentTime = System.currentTimeMillis()/1000;
		
		Assert.assertTrue(exp>currentTime);		
	}
	
	@Test(groups = {"regression"}, description = "POST /login: Should return 401 with failure status and error message when invalid credentials are provided [DataDriven]",
		      dataProvider = "invalidLoginData",
		      dataProviderClass = LoginDataProvider.class)
	public void shouldReturn401_whenInvalidCredentialsProvided(String email, String password, String scenario) {
	    LoginRequest request = RequestBuilder.buildLoginRequest(email, password);

	    LoginResponse response = authClient.login(request)
	            .assertStatus(401)
	            .as(LoginResponse.class);

	    Assert.assertFalse(response.isSuccess(),
	            "Expected login failure for " + scenario + ", but got success");

	    Assert.assertEquals(response.getMessage(), AppConstants.LOGIN_INVALID_CREDENTIALS_MESSAGE,
	            "Error message mismatch for " + scenario);
	}	
	
	@Test(groups = {"regression"}, description = "POST /login: Should return 400 with validation error message when required fields (email/password) are missing [DataDriven]",
			dataProvider="invalidLoginInputs",dataProviderClass=LoginDataProvider.class)
	public void shouldReturn400_whenRequiredLoginFieldsMissing(String email, String password,String expectedErrorMessage) {
	    LoginRequest request = RequestBuilder.buildLoginRequest(email, password);

	    LoginResponse response = authClient.login(request)
	            .assertStatus(400)
	            .as(LoginResponse.class);
	    
	    Assert.assertEquals(response.getMessage(), expectedErrorMessage,
	    		"Error message expected: [" + expectedErrorMessage 
	    		+ "] but got: [" + response.getMessage() + "]");	    
	    
	}	
	
	@Test(groups = {"smoke"}, description = "POST /register: Should return 201 with userId, email, role in response and create a hashed-password user document in MongoDB when valid details are provided")
	public void shouldRegisterSuccessfully_whenValidDetailsProvided() {	
		String name = "TestUser_" + System.currentTimeMillis();
		String email = "test" + System.currentTimeMillis() + "@gmail.com";
		String password=AppConstants.TEST_USER_PASSWORD;
		
		MongoCollection<Document> mdb_TestCollection = MongoConnection.connect(AppConstants.MONGO_DB_NAME,AppConstants.MONGO_USERS_COLLECTION);

		try {
			RegisterRequest request = RequestBuilder.buildRegisterRequest(name,email, password);
			
			RegisterResponse response = authClient.register(request)
					.assertStatus(201)
					.validateSchema("schema/register-schema.json")
					.as(RegisterResponse.class);
			
			Assert.assertEquals(response.isSuccess(), true);
			Assert.assertEquals(response.getMessage(),AppConstants.REGISTER_SUCCESS_MESSAGE);		
			Assert.assertNotNull(response.getData().getUserId());
			Assert.assertEquals(response.getData().getEmail(),email);
			Assert.assertEquals(response.getData().getRole(),AppConstants.ROLE_USER);
			
			Document mdb_TestDocument = MongoHelper.findOneByAnyParams(mdb_TestCollection, "email", email);
			
			Assert.assertTrue(mdb_TestDocument.containsKey("_id"));			
			Assert.assertEquals(mdb_TestDocument.get("name"), name);
			Assert.assertEquals(mdb_TestDocument.get("email"), email);
			Assert.assertEquals(mdb_TestDocument.get("role"), AppConstants.ROLE_USER);
			Assert.assertNotEquals(mdb_TestDocument.get("password"), password);		
		}finally {
			MongoHelper.deleteOne(mdb_TestCollection, "email", email);						
		}				
	}
	
	@Test(groups = {"regression"}, description = "POST /register: Should return 400 with validation error message when required fields (name/email/password) are missing [DataDriven]",
		      dataProvider = "invalidRegisterInputs",
		      dataProviderClass = LoginDataProvider.class)
	public void shouldReturn400_whenRequiredRegisterFieldsMissing(String name,String email, String password,String expectedErrorMessage) {
	    RegisterRequest request = RequestBuilder.buildRegisterRequest(name,email, password);
	    
	    RegisterResponse response = authClient.register(request)
	            .assertStatus(400)
	            .as(RegisterResponse.class);
	    
	    Assert.assertEquals(response.getMessage(), expectedErrorMessage);	    
	    
	}	
		
	@Test(description = "POST /register: Should return 409 with conflict message when registering with an already existing email")
	public void shouldReturn409_whenRegisterWithExistingMail() {
	    RegisterRequest request = RequestBuilder.buildRegisterRequest(AppConstants.TEST_USER_NAME,user.getEmail(),AppConstants.TEST_USER_PASSWORD);
	    
	    RegisterResponse response = authClient.register(request)
	            .assertStatus(409)
	            .as(RegisterResponse.class);
	    
	    Assert.assertEquals(response.isSuccess(),false);	    
	    Assert.assertEquals(response.getMessage(),AppConstants.REGISTER_EXISTS_MESSAGE);	    
	    
	}	
	
	@Test(groups = {"smoke"}, description = "POST /forget-password: Should return 200 with OTP sent success message when a valid registered email is provided")
	public void shouldReturn200_whenForgetPasswordWithValidEmail() {
		ForgetPasswordRequest request = RequestBuilder.buildForgetPasswordRequest(user.getEmail());
		
		ForgetPasswordResponse response = authClient.forgetPassword(request)
				  .assertStatus(200)
				  .validateSchema("schema/forget-password-schema.json")
				  .as(ForgetPasswordResponse.class);
		
		Assert.assertEquals(response.isSuccess(),true);
		Assert.assertEquals(response.getMessage(), AppConstants.FORGET_PASSWORD_SUCCESS_MESSAGE);	
	}
	
	@Test(groups = {"regression"}, description = "POST /forget-password: Should return 200 with OTP sent success message even when an unregistered email is provided (no email enumeration)")
	public void shouldReturn200_whenForgetPasswordWithInvalidEmail() {
		ForgetPasswordRequest request = RequestBuilder.buildForgetPasswordRequest(AppConstants.INVALID_TEST_EMAIL);
		
		ForgetPasswordResponse response = authClient.forgetPassword(request)
				  .assertStatus(200)
				  .as(ForgetPasswordResponse.class);
		
		Assert.assertEquals(response.isSuccess(),true);
		Assert.assertEquals(response.getMessage(), AppConstants.FORGET_PASSWORD_SUCCESS_MESSAGE);	
	}
	
	@Test(groups = {"regression"}, description = "POST /forget-password: Should return 400 with email required error message when email field is null")
	public void shouldReturn400_whenForgetPasswordEmailIsNull() {
		ForgetPasswordRequest request = RequestBuilder.buildForgetPasswordRequest(null);
		
		ForgetPasswordResponse response = authClient.forgetPassword(request)
				  .assertStatus(400)
				  .as(ForgetPasswordResponse.class);
		
		Assert.assertEquals(response.isSuccess(),false);
		Assert.assertEquals(response.getMessage(), AppConstants.FORGET_PASSWORD_EMAIL_REQUIRED_MESSAGE);	
	}
	
	@Test(groups = {"smoke"}, description = "POST /reset-password: Should return 200 with success status and message when a valid OTP fetched from MongoDB is provided")
	public void shouldReturn200_whenResetPasswordWithValidOTP() {
		ForgetPasswordRequest request =  RequestBuilder.buildForgetPasswordRequest(user.getEmail());
		
		authClient.forgetPassword(request)
				  .assertStatus(200);
		
		MongoCollection<Document> mdb_TestCollection =MongoConnection.connect(AppConstants.MONGO_DB_NAME, AppConstants.MONGO_USERS_COLLECTION);
		Document mdb_TestDocument = MongoHelper.findOneByAnyParams(mdb_TestCollection, "email", user.getEmail());
		String otp = mdb_TestDocument.get("otp").toString();
		
		ResetPasswordRequest resetRequest = RequestBuilder.buildResetPasswordRequest(user.getPassword(),otp);
		
		ResetPasswordResponse resetResponse = authClient.resetPassword(resetRequest)
				  .assertStatus(200)
				  .validateSchema("schema/reset-password-schema.json")
				  .as(ResetPasswordResponse.class);
		
		Assert.assertEquals(resetResponse.getStatus(),AppConstants.RESET_PASSWORD_SUCCESS_STATUS);
		Assert.assertEquals(resetResponse.getMessage(),AppConstants.RESET_PASSWORD_SUCCESS_MESSAGE);
	}
	
	@Test(groups = {"regression"}, description = "POST /reset-password: Should return 401 with failure status and invalid request message when OTP is null")
	public void shouldReturn401_whenOTPIsNull() {
		ResetPasswordRequest request =  RequestBuilder.buildResetPasswordRequest(user.getPassword(),null);
		
		ResetPasswordResponse response = authClient.resetPassword(request)
		          .assertStatus(401)
		          .as(ResetPasswordResponse.class);
		
		Assert.assertEquals(response.getStatus(), AppConstants.RESET_PASSWORD_FAILURE_STATUS);
		Assert.assertEquals(response.getMessage(),AppConstants.RESET_PASSWORD_INVALID_REQUEST_MESSAGE);			
	}
	
	@Test(groups = {"regression"}, description = "POST /reset-password: Should return 401 with failure status and invalid request message when both password and OTP fields are null")
	public void shouldReturn401_whenBothFieldsAreNull() {
		ResetPasswordRequest request =  RequestBuilder.buildResetPasswordRequest(null,null);
		
		ResetPasswordResponse response = authClient.resetPassword(request)
		          .assertStatus(401)
		          .as(ResetPasswordResponse.class);
		
		Assert.assertEquals(response.getStatus(),AppConstants.RESET_PASSWORD_FAILURE_STATUS);
		Assert.assertEquals(response.getMessage(), AppConstants.RESET_PASSWORD_INVALID_REQUEST_MESSAGE);
	}
	
	@Test(groups = {"regression"}, description = "POST /reset-password: Should return 400 with failure status and wrong OTP message when an incorrect OTP is submitted")
	public void shouldReturn400_whenResetPasswordWithWrongOTP() {
		ResetPasswordRequest request =RequestBuilder.buildResetPasswordRequest(user.getPassword(),AppConstants.INVALID_OTP);
		
		ResetPasswordResponse response = authClient.resetPassword(request)
			      .assertStatus(400)
			      .as(ResetPasswordResponse.class);
		
		Assert.assertEquals(response.getStatus(), AppConstants.RESET_PASSWORD_FAILURE_STATUS);
		Assert.assertEquals(response.getMessage(), AppConstants.RESET_PASSWORD_WRONG_OTP_MESSAGE);
	}
	
	@Test(groups = {"regression"}, description = "GET /current-user: Should return 200 with correct email, name and role matching the authenticated user's profile")
	public void shouldReturn200_whenLoginWithValidUser() {	
		
		CurrentUserResponse response = authClient.getCurrentUser(AppConstants.ROLE_USER)
			      .assertStatus(200)
			      .validateSchema("schema/get-current-user-schema.json")
			      .as(CurrentUserResponse.class);
		
		Assert.assertEquals(response.isSuccess(), true);
		Assert.assertEquals(response.getUser().getEmail(),user.getEmail());
		Assert.assertEquals(response.getUser().getName(),AppConstants.TEST_USER_NAME);
		Assert.assertEquals(response.getUser().getRole(),AppConstants.ROLE_USER);
		Assert.assertNotEquals(response.getUser().getId(),null);
	}	
}