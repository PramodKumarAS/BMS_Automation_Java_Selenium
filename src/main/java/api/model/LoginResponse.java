package api.model;

public class LoginResponse {

    private boolean success;
    private String token;
    private String message;
    private String role;

    public boolean isSuccess() {
        return success;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }

    public String getRole() {
        return role;
    }
}