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
        String userEmail = "";
        String userPassword = "";

        switch (role){
            case "user":
                userEmail=System.getenv("USER_EMAIL");
                userPassword=System.getenv("USER_PASSWORD");
                break;
            case "partner":
                userEmail=System.getenv("PARTNER_EMAIL");
                userPassword=System.getenv("PARTNER_PASSWORD");
                break;

            case "admin":
                userEmail=System.getenv("ADMIN_EMAIL");
                userPassword=System.getenv("ADMIN_PASSWORD");
                break;
        }

        AuthClient authClient = new AuthClient();

	    String generatedtoken = authClient.login(RequestBuilder.buildLoginRequest(userEmail, userPassword))
	            .assertStatus(200)
	            .as(LoginResponse.class)
	            .getToken();
	    
	    return generatedtoken;
    }
}