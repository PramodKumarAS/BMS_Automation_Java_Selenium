package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.BasePage;
import customElements.Button;
import customElements.Element;
import customElements.Input;

public class MovieDetailsPage extends BasePage<MovieDetailsPage> {

	public MovieDetailsPage(WebDriver driver) {
		super(driver);
	}
	
	public Element<MovieDetailsPage> ele_MovieTitle(){
		return element().setProperties(By.xpath("//*[@class='movie-title-details']//h1"));
	}

	public Element<MovieDetailsPage> ele_TheatreAddress(){
		return element().setProperties(By.xpath("//*[@class='movie-title-details']//p"));
	}
	
	public Element<MovieDetailsPage> ele_ShowName(){
		return element().setProperties(By.xpath("//span[normalize-space(text())='Show Name:']/ancestor::h3"));
	}
	
	public Element<MovieDetailsPage> ele_ShowDateTime(){
		return element().setProperties(By.xpath("//span[normalize-space(text())='Date & Time:']/ancestor::h3"));
	}
	public Element<MovieDetailsPage> ele_ShowTicketPrice(){
		return element().setProperties(By.xpath("//span[normalize-space(text())='Ticket Price:']/ancestor::h3"));
	}
	public Element<MovieDetailsPage> ele_TotalSeats(){
		return element().setProperties(By.xpath("//span[normalize-space(text())='Total Seats:']/ancestor::h3"));
	}
	
	public Element<MovieDetailsPage> ele_AvailableSeats(){
		return element().setProperties(By.xpath("//span[contains(text(),' Available Seats:')]//ancestor::h3"));
	}
	
	public Button<MovieDetailsPage> btn_SelectSeat(String seatNumber){
		return button().setProperties(By.xpath("//button[@class='seat-btn'][normalize-space(text())='"+seatNumber+"']"));
	}
	
	public Button<MovieDetailsPage> btn_BookedSeat(String seatNumber){
		return button().setProperties(By.xpath(".//button[@class='seat-btn booked'][normalize-space(text())='"+seatNumber+"']")); 
	}
	
	public Element<MovieDetailsPage> ele_SelectedSeats(){
		return element().setProperties(By.xpath("//*[normalize-space(text())='Selected Seats:']//span"));
	}
	 
	public Element<MovieDetailsPage> ele_TotalPrice(){
		return element().setProperties(By.xpath("//*[normalize-space(text())='Total Price:']//span"));
	}
	
	public Button<MovieDetailsPage> btn_PayNow(){
		return button().setProperties(By.xpath("//*[normalize-space(text())='Pay Now']/ancestor::button"));
	}
	
	public Input<MovieDetailsPage> input_Email(){
		return input().setProperties(By.xpath("//*[@id='email']"));
	}
	
	public Input<MovieDetailsPage> input_CardNumber(){
		return input().setProperties(By.xpath("//*[@id='card_number']"));
	}
	
	public Input<MovieDetailsPage> input_ExpDate(){
		return input().setProperties(By.xpath("//*[@id='cc-exp']"));
	}
	
	public Input<MovieDetailsPage> input_CVC(){
		return input().setProperties(By.xpath("//*[@id='cc-csc']"));
	}
	
	public Button<MovieDetailsPage> btn_Pay(){
		return button().setProperties(By.xpath("//form//*[contains(text(),'Pay')]"));
	}
	
	public MovieDetailsPage waitForPageLoad() {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(45));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[contains(@class,'seat-ul')]")));
		
		return this;
	}
}
