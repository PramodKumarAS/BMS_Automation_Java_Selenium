package tests.EndToEndTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.testng.annotations.Test;
import base.BaseTest;
import pages.HomePage;
import pages.SingleMoviePage;

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
		   
	}
}
