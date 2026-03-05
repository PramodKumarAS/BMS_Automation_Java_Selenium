package pages;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import base.BasePage;
import elements.Button;
import elements.Input;

public class AddMovieModalPage extends BasePage<AddMovieModalPage> {
	
	public Input<AddMovieModalPage> txt_MovieName(){
		return input().setProperties(By.id("movieName"));
	}
	
	public Input<AddMovieModalPage> txt_Description(){
		return input().setProperties(By.id("description"));
	}
	
	public Input<AddMovieModalPage> txt_MovieDuration(){
		return input().setProperties(By.id("duration"));
	}
		
	public AddMovieModalPage selectFromVirtualDropdown(String dropDownId,String optionText) {

		driver.findElement(By.id(dropDownId)).click(); 

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(45));

	    // Wait for drop down to open
	    WebElement scrollContainer = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                    By.cssSelector(".ant-select-dropdown:not(.ant-select-dropdown-hidden)")
	            )
	    );

	    By optionLocator = By.xpath(
	            "//div[@class='ant-select-item-option-content' and text()='" + optionText + "']"
	    );

	    int maxScrolls = 20;

	    for (int i = 0; i < maxScrolls; i++) {

	        List<WebElement> elements = driver.findElements(optionLocator);

	        if (!elements.isEmpty()) {
	            elements.get(0).click();
	            return this;
	        }

	        // Scroll down
	        ((JavascriptExecutor) driver).executeScript(
	                "arguments[0].scrollTop = arguments[0].scrollTop + 200;",
	                scrollContainer
	        );

	        try {
	            Thread.sleep(300); // small wait for rendering
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }

	    throw new NoSuchElementException("Option not found: " + optionText);
	}	
	
	public Input<AddMovieModalPage> txt_ReleaseDate(){
		return input().setProperties(By.id("releaseDate"));
	}	
	
	public Input<AddMovieModalPage> txt_PosterUrl(){
		return input().setProperties(By.id("poster"));
	}		
	
	public Button<AddMovieModalPage> btn_Submit(){
		return  button().setProperties(By.xpath("//*[normalize-space(text())='Submit the Data']/ancestor::button"));
	}	

}
