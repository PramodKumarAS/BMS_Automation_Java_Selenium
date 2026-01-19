package tests.EndToEndTests;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.testng.annotations.Test;
import base.BaseTest;
import pages.*;

public class UserBookingShow extends BaseTest {
	
	@Test(priority=1,testName="Validate user booking a show")
	public void bookShow() {
		HomePage homePage = new HomePage(driver).waitForPageToLoad();
		
		homePage
		   .txt_SearchMovies().setText("Avengers: Endgame")
		   .ele_MoviesPoster("Avengers: Endgame").click();
		
		SingleMoviePage singleMoviePage = new SingleMoviePage(driver).waitForPageLoad();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYY");
		String todayDate = LocalDate.now().format(formatter);
		
		//TODO: Create a show for Today's Date using API
		singleMoviePage
		   .input_ChooseTheDate().setText(todayDate)
		   .btn_BookShow().click();
		
		MovieDetailsPage movieDetailsPage = new MovieDetailsPage(driver);
		
		movieDetailsPage
		   .btn_SelectSeat("12").click()
		   .btn_SelectSeat("13").click()
		   .btn_PayNow().click()
		   .input_Email().setText("pramodkumaras143@gmail.com")
		   .input_CardNumber().setText("4242424242424242")
		   .input_ExpDate().setText("02 / 30")
		   .input_CVC().setText("123")
		   .btn_Pay().click();
		
		//TODO: Validate booked show in MongoDB
		   
	}
}
