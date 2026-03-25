package tests.api;

import java.util.Base64;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.model.LoginRequest;
import api.model.LoginResponse;
import base.APIBaseTest;
import dataProviders.LoginDataProvider;
import io.restassured.path.json.JsonPath;

public class AuthApiTests extends APIBaseTest  {
	
	@Test
	public void shouldLoginSuccessfully_withValidCredentials() {		
		LoginRequest request = new LoginRequest();
		request.setEmail(user.getEmail());
		request.setPassword(user.getPassword());
		
		LoginResponse apiLoginResponse = authClient.login(request)
				.assertStatus(200)
				.validateSchema("schema/login-schema.json")
				.as(LoginResponse.class);
		
		Assert.assertTrue(apiLoginResponse.isSuccess(),
			    "Expected login to succeed, but it failed");
		Assert.assertNotNull(apiLoginResponse.getToken(),
			    "Expected token to be returned, but it was null");
		Assert.assertEquals(apiLoginResponse.getMessage(), "Successfully logged in!",
			    "Expected message 'Successfully logged in!' but got: " + apiLoginResponse.getMessage());
		Assert.assertEquals(apiLoginResponse.getRole(), "User",
			    "Expected role 'User' but got: " + apiLoginResponse.getRole());
	}
	
	@Test
	public void shouldHaveValidJWTStructure() {
		LoginRequest request = new LoginRequest();
		request.setEmail(user.getEmail());
		request.setPassword(user.getPassword());
		
		LoginResponse apiLoginResponse = authClient.login(request)
				.assertStatus(200)
				.as(LoginResponse.class);
		
		String token = apiLoginResponse.getToken();
		String[] parts = token.split("\\.");
		
		Assert.assertEquals(parts.length, 3,
				"Expected JWT to have 3 parts but got: " + parts.length);		
	}
	
	@Test
	public void shouldDecodeJWTBase64Successfully() {
		LoginRequest request = new LoginRequest();
		request.setEmail(user.getEmail());
		request.setPassword(user.getPassword());
		
		LoginResponse apiLoginResponse = authClient.login(request)
				.assertStatus(200)
				.as(LoginResponse.class);
		
		String token = apiLoginResponse.getToken();
		String[] parts = token.split("\\.");
		
		String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
				
		Assert.assertTrue(payload.contains("userId"),
				"userId is missing");
		Assert.assertTrue(payload.contains("role"),
				"role is missing");
		Assert.assertTrue(payload.contains("exp"),
				"exp is missing");	
		
	
	}
	
	
	
	
	
	
	
	
	
	@Test
	
	public void shouldHaveValidTokenExpiry() {
		LoginRequest request = new LoginRequest();
		request.setEmail(user.getEmail());
		request.setPassword(user.getPassword());
		
		LoginResponse apiLoginResponse = authClient.login(request)
				.assertStatus(200)
				.as(LoginResponse.class);
		
		String token = apiLoginResponse.getToken();
		String[] parts = token.split("\\.");
		
		String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
		
		JsonPath jsonPath = new JsonPath(payload);
		long exp = jsonPath.getLong("exp");
		long currentTime = System.currentTimeMillis()/1000;
		
		Assert.assertTrue(exp>currentTime, 
				"Token has expired. exp=" + exp + ", currentTime=" + currentTime);		
	}
	
	@Test(dataProvider="invalidLoginData",dataProviderClass=LoginDataProvider.class)
	public void shouldReturnError_withInvalidCredentials(String email, String password, String scenario) {
	    LoginRequest request = new LoginRequest();
	    request.setEmail(email);
	    request.setPassword(password);

	    LoginResponse response = authClient.login(request)
	            .assertStatus(401)
	            .as(LoginResponse.class);

	    Assert.assertFalse(response.isSuccess(),
	            "Expected login failure for " + scenario + ", but got success");

	    Assert.assertEquals(response.getMessage(), "Invalid email or password",
	            "Error message mismatch for " + scenario);
	}	
}