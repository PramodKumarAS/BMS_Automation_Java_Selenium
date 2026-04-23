package api.model;

public class UpdateTheatreResponse {
    private boolean success;
    private String message;
    private TheatreResponse updatedTheatre;

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public TheatreResponse getUpdatedTheatre() { return updatedTheatre; }

}
