package base;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
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
import config.ConfigReader;
import data.TestDataLoader;
import driver.DriverConfig;
import driver.DriverFactory;
import listeners.ExtentTestListener;
import listeners.RetryListener;
import ui.pages.LoginPage;

@Listeners({RetryListener.class, ExtentTestListener.class})
public class BaseTest {

	public WebDriver driver;
	public String baseURL;
	
	@Parameters("browser")
	@BeforeClass
	public void OneTimeSetUp(@Optional("chrome") String browserName) {

	    boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false"));

	    DriverConfig config = new DriverConfig.DriverConfigBuilder()
	            .browser(browserName)
	            .headless(isHeadless)   // ← now CI controls this
	            .incognito(false)
	            .build();

	    driver = DriverFactory.createDriver(config);
	    driver.manage().window().maximize();
	    baseURL = ConfigReader.get("baseUrl");
	}
	
	@AfterClass
	public void OneTimeTearDown() {
		DriverFactory.quitDriver();
	}
	
	public void loginToApp() {
		User userData=null;
		UsersList users = TestDataLoader.loadUsers("users.json");
		userData = users.getUsers().get(0);
		
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
	
	public void captureScreenShot(String testName) {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File dest = new File("target/screenshots/" +testName+ ".png");
		
		try {
			FileUtils.copyFile(src, dest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
