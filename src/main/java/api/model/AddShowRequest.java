package api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddShowRequest {

    private String name;
    private String date;
    private String time;
    private String movie;
    private int ticketPrice;
    private int totalSeats;
    private String theatre;

    @JsonProperty("showId")
    private String showId;   // for update/delete

    // Getters & Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getMovie() { return movie; }
    public void setMovie(String movie) { this.movie = movie; }

    public int getTicketPrice() { return ticketPrice; }
    public void setTicketPrice(int ticketPrice) { this.ticketPrice = ticketPrice; }

    public int getTotalSeats() { return totalSeats; }
    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }

    public String getTheatre() { return theatre; }
    public void setTheatre(String theatre) { this.theatre = theatre; }

    public String getShowId() { return showId; }
    public void setShowId(String showId) { this.showId = showId; }
}