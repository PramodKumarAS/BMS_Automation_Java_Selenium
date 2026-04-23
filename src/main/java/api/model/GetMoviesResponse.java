package api.model;

import java.util.List;

public class GetMoviesResponse {

    private boolean success;
    private String message;
    private List<Movie> movies;

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public List<Movie> getMovies() { return movies; }

    public void setSuccess(boolean success) { this.success = success; }
    public void setMessage(String message) { this.message = message; }
    public void setMovies(List<Movie> movies) { this.movies = movies; }
}
