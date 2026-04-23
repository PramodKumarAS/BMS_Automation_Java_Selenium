package api.model;

public class AddTheatreResponse {

    private boolean success;
    private String message;
    private TheatreResponse theatre;

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public TheatreResponse getTheatre() { return theatre; }
}