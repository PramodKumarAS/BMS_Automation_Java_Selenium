package pages;


import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.BasePage;
import elements.Button;
import elements.Element;
import elements.Table;

public class AdminHomePage extends BasePage<AdminHomePage>{
	
	public Element<AdminHomePage> ele_Heading(){
		return element().setProperties(By.xpath("//h1[normalize-space(text())='Admin Page']"));
	}
	
	public Button<AdminHomePage> btn_AddMovie(){
		return  button().setProperties(By.xpath("//*[normalize-space(text())='Add Movie']/ancestor::button"));
	}
	
	public Table<AdminHomePage> tbl_Movies(){
		return table().setProperties(By.xpath("//table"));
	}
	
	public boolean rowElementExists(String movieName) {
			
		boolean isElementExists;
		
		try {			
			isElementExists = driver.findElement(By.xpath("//td[normalize-space(text())='" + movieName + "']")).isDisplayed();
			
		}catch(NoSuchElementException e) {
			isElementExists=false;
		}
		
		return isElementExists;	
	}
		
	public void goToNextPageUntilRowFound(String movieName) {
		
		while(!rowElementExists(movieName)) {
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(45));
			WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@aria-label='right']/ancestor::button")));
			ele.click();
		}
	}	
}
