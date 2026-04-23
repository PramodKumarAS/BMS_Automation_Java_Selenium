package api.model;

public class AddShowResponse {

    private boolean success;
    private String message;
    private Show show;

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public Show getShow() { return show; }
}