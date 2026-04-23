package api.model;

public class BookShowResponse {

    private boolean success;
    private String message;
    private BookingData data;

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public BookingData getData() { return data; }
    public void setData(BookingData data) { this.data = data; }
}