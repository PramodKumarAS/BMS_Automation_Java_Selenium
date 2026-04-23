package api.model;

import java.util.List;

public class GetAllShowsByTheatreResponse {
    private boolean success;
    private String message;
    private List<ShowModel> shows;

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public List<ShowModel> getShows() { return shows; }

    public void setSuccess(boolean success) { this.success = success; }
    public void setMessage(String message) { this.message = message; }
    public void setShows(List<ShowModel> shows) { this.shows = shows; }

}