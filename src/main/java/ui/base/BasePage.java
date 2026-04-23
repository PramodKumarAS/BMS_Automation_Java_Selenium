package ui.base;

import org.openqa.selenium.WebDriver;

import driver.DriverFactory;
import ui.components.*;

public class BasePage<T> {

    protected WebDriver driver;

    public BasePage() {
        this.driver = DriverFactory.getDriver();
    }

    public Input<T> input() {
        return new Input<>((T)this,driver);
    }
    
    public Button<T> button() {
        return new Button<>((T) this,driver);
    }

    public Link<T> link() {
        return new Link<>((T) this,driver);
    }
    
    public Element<T> element(){
    	return new Element<>((T) this,driver);
    }
    
    public Table<T> table(){
    	return new Table<>((T) this,driver);
    }
}