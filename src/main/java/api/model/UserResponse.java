package api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {

    @JsonProperty("_id")
    private String id;

    private String name;
    private String email;
    private String role;
    private String otp;
    private String otpExpiry;

    @JsonProperty("__v")
    private int version;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public int getVersion() {
        return version;
    }
    
    public String getOtp() {
        return otp;
    }
    
    public String getOtpExpiry() {
        return otpExpiry;
    }
}