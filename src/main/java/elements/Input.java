package elements;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Input<T>  {
	
	public WebDriver _driver;
	private WebElement _componentElement=null;
	private By _locator;
	private T _page;
	
	public Input(T page,WebDriver driver){
		_page=page;
		_driver=driver;
	}
	
	public Input<T> setProperties(By locator) {
		this._locator = locator;
		
		return this;
	}
	
	public boolean exist() {
		try {
			_componentElement= _driver.findElement(_locator);
			return _componentElement.isDisplayed();	
		}catch(NoSuchElementException e) {
			return false;
		}		
	}
	
	public T setText(String input) {
		_driver.findElement(_locator).sendKeys(input);
		return _page;
	}
}