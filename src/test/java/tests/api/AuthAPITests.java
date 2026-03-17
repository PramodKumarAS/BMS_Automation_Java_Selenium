package tests.api;

import org.bson.Document;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.mongodb.client.MongoCollection;

import api.model.ForgetPasswordRequest;
import api.model.ForgetPasswordResponse;
import api.model.LoginRequest;
import api.model.LoginResponse;
import api.model.RegisterRequest;
import api.model.RegisterResponse;
import api.model.ResetPasswordRequest;
import api.model.ResetPasswordResponse;
import base.APIBaseTest;
import db.MongoConnection;
import db.MongoUtils;

public class AuthAPITests extends APIBaseTest  {
	
	@Test
	public void verifyUserCanLoginSuccessfully() {		
		LoginRequest request = new LoginRequest();
		request.setEmail(user.getEmail());
		request.setPassword(user.getPassword());
		
		LoginResponse response = authClient.login(request);
		
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getToken());
		Assert.assertEquals(response.getMessage(), "Successfully logged in!");
		Assert.assertEquals(response.getRole(), "User");			
	}
	
	@Test
	public void verifyUserGetErrorForInvalidLoginCredentials() {
		
		//Valid Email but Wrong Password
		LoginRequest request1 = new LoginRequest();
		request1.setEmail("pkUser@gmail.com");
		request1.setPassword("12333");
		LoginResponse loginResponseForWrongPassword = authClient.login(request1);
		
		Assert.assertEquals(loginResponseForWrongPassword.isSuccess(), false);
		Assert.assertEquals(loginResponseForWrongPassword.getMessage(), "No user/pass combo found");
		
		//Invalid Email
		LoginRequest request2 = new LoginRequest();
		request2.setEmail("pk21User@gmail.com");
		request2.setPassword("12333");
		LoginResponse loginResponseForWrongEmail = authClient.login(request2);
		
		Assert.assertEquals(loginResponseForWrongEmail.isSuccess(), false);
		Assert.assertEquals(loginResponseForWrongEmail.getMessage(), "No user found");
	}
	
	@Test
	public void verifyUserCanRegisterSuccessfully() {
		RegisterRequest request = new RegisterRequest();
		request.setEmail("pkP111artner@gmail.com");
		request.setname("Pramod");
		request.setPassword("14568");
		
		RegisterResponse response = authClient.register(request);
		
		Assert.assertTrue(response.isSuccess());
		Assert.assertEquals(response.getMessage(), "Registration is successfull");		
	}
	
	@Test
	public void verifyUserCanResetPassword() {		
		ForgetPasswordRequest request = new ForgetPasswordRequest();
		request.setEmail(user.getEmail());
		
		ForgetPasswordResponse response = authClient.forgetPassword(request);
		
		Assert.assertEquals(response.getStatus(),"success");
		Assert.assertEquals(response.getMessage(),"otp sent to your email");
		
		MongoCollection<Document> mdb_UserCollection= MongoConnection.connect("test", "users");
		Document mdb_Document = MongoUtils.findOneByAnyParams(mdb_UserCollection, "email", user.getEmail());
		Object otp = mdb_Document.get("otp");
		
		ResetPasswordRequest resetRequest = new ResetPasswordRequest();
		resetRequest.setOtp(otp.toString());
		resetRequest.setpassword("14036");
		
		ResetPasswordResponse resetResponse = authClient.resetPassword(resetRequest);
		
		Assert.assertEquals(resetResponse.getStatus(), "success");
		Assert.assertEquals(resetResponse.getMessage(), "password reset successfully");	
	}
	
	@Test
	public void verifyUserGetErrorForInvalidEmailForReset() {
		ForgetPasswordRequest request = new ForgetPasswordRequest();
		request.setEmail("pk1223@gmail.com");//Invalid Email
		ForgetPasswordResponse forgetPasswordResponse = authClient.forgetPassword(request);
		
		Assert.assertEquals(forgetPasswordResponse.getStatus(), "failure");
		Assert.assertEquals(forgetPasswordResponse.getMessage(), "user not found for this email");
	}
}