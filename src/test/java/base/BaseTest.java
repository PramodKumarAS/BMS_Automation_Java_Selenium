package base;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.annotations.Listeners;

import pages.LoginPage;

@Listeners(listeners.ExtentTestListener.class)
public class BaseTest {

	public WebDriver driver;
	public String baseURL;

	@BeforeClass
	public void OneTimeSetUp() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		baseURL="https://bookmyshow0101.netlify.app/login";
	}
	
	@AfterClass
	public void OneTimeTearDown() {
		if(driver!=null) {		
			driver.quit();
		}
	}
	
	public void loginToApp() {
		driver.get(baseURL);
		
		LoginPage loginPage = new LoginPage(driver);
		
		loginPage
		   .txt_EmailField().setText("pkUser@gmail.com")
           .txt_PasswordField().setText("14036")
	       .btn_Login().click();
		
//		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(45));
//		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//input[@placeholder='Type here to search for movies']")));
	}
	
	public void loginToApp(String userEmail,String userPassword) {
		driver.get(baseURL);
		
		LoginPage loginPage = new LoginPage(driver);
		
		loginPage
		   .txt_EmailField().setText(userEmail)
           .txt_PasswordField().setText(userPassword)
	       .btn_Login().click();		
	}
	
	public void waitForSeconds(int seconds) {
	    try {
	        Thread.sleep(seconds * 1000L);
	    } catch (InterruptedException e) {
	        Thread.currentThread().interrupt();
	        throw new RuntimeException("Thread interrupted while waiting", e);
	    }
	}
}
