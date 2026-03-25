package dataProviders;

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
}
