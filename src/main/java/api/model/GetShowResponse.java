package api.model;

public class GetShowResponse {

    private boolean success;
    private String message;
    private ShowResponse data;

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public ShowResponse getData() { return data; }

    public void setSuccess(boolean success) { this.success = success; }
    public void setMessage(String message) { this.message = message; }
    public void setData(ShowResponse data) { this.data = data; }
}