package utilities;

public class DriverConfig {

    String browser;
    boolean headless;
    boolean incognito;

    public DriverConfig(String browser, boolean headless, boolean incognito) {
        this.browser = browser;
        this.headless = headless;
        this.incognito = incognito;
    }
}