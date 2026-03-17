package base;

import org.testng.annotations.AfterClass;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import api.model.User;
import api.model.UsersList;
import driver.ConfigReader;
import driver.TestDataLoader;
import pages.LoginPage;
import utilities.DriverConfig;
import utilities.DriverFactory;

@Listeners(listeners.ExtentTestListener.class)
public class BaseTest {

	public WebDriver driver;
	public String baseURL;
	
	@Parameters("browser")
	@BeforeClass
	public void OneTimeSetUp(@Optional("chrome") String browserName) {
		
		DriverConfig config = new DriverFactory.Builder()
                .browser(browserName)
                .headless(false)
                .incognito(false)
                .build();

		DriverFactory.initDriver(config);	
		driver = DriverFactory.getDriver();
		
		driver.manage().window().maximize();
		baseURL=ConfigReader.get("baseUrl");

	}
	
	@AfterClass
	public void OneTimeTearDown() {
		DriverFactory.quitDriver();
	}
	
	public void loginToApp() {
		User userData=null;
		try {
			UsersList users = TestDataLoader.loadUsers("users.json");
			userData = users.getUsers().get(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
