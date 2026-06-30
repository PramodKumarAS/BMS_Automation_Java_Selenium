package api.auth;

import api.builder.RequestBuilder;
import api.endpoints.AuthClient;
import api.model.LoginResponse;

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
            case "User":
                userEmail=System.getenv("USER_EMAIL");
                userPassword=System.getenv("USER_PASSWORD");
                break;
            case "Partner":
                userEmail=System.getenv("PARTNER_EMAIL");
                userPassword=System.getenv("PARTNER_PASSWORD");
                break;

            case "Admin":
                userEmail=System.getenv("ADMIN_EMAIL");
                userPassword=System.getenv("ADMIN_PASSWORD");
                break;
        }

        AuthClient authClient = new AuthClient();

        return authClient.login(RequestBuilder.buildLoginRequest(userEmail, userPassword))
                .assertStatus(200)
                .as(LoginResponse.class)
                .getToken();
    }
}