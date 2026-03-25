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
    
    public static class DriverConfigBuilder {

        private String browser = "chrome";
        private boolean headless = false;
        private boolean incognito = false;

        public DriverConfigBuilder browser(String browser){
            this.browser = browser;
            return this;
        }

        public DriverConfigBuilder headless(boolean headless){
            this.headless = headless;
            return this;
        }

        public DriverConfigBuilder incognito(boolean incognito){
            this.incognito = incognito;
            return this;
        }

        public DriverConfig build(){
            return new DriverConfig(browser, headless, incognito);
        }
    }    
}