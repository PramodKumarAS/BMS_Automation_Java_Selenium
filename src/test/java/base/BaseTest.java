package base;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import config.CredentialsReader;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import config.ConfigReader;
import data.TestDataLoader;
import driver.DriverConfig;
import driver.DriverFactory;
import ui.pages.LoginPage;

public class BaseTest {

	public String baseURL;
	
	@Parameters({"browserType","typeOfRun"})
	@BeforeMethod
	public void OneTimeSetUp(@Optional("chrome") String browserName,@Optional("local") String typeOfRun) throws MalformedURLException {

		browserName = System.getProperty("browser",browserName);
		typeOfRun   = System.getProperty("runType",typeOfRun);

	    boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false"));

	    DriverConfig config = new DriverConfig.DriverConfigBuilder()
	            .browser(browserName)
	            .headless(isHeadless)
	            .incognito(false)
	            .build();
		System.out.println("SETUP   " + Thread.currentThread().getId());

	    DriverFactory.createDriver(config,typeOfRun);

	    DriverFactory.getDriver().manage().window().maximize();
	    baseURL = ConfigReader.get("baseUrl");
	}
	
	@AfterMethod
	public void OneTimeTearDown() {
		System.out.println("TEARDOWN " + Thread.currentThread().getId());

		DriverFactory.quitDriver();
	}
	
	public void loginToApp() {
		DriverFactory.getDriver().get(baseURL);

		LoginPage loginPage = new LoginPage();

		loginPage
		   .txt_EmailField().setText(CredentialsReader.username("USER_EMAIL"))
           .txt_PasswordField().setText(CredentialsReader.password("USER_PASSWORD"))
	       .btn_Login().click();

		WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(),Duration.ofSeconds(45));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Type here to search for movies']")));
	}

	public void loginToApp(String userEmail,String userPassword) {
		DriverFactory.getDriver().get(baseURL);

		LoginPage loginPage = new LoginPage();

		loginPage
		   .txt_EmailField().setText(userEmail)
           .txt_PasswordField().setText(userPassword)
	       .btn_Login().click();

		WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(),Duration.ofSeconds(45));
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
		TakesScreenshot ts = (TakesScreenshot) DriverFactory.getDriver();
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