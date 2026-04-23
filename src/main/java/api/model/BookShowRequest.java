package api.model;

import java.util.List;

public class BookShowRequest {

    private List<Integer> seats;
    private String show;
    private String transactionId;
    private String user;

    public List<Integer> getSeats() { return seats; }
    public void setSeats(List<Integer> seats) { this.seats = seats; }

    public String getShow() { return show; }
    public void setShow(String show) { this.show = show; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }
}