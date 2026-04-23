package api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TheatreResponse {
    private String name;
    private String address;
    private String  phone;   
    private String email;
    private String owner;
    @JsonProperty("isActive")
    private boolean isActive;
    @JsonProperty("_id")
    private String id;

    private String createdAt;
    private String updatedAt;

    @JsonProperty("__v")
    private int version;

    // Getters
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String  getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getOwner() { return owner; }
    public boolean isActive() { return isActive; }
    public String getId() { return id; }
    public String getCreatedAt() { return createdAt; }
    public String getUpdatedAt() { return updatedAt; }
    public int getVersion() { return version; }

}
