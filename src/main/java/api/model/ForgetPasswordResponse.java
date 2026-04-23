package api.model;

public class ForgetPasswordResponse {

    private boolean success;
    private String message;

    public boolean isSuccess() {   
        return success;
    }

    public String getMessage() {
        return message;
    }
}