package api.model;

public class ResetPasswordRequest {
	private String otp;
	private String password;
	
	public void setOtp(String otp) {
		this.otp=otp;
	}
	
	public String getOtp() {
		return this.otp;
	}

	public void setpassword(String password) {
		this.password=password;
	}
	
	public String getpassword() {
		return this.password;
	}	
	
}