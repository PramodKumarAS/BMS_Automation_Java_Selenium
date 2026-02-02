package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import base.BasePage;
import elements.*;

public class RegisterPage extends BasePage<RegisterPage>{

	public RegisterPage(WebDriver driver){
		super(driver);
	}
	
	public Input<RegisterPage> txt_RegisterFullName() {
		return input().setProperties(By.id("name"));
	}
	
	public Input<RegisterPage> txt_RegisterMail(){
		return input().setProperties(By.id("email"));
	}
	public Input<RegisterPage> txt_RegisterPassword() {
		return input().setProperties(By.id("password"));
	}
	public Element<RegisterPage> ele_RegisterAsAdmin() {
		return element().setProperties(By.id("isAdmin"));
	}
	public Element<RegisterPage> ele_RegisterAsPartner () {
		return element().setProperties(By.id("isPartner"));
	}
	public Button<RegisterPage> btn_Register() {
		return button().setProperties(By.xpath("//*[normalize-space(text())='Register']/ancestor::button"));
	}
	public Link<RegisterPage> lnk_LoginNow() {
		return link().setProperties(By.xpath("//*[normalize-space(text())='Login now']"));
	}
}