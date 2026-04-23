package api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddTheatreRequest {

    private String name;
    private String address;
    private String email;
    private String phone;
    private String owner;
    @JsonProperty("_id")
    private String id;

    // Getters
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getOwner() { return owner; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    // Setters (REQUIRED for your RequestBuilder)
    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setOwner(String owner) { this.owner = owner; }
}