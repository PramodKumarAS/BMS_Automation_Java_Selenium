package api.model;

import java.util.List;

public class GetTheatresResponse {
    private boolean success;
    private String message;
    private List<Theatre> allTheatres;

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public List<Theatre> getAllTheatres() { return allTheatres; }

}
