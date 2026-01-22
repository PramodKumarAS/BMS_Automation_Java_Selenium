package tests.EndToEndTests;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import base.BaseTest;
import pages.HomePage;
import pages.MovieDetailsPage;
import pages.SingleMoviePage;

public class UserBookingShow extends BaseTest {
	
	@BeforeClass
	public void setUp() {
		loginToApp();
	}
	
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
		
		MovieDetailsPage movieDetailsPage = new MovieDetailsPage(driver).waitForPageLoad();

		movieDetailsPage
		   .btn_SelectSeat("12").click()
		   .btn_SelectSeat("13").click()
		   .btn_PayNow().click();
		
		waitForSeconds(3);//Wait for Card to open need to think something else fix this
		driver.switchTo().frame(0);

		movieDetailsPage
		   .input_Email().setText("pramodkumaras143@gmail.com")
		   .input_CardNumber().setText("6011")
		   .input_CardNumber().setText("0009")
		   .input_CardNumber().setText("9013")
		   .input_CardNumber().setText("9424")
		   .input_ExpDate().setText("11")
		   .input_ExpDate().setText("/")
		   .input_ExpDate().setText("30")
		   .input_CVC().setText("123")
		   .btn_Pay().click();
		
		waitForSeconds(3);
		
		boolean isSeatNumberBooked_12 = movieDetailsPage.btn_BookedSeat("12").exist();
		boolean isSeatNumberBooked_13 = movieDetailsPage.btn_BookedSeat("13").exist();
		String out_TotalSeats_AfterBooking = movieDetailsPage.ele_TotalSeats().getText();
		String out_AvailableSeats_AfterBooking = movieDetailsPage.ele_AvailableSeats().getText();

		SoftAssert sa = new SoftAssert();
		sa.assertTrue(isSeatNumberBooked_12,"Seat Number 12 is not booked correctly");
		sa.assertTrue(isSeatNumberBooked_13,"Seat Number 13 is not booked correctly");
		sa.assertEquals(out_TotalSeats_AfterBooking,"100","Seat Number 13 is not booked correctly");
		sa.assertEquals(out_AvailableSeats_AfterBooking,"98","Seat Number 13 is not booked correctly");
		sa.assertAll();
		//TODO: Validate booked show in MongoDB
		   
	}
}
