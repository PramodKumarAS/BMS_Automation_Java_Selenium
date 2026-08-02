package tests.e2e;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import api.builder.RequestBuilder;
import api.endpoints.ShowClient;
import api.model.AddShowRequest;
import api.model.AddShowResponse;
import api.model.Show;
import com.mongodb.client.result.UpdateResult;
import config.ConfigReader;
import constants.AppConstants;
import data.TestDataLoader;
import dev.failsafe.internal.util.Assert;
import io.restassured.RestAssured;
import org.bson.Document;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.mongodb.client.MongoCollection;

import base.BaseTest;
import data.MongoConnection;
import data.MongoHelper;
import driver.DriverFactory;
import ui.pages.HomePage;
import ui.pages.MovieDetailsPage;
import ui.pages.SingleMoviePage;

public class UserBookShowTest extends BaseTest {
	
	HomePage homePage;
	SingleMoviePage singleMoviePage;
	MovieDetailsPage movieDetailsPage;
	public MongoCollection<Document> mdb_Booking_collection=null;
	public MongoCollection<Document> mdb_Shows_collection=null;
	public MongoCollection<Document> mdb_Movies_collection=null;	
	public String bookingShowId = "";
	Show showData        = null;
	String theatreId = null;
	MongoCollection<Document> mdb_TheatresCollection = null;
	MongoCollection<Document> mdb_MoviesCollection  = null;
	ShowClient showClient ;

	@BeforeMethod
	public void setUp() {
		loginToApp();
		
		mdb_Booking_collection = MongoConnection.connect("test", "bookings");
		mdb_Shows_collection = MongoConnection.connect("test", "shows");
		mdb_Movies_collection=MongoConnection.connect("test", "movies");
		showData        = TestDataLoader.loadShows("shows.json");
		mdb_TheatresCollection = MongoConnection.connect(AppConstants.MONGO_DB_NAME, AppConstants.MONGO_THEATRES_COLLECTION);
		mdb_MoviesCollection   = MongoConnection.connect(AppConstants.MONGO_DB_NAME, AppConstants.MONGO_MOVIES_COLLECTION);
		showClient= new ShowClient();

		Document mdb_Theatre = MongoHelper.findOneByAnyParams(mdb_TheatresCollection, "name", AppConstants.SEED_THEATRE_NAME);
		theatreId = mdb_Theatre.getObjectId("_id").toHexString();

		homePage = new HomePage();
		singleMoviePage = new SingleMoviePage();
		movieDetailsPage = new MovieDetailsPage();
	}

	@AfterMethod
	public void tearDown() {
		MongoHelper.deleteAll(mdb_Booking_collection);
		MongoHelper.deleteOne(mdb_Shows_collection,"name",showData.getName());
	}

	@Test(priority=1,testName="Validate user booking a show")
	public void TS01_Validate_UserbookShow() {		
		homePage
		   .txt_SearchMovies().setText("Avengers: Endgame");
		
		waitForSeconds(3);
	
		homePage		
		   .ele_MoviesPoster("Avengers: Endgame").click();
				
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String todayDate = LocalDate.now().format(formatter);
		
		singleMoviePage
		   .waitForPageLoad();

		boolean isBookShowBtn_Exists = singleMoviePage.btn_BookShow().exist();
		
		if(!isBookShowBtn_Exists) {
			//Create a show for Today's Date using DB
			Document mdb_Movies = MongoHelper.findOneByAnyParams(mdb_Movies_collection, "movieName", "Avengers: Endgame");	
			Object movieId = mdb_Movies.get("_id");
			Document show = MongoHelper.findOne(mdb_Shows_collection,"movieId",movieId.toString());

			if(show==null){
				AddShowRequest request = RequestBuilder.buildAddShowRequest(
						showData.getName(),
						todayDate,
						showData.getTime(),
						movieId.toString(),
						showData.getTicketPrice(),
						showData.getTotalSeats(),
						theatreId);

				showClient.addShow(request).assertStatus(200);
			}

			UpdateResult result  =  MongoHelper.updateShowDate(mdb_Shows_collection, movieId.toString(), LocalDate.now());
			Assert.isTrue(result.getModifiedCount()<1,"Show not added");
			DriverFactory.getDriver().navigate().refresh();
			waitForSeconds(5);			
		}
			
		singleMoviePage
//		   .input_ChooseTheDate().setText("2472026")
		   .btn_BookShow().click();

		movieDetailsPage
		   .waitForPageLoad()
		   .btn_SelectSeat("12").click()
		   .btn_SelectSeat("13").click()
		   .btn_PayNow().click();
		
		String bookingURL = DriverFactory.getDriver().getCurrentUrl();

		WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(),Duration.ofSeconds(45));
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
		
		DriverFactory.getDriver().switchTo().defaultContent();
		homePage.waitForPageToLoad();

		DriverFactory.getDriver().get(bookingURL);
		String[] url=bookingURL.split("/");
		bookingShowId = url[url.length-1];
		
		waitForSeconds(5);//Wait for Card to open 

		boolean isSeatNumberBooked_12 = movieDetailsPage.btn_BookedSeat("12").exist();
		boolean isSeatNumberBooked_13 = movieDetailsPage.btn_BookedSeat("13").exist();
		String out_TotalSeats_AfterBooking = movieDetailsPage.ele_TotalSeats().getText();
		String out_AvailableSeats_AfterBooking = movieDetailsPage.ele_AvailableSeats().getText();

		SoftAssert sa = new SoftAssert();
		sa.assertTrue(isSeatNumberBooked_12,"Seat Number 12 is not booked correctly");
		sa.assertTrue(isSeatNumberBooked_13,"Seat Number 13 is not booked correctly");
		sa.assertTrue(out_TotalSeats_AfterBooking.contains(String.valueOf(showData.getTotalSeats())),"Total Seats not displayed correctly after booking");
		sa.assertTrue(out_AvailableSeats_AfterBooking.contains(String.valueOf(showData.getTotalSeats() - 2)),"Available Seats not displayed correctly after booking");
		sa.assertAll();		   
	}
}