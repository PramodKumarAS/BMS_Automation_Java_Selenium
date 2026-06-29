package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

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
    public static WebDriver createDriver(DriverConfig config,String typeOfRun) throws MalformedURLException {

        if(driver.get()==null){

            WebDriver newDriver;

			if(typeOfRun.equalsIgnoreCase("local")){
				switch(config.browser.toLowerCase()) {

					case "chrome":
						ChromeOptions options = new ChromeOptions();
						if(config.headless){
							options.addArguments("--headless=new");
							options.addArguments("--no-sandbox");
							options.addArguments("--disable-dev-shm-usage");
							options.addArguments("--disable-gpu");
							options.addArguments("--window-size=1920,1080");
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
			}else if(typeOfRun.equalsIgnoreCase("remote")){
				switch(config.browser.toLowerCase()) {

					case "chrome":
						ChromeOptions chromeOptions = new ChromeOptions();
						newDriver = new RemoteWebDriver(new URL("http://192.168.1.7:4444"),chromeOptions);
						break;

					case "firefox":
						FirefoxOptions firefoxOptions = new FirefoxOptions();
						newDriver = new RemoteWebDriver(new URL("http://192.168.1.7:4444"),firefoxOptions);
						break;

					case "edge":
						EdgeOptions	edgeOptions = new EdgeOptions();
						newDriver = new RemoteWebDriver(new URL("http://192.168.1.7:4444"),edgeOptions);
						break;

					default:
						throw new RuntimeException("Invalid browser");
				}

				driver.set(newDriver);
			}

        }
        
        return driver.get();
    }
}