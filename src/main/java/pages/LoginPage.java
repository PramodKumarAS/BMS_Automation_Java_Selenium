package pages;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import base.BasePage;
import elements.*;

public class LoginPage extends BasePage<LoginPage> {
		
	public LoginPage(WebDriver driver){
		super(driver);
	}
	
    public Element<LoginPage> ele_loginPageHeader()  { 
    	return element().setProperties(By.xpath("//h2[normalize-space(text())='🎬 BookMyShow']"));
    };
    
    public Input<LoginPage> txt_EmailField() {
    	return input().setProperties(By.id("email"));
    }
    
    public Input<LoginPage> txt_PasswordField() {
    	return input().setProperties(By.id("password"));
    }
    
    public Button<LoginPage> btn_Login() {
    	return button().setProperties(By.xpath("//button[@type='submit']"));
    }
    public Button<LoginPage> btn_NewRegister() {
    	return button().setProperties(By.xpath("//a[normalize-space(text())='Register']"));
    }
    public Button<LoginPage> btn_FogotPassword() {
    	return button().setProperties(By.xpath("//a[normalize-space(text())='Forgot Password?']"));
    }
    public Element<LoginPage> ele_LeftCarousel() {
    	return element().setProperties(By.xpath("//img[@alt='carousel-left']"));
    }
    public Element<LoginPage> ele_RightCarousel() {
    	return element().setProperties(By.xpath("//img[@alt='carousel-right']"));
    }
	
	public WebElement waitForElementVisible(By locator,int sec) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(sec));		
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));	
	}
}