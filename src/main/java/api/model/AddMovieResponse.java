package api.model;

public class AddMovieResponse {

    private boolean success;
    private String message;
    private Movie movie;

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public Movie getMovie() { return movie; }
}