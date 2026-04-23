package api.model;

public class CurrentUserResponse {
    private boolean success;
    private UserResponse user;

    public boolean isSuccess() {
        return success;
    }

    public UserResponse getUser() {
        return user;
    }
}
