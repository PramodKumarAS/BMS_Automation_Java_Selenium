package pages;



import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.BasePage;
import elements.*;

public class HomePage extends BasePage<HomePage> {

	public HomePage(WebDriver driver){
		super(driver);
	}
	
	public Input<HomePage> txt_SearchMovies(){
		return input().setProperties(By.xpath("//input[@placeholder='Type here to search for movies']"));
	}
	
	public Element<HomePage> ele_MoviesPoster(String movieName){
		return element().setProperties(By.xpath("//h3[normalize-space(text())='" + movieName + "']/preceding-sibling::img"));
	}
	
	public Element<HomePage> ele_UserMenu(){
		return element().setProperties(By.xpath("//div[contains(@data-menu-id,'user-menu')]"));
	}
	
	public Element<HomePage> ele_UserProfile(){
		return element().setProperties(By.xpath("//li[contains(@data-menu-id,'profile')]"));
	}
	
	public Element<HomePage> ele_Logout(){
		return element().setProperties(By.xpath("//li[contains(@data-menu-id,'logout')]"));
	}
	
	public Element<HomePage> ele_Movies(){
		return element().setProperties(By.xpath("//*[normalize-space(text())='Movies']"));
	}
	
	public Element<HomePage> ele_Theatres(){
		return element().setProperties(By.xpath("//*[normalize-space(text())='Theatres']"));
	}

	public Button<HomePage> btn_AddMovie(){
		return button().setProperties(By.xpath("//*[normalize-space(text())='Add Movie']/ancestor::button"));
	}
	
	public Button<HomePage> btn_AddTheatre(){
		return button().setProperties(By.xpath("//*[normalize-space(text())='Add Theatre']/ancestor::button"));
	}
	
	public HomePage waitForPageToLoad() {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(45));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@alt='Movie Poster']")));

		return this;
	}
}
