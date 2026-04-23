package api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShowModel {
    @JsonProperty("_id")
    private String id;

    private String name;
    private String date;
    private String time;

    private Movie movie;
    private int ticketPrice;
    private int totalSeats;
    private String theatre;

    private String createdAt;
    private String updatedAt;

    @JsonProperty("__v")
    private int version;

    private List<String> bookedSeats;

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public Movie getMovie() { return movie; }
    public int getTicketPrice() { return ticketPrice; }
    public int getTotalSeats() { return totalSeats; }
    public String getTheatre() { return theatre; }
    public String getCreatedAt() { return createdAt; }
    public String getUpdatedAt() { return updatedAt; }
    public int getVersion() { return version; }
    public List<String> getBookedSeats() { return bookedSeats; }
}
