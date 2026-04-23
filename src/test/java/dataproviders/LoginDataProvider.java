package dataproviders;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;

public class LoginDataProvider {

	@DataProvider (name="login-data-provider")
	public Object[][] loginData(){
		return new Object[][] {
	        {
	            "pkUser@gmail.com",
	            "14036",
	            "UserPramod",
	            By.xpath("//input[@placeholder='Type here to search for movies']")
	        },
	        {
	            "pkPartner@gmail.com",
	            "14036",
	            "PartnerPramod",
	            By.xpath("//*[normalize-space(text())='Add Theatre']/ancestor::button")
	        },
	        {
	            "pkAdmin@gmail.com",
	            "14036",
	            "AdminPramod",
	            By.xpath("//*[normalize-space(text())='Add Movie']/ancestor::button")
	        }			
		};
	}
	
	@DataProvider (name="invalidLoginData")
	public Object[][] invalidLoginData(){
		return new Object[][] {
	        {"praod@gmail.com", "14036", "invalid email"},
	        {"pkUser@gmail.com", "!212!!", "invalid password"}			
		};
	}
	
	@DataProvider (name="invalidLoginInputs")
	public Object[][] ivalidInputs(){
		return new Object[][] {
	        {null, "pass123", "Email and password are required"},   // missing email
	        {"pkUser@gmail.com", null, "Email and password are required"}, // missing password
	        {"", "pass123", "Email and password are required"},     // empty email
	        {"pkUser@gmail.com", "", "Email and password are required"}, // empty password
	        {"abc", "pass123", "Invalid email format"}              // invalid email
		};
	}
	
	@DataProvider(name = "invalidRegisterInputs")
	public Object[][] invalidRegisterInputs() {
	    return new Object[][] {

	        // 🔴 1. Required field validation
	        {null, "test@gmail.com", "Pass@123", "All fields are required"},   // name missing
	        {"Pramod", null, "Pass@123", "All fields are required"},           // email missing
	        {"Pramod", "test@gmail.com", null, "All fields are required"},     // password missing

	        // 🔴 2. Empty / whitespace validation
	        {"", "test@gmail.com", "Pass@123", "All fields are required"},      
	        {"   ", "test@gmail.com", "Pass@123", "Fields cannot be empty"},   
	        {"Pramod", "   ", "Pass@123", "Fields cannot be empty"},           
	        {"Pramod", "test@gmail.com", "   ", "Fields cannot be empty"},     

	        // 🔴 3. Email format validation
	        {"Pramod", "abc", "Pass@123", "Invalid email format"},
	        {"Pramod", "abc@", "Pass@123", "Invalid email format"},
	        {"Pramod", "abc@gmail", "Pass@123", "Invalid email format"},

	        // 🔴 4. Gmail domain validation
	        {"Pramod", "test@yahoo.com", "Pass@123", "Only Gmail accounts are allowed"},
	        {"Pramod", "test@outlook.com", "Pass@123", "Only Gmail accounts are allowed"},

	        // 🔴 5. Password length validation
	        {"Pramod", "test@gmail.com", "Pas@1", "Password must be at least 6 characters"},

	        // 🔴 5. Password strength validation
	        {"Pramod", "test@gmail.com", "password", "Password must contain uppercase and special character"},
	        {"Pramod", "test@gmail.com", "PASSWORD", "Password must contain uppercase and special character"},
	        {"Pramod", "test@gmail.com", "Pass123", "Password must contain uppercase and special character"}
	    };
	}
}
