package api.auth;

import api.builder.RequestBuilder;
import api.endpoints.AuthClient;
import api.model.LoginResponse;
import config.CredentialsReader;

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
                userEmail= CredentialsReader.username("USER_EMAIL");
                userPassword=CredentialsReader.password("USER_PASSWORD");
                break;
            case "Partner":
                userEmail=CredentialsReader.username("PARTNER_EMAIL");
                userPassword=CredentialsReader.password("PARTNER_PASSWORD");
                break;

            case "Admin":
                userEmail=CredentialsReader.username("ADMIN_EMAIL");
                userPassword=CredentialsReader.password("ADMIN_PASSWORD");
                break;
        }

        AuthClient authClient = new AuthClient();

        return authClient.login(RequestBuilder.buildLoginRequest(userEmail, userPassword))
                .assertStatus(200)
                .as(LoginResponse.class)
                .getToken();
    }
}