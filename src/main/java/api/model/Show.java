package api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Show {

    private String name;
    private String date;
    private String time;
    private String movie;
    private int ticketPrice;
    private int totalSeats;
    private List<Object> bookedSeats;
    private String theatre;

    @JsonProperty("_id")
    private String id;

    private String createdAt;
    private String updatedAt;

    @JsonProperty("__v")
    private int version;

    public String getName() { return name; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getMovie() { return movie; }
    public int getTicketPrice() { return ticketPrice; }
    public int getTotalSeats() { return totalSeats; }
    public List<Object> getBookedSeats() { return bookedSeats; }
    public String getTheatre() { return theatre; }
    public String getId() { return id; }
    public String getCreatedAt() { return createdAt; }
    public String getUpdatedAt() { return updatedAt; }
    public int getVersion() { return version; }
}