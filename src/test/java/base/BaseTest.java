package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

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
}
