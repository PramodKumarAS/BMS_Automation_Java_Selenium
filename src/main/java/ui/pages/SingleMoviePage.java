package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ui.base.BasePage;
import ui.components.*;

import java.time.Duration;

public class SingleMoviePage extends BasePage<SingleMoviePage> {
	
	public SingleMoviePage init() {
		return new SingleMoviePage();
	}
	
	public Element<SingleMoviePage> ele_SingleMovieTitle() {
		return element().setProperties(By.xpath("//*[contains(@class,'movie')]//h1"));
	}
	
	public Element<SingleMoviePage> ele_Langauge(){
		return element().setProperties(By.xpath("//*[contains(@class,'movie')]//p[normalize-space(text())='Language:']//span"));
	}
	
	public Element<SingleMoviePage> ele_Genre(){
		return element().setProperties(By.xpath("//*[contains(@class,'movie')]//p[normalize-space(text())='Genre:']//span"));
	}
	public Element<SingleMoviePage> ele_ReleaseDate(){
		return element().setProperties(By.xpath("//*[contains(@class,'movie')]//p[normalize-space(text())='Release Date:']//span"));
	}
	public Element<SingleMoviePage> ele_Duration(){
		return element().setProperties(By.xpath("//*[contains(@class,'movie')]//p[normalize-space(text())='Duration:']//span"));
	}

	public Input<SingleMoviePage> input_ChooseTheDate(){
		return input().setProperties(By.xpath("//*[@type=\"date\"]"));
	}
	
	public Element<SingleMoviePage> ele_MoviePoster(){
		return element().setProperties(By.xpath("//img[@alt='Movie Poster']"));
	}
	
	public Element<SingleMoviePage> ele_NoTheatreAvailableMessage(){
		return element().setProperties(By.xpath("//*[normalize-space(text())='Currently, no theatres available for this movie!']"));
	}
	
	public Button<SingleMoviePage> btn_BookShow(){
		return button().setProperties(By.xpath("//*[normalize-space(text())='Book Show -']/ancestor::button"));
	}
	
	public SingleMoviePage waitForPageLoad() {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(45));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@alt='Movie Poster']")));
		return this;
	}
}