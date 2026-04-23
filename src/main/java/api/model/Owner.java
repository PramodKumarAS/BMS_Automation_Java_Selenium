package api.model;

public class Owner {
    private String _id;
    private String name;
    private String email;
    private String password;
    private String role;
    private int __v;
    private String otp;
    private String otpExpiry;
    
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int get__v() {
		return __v;
	}
	public void set__v(int __v) {
		this.__v = __v;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getOtpExpiry() {
		return otpExpiry;
	}
	public void setOtpExpiry(String otpExpiry) {
		this.otpExpiry = otpExpiry;
	}

    // Getters & Setters
}
