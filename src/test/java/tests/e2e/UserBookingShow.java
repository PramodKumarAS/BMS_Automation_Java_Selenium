package tests.e2e;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.bson.Document;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.mongodb.client.MongoCollection;

import api.service.UserApiService;
import base.BaseTest;
import db.MongoConnection;
import db.MongoUtils;
import pages.HomePage;
import pages.MovieDetailsPage;
import pages.SingleMoviePage;

public class UserBookingShow extends BaseTest {
	
	public MongoCollection<Document> mdb_Booking_collection=null;
	public MongoCollection<Document> mdb_Shows_collection=null;
	public String bookingShowId = "697c1c7f0476ba2e220e7476";
	
	@BeforeClass
	public void setUp() {
		loginToApp();
		mdb_Booking_collection = MongoConnection.connect("test", "bookings");
		mdb_Shows_collection = MongoConnection.connect("test", "shows");
	}
	
	@AfterClass
	public void tearDown() {
		MongoUtils.deleteAll(mdb_Booking_collection);
		MongoUtils.updateArrayFieldToEmpty(mdb_Shows_collection,bookingShowId , "bookedSeats");
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
		
		singleMoviePage
		   .input_ChooseTheDate().setText(todayDate);
		
		boolean isBookShowBtn_Exists = singleMoviePage.btn_BookShow().exist();
		
		if(!isBookShowBtn_Exists) {
			//Create a show for Today's Date using API
			UserApiService userAPI = new UserApiService();
			userAPI.postUser();
			driver.navigate().refresh();
			waitForSeconds(5);
		}
			
		singleMoviePage
		   .input_ChooseTheDate().setText(todayDate)
		   .btn_BookShow().click();

		MovieDetailsPage movieDetailsPage = new MovieDetailsPage(driver).waitForPageLoad();

		movieDetailsPage
		   .btn_SelectSeat("12").click()
		   .btn_SelectSeat("13").click()
		   .btn_PayNow().click();
		
		String bookingURL = driver.getCurrentUrl();
		
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(45));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));
		waitForSeconds(5);//Wait for Card to open 
		
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
		
		driver.switchTo().defaultContent();
		homePage.waitForPageToLoad();
		
		driver.get(bookingURL);
		waitForSeconds(5);//Wait for Card to open 

		boolean isSeatNumberBooked_12 = movieDetailsPage.btn_BookedSeat("12").exist();
		boolean isSeatNumberBooked_13 = movieDetailsPage.btn_BookedSeat("13").exist();
		String out_TotalSeats_AfterBooking = movieDetailsPage.ele_TotalSeats().getText();
		String out_AvailableSeats_AfterBooking = movieDetailsPage.ele_AvailableSeats().getText();

		SoftAssert sa = new SoftAssert();
		sa.assertTrue(isSeatNumberBooked_12,"Seat Number 12 is not booked correctly");
		sa.assertTrue(isSeatNumberBooked_13,"Seat Number 13 is not booked correctly");
		sa.assertTrue(out_TotalSeats_AfterBooking.contains("250"),"Total Seats not displayed correctly after booking");
		sa.assertTrue(out_AvailableSeats_AfterBooking.contains("248"),"Available Seats not displayed correctly after booking");
		sa.assertAll();
		
		//TODO: Validate booked show in MongoDB
		   
	}
}
