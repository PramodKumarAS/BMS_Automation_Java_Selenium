package base;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import api.model.User;
import api.model.UsersList;
import pages.LoginPage;
import utilities.DriverManager;
import utils.ConfigReader;
import utils.TestDataLoader;

@Listeners(listeners.ExtentTestListener.class)
public class BaseTest {

	public WebDriver driver;
	public String baseURL;
	User userData;
	
	@Parameters("browser")
	@BeforeClass
	public void OneTimeSetUp(@Optional("chrome") String browserName) {
		DriverManager.initDriver(browserName);
		driver = DriverManager.getDriver();
		driver.manage().window().maximize();
		baseURL=ConfigReader.get("baseUrl");

		try {
			UsersList users = TestDataLoader.loadUsers("users.json");
			userData = users.getUsers().get(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public void OneTimeTearDown() {
		DriverManager.quitDriver();
	}
	
	public void loginToApp() {
		driver.get(baseURL);
		
		LoginPage loginPage = new LoginPage();

		loginPage
		   .txt_EmailField().setText(userData.getEmail())
           .txt_PasswordField().setText(userData.getPassword())
	       .btn_Login().click();
		
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(45));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Type here to search for movies']")));
	}
	
	public void loginToApp(String userEmail,String userPassword) {
		driver.get(baseURL);
		
		LoginPage loginPage = new LoginPage();
		
		loginPage
		   .txt_EmailField().setText(userEmail)
           .txt_PasswordField().setText(userPassword)
	       .btn_Login().click();		
		
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(45));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@data-menu-id,'user-menu')]")));	
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
