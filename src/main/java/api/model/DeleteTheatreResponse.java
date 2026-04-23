package api.model;

public class DeleteTheatreResponse {
    private boolean success;
    private String message;
    private TheatreResponse deletedTheatre;

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public TheatreResponse getDeletedTheatre() { return deletedTheatre; }

}
