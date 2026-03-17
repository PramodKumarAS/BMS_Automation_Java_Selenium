package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {
	
	//SINGLETON
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private DriverFactory() {}

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if(driver.get()!=null){
            driver.get().quit();
            driver.remove();
        }
    }

    //FACTORY
    public static void initDriver(DriverConfig config) {

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
    }

    // BUILDER
    public static class Builder {

        private String browser = "chrome";
        private boolean headless = false;
        private boolean incognito = false;

        public Builder browser(String browser){
            this.browser = browser;
            return this;
        }

        public Builder headless(boolean headless){
            this.headless = headless;
            return this;
        }

        public Builder incognito(boolean incognito){
            this.incognito = incognito;
            return this;
        }

        public DriverConfig build(){
            return new DriverConfig(browser, headless, incognito);
        }
    }

}