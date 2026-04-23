package api.model;

public class UpdateMovieResponse {

    private boolean success;
    private String message;
    private Movie updatedMovie;

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public Movie getUpdatedMovie() { return updatedMovie; }
}