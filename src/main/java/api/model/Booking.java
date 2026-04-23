package api.model;

import java.util.List;

public class Booking {

    private String _id;
    private ShowModel show;
    private UserResponse user;
    private Movie movie;
    private List<Integer> seats;
    private String transactionId;
    private String createdAt;
    private String updatedAt;
    private int __v;

    // Getter & Setter for _id
    public String getId() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    // Getter & Setter for show
    public ShowModel getShow() {
        return show;
    }

    public void setShow(ShowModel show) {
        this.show = show;
    }

    // Getter & Setter for user
    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    // Getter & Setter for seats
    public List<Integer> getSeats() {
        return seats;
    }

    public void setSeats(List<Integer> seats) {
        this.seats = seats;
    }

    // Getter & Setter for transactionId
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    // Getter & Setter for createdAt
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    // Getter & Setter for updatedAt
    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Getter & Setter for __v
    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}
}