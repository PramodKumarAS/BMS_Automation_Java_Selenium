package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {
	
	//SINGLETON -> Private constructor + Private static instance + global access method
	private DriverFactory() {}

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();


	public static WebDriver getDriver() {
	    if(driver.get() == null){
	        throw new RuntimeException("Driver not initialized. Call initDriver() first.");
	    }
	    return driver.get();
	}
	
    public static void quitDriver() {
        if(driver.get()!=null){
            driver.get().quit();
            driver.remove();
        }
    }
     
    //FACTORY -> Method that returns objects based on input
    public static WebDriver createDriver(DriverConfig config) {

        if(driver.get()==null){

            WebDriver newDriver;

            switch(config.browser.toLowerCase()) {

                case "chrome":

                    ChromeOptions options = new ChromeOptions();

                    if(config.headless){
                        options.addArguments("--headless=new");
                    }

                    if(config.incognito){
                        options.addArguments("--incognito");
                    }

                    newDriver = new ChromeDriver(options);
                    break;

                case "firefox":
                    newDriver = new FirefoxDriver();
                    break;

                default:
                    throw new RuntimeException("Invalid browser");
            }

            driver.set(newDriver);
        }
        
        return driver.get();        
    }
}