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
import elements.*;

public class AddShowModalPage extends BasePage<AddShowModalPage> {

	public Input<AddShowModalPage> txt_ShowName(){
		return input().setProperties(By.id("name"));
	}
	
	public Input<AddShowModalPage> txt_ShowDate(){
		return input().setProperties(By.id("date"));
	}
	
	public Input<AddShowModalPage> txt_ShowTime(){
		return input().setProperties(By.id("time"));
	}
	
	public Input<AddShowModalPage> txt_TicketPrice(){
		return input().setProperties(By.id("ticketPrice"));
	}

	public Input<AddShowModalPage> txt_TotalSeats(){
		return input().setProperties(By.id("totalSeats"));
	}	

	public AddShowModalPage slct_Movie(String movieName) {
		driver.findElement(By.id("movie")).click(); 
		
		driver.findElement(By.xpath("//*[@title='"+ movieName +"']")).click();
		
		return this;
	}
	
	public AddShowModalPage selectFromVirtualDropdown(String optionText) {

		driver.findElement(By.id("movie")).click(); 

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // Wait for drop down to open
	    WebElement scrollContainer = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                    By.cssSelector(".rc-virtual-list-holder")
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
	
	public Button<AddShowModalPage> btn_AddTheShow(){
		return button().setProperties(By.xpath("//*[normalize-space(text())='Add the Show']/ancestor::button"));
	}
}
