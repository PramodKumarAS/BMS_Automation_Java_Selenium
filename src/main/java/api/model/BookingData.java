package api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookingData {

    private String show;
    private String user;
    private List<Integer> seats;
    private String transactionId;
    private String createdAt;
    private String updatedAt;
    private String __v;

    @JsonProperty("_id")
    private String Id;   

    public String getShow() { return show; }
    public void setShow(String show) { this.show = show; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public List<Integer> getSeats() { return seats; }
    public void setSeats(List<Integer> seats) { this.seats = seats; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
    
    public String getId() { return Id; }
    public void setId(String Id) { this.Id = Id; }
    
    public String get__v() { return __v; }
    public void set__v(String __v) { this.__v = __v; }
}