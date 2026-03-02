package elements;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Table<T> {
	public WebDriver _driver;
	private WebElement _componentElement=null;
	private By _locator;
	private T _page;
	
	public Table(T page,WebDriver driver){
		_driver=driver;
		_page=page;
	}
	
	public Table<T> setProperties(By locator) {
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
	
	public int getRowCount() {
		List<WebElement> recordCount = _driver.findElements(By.xpath("//table//tbody//tr"));
		
		return recordCount.size();
	}
	
	public HashMap<String,String> getRowRecordByValue(String colValue){
		HashMap<String,String> rowData = new HashMap<>();
		List<WebElement> columnHeaders = _driver.findElements(By.xpath("//thead"));
		List<WebElement> columnRowValues = _driver.findElements(By.xpath("//td[normalize-space(text())='"+ colValue +"']/ancestor::tr//td"));
		
		for(int i=0; i<columnRowValues.size(); i++) {
			if(!columnHeaders.get(i).getText().equals("Action")) {				
				rowData.put(columnHeaders.get(i).getText(), columnRowValues.get(i).getText()); 
			}
		}
		
		return rowData;
	}
	
}
