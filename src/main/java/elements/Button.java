package elements;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Button<T> {
	private WebDriver _driver;
	private By _locator;
	private WebElement _componentElement;
	private T _page;
	
	public Button(T page,WebDriver driver){
		_page=page;
		_driver=driver;
	}
	
	public Button<T> setProperties(By locator){
		_locator=locator;
		
		return this;
	}
	
	public boolean exist() {
		try {
			_componentElement=_driver.findElement(_locator);
			return _componentElement.isDisplayed();
		}catch(NoSuchElementException e) {
			return false;
		}
	}
	
	public T click() {
		_componentElement=_driver.findElement(_locator);
		_componentElement.click();
		
		return _page;
	}
}