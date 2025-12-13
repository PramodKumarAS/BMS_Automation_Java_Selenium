package customElements;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Element<T> {

	public WebDriver _driver;
	private WebElement _componentElement=null;
	private By _locator;
	private T _page;
	
	public Element(T page,WebDriver driver){
		_page=page;
		_driver=driver;
	}
	
	public Element<T> setProperties(By locator) {
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
	
	public T click() {
		_componentElement=_driver.findElement(_locator);
		_componentElement.click();
		
		return _page;
	}
}