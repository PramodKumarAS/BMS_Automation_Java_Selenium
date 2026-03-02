package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

//Singleton and Factory Design Pattern
public class DriverManager {
	
	private static ThreadLocal<WebDriver> driver=new ThreadLocal<>();
	
	private DriverManager() {
		
	}
	
	public static void initDriver(String browser) {
		if(driver.get()==null) {
			WebDriver newDriver;
			
			switch(browser.toLowerCase()) {
				case "chrome":
					newDriver = new ChromeDriver();
					break;
					
				case "firefox":
					newDriver = new FirefoxDriver();
					break;
				
				default:
					throw new RuntimeException("Invalid browser");
			}
			
			driver.set(newDriver);
		}
	}
	
	public static WebDriver getDriver() {

		return driver.get();
	}

    public static void quitDriver() {

        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}