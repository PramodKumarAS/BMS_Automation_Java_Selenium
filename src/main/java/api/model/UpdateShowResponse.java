package api.model;

public class UpdateShowResponse {
    private boolean success;
    private String message;
    private Show updatedShow;

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public Show getUpdatedShow() { return updatedShow; }
}