package api.auth;

import api.builder.RequestBuilder;
import api.endpoints.AuthClient;
import api.model.LoginResponse;
import api.model.User;
import api.model.UsersList;
import data.TestDataLoader;

public class AuthManager {

    private static String token;

    public static String getToken(String role) {
        if (token == null) {
            token = generateToken(role);
        }
        return token;
    }

    private static String generateToken(String role) {
		UsersList users = TestDataLoader.loadUsers("users.json");

		
		User user = users.getUsers().stream()
				.filter(u->u.getRole().equalsIgnoreCase(role))
				.findFirst()
                .orElseThrow(() -> new RuntimeException("No user found for role: " + role));				

        AuthClient authClient = new AuthClient();

	    String generatedtoken = authClient.login(RequestBuilder.buildLoginRequest(user.getEmail(), user.getPassword()))
	            .assertStatus(200)
	            .as(LoginResponse.class)
	            .getToken();
	    
	    return generatedtoken;
    }
}