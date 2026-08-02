package tests.e2e;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import config.CredentialsReader;
import org.bson.Document;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.mongodb.client.MongoCollection;

import api.model.Show;
import api.model.User;
import api.model.UsersList;
import base.BaseTest;
import data.MongoConnection;
import data.MongoHelper;
import data.TestDataLoader;
import ui.pages.AddShowModalPage;
import ui.pages.ListOfShowsModalPage;
import ui.pages.PartnerHomePage;

public class PartnerAddShowTest extends BaseTest {
	Show showData=null;
 	PartnerHomePage partnerPage;
	ListOfShowsModalPage showsModalPage;
	AddShowModalPage addShowModalPage;
	MongoCollection<Document> mdb_Shows_collection=null;

	@BeforeMethod
	public void oneTimeSetUp() {
		showData = TestDataLoader.loadShows("shows.json");
		
		loginToApp(CredentialsReader.username("PARTNER_EMAIL"),CredentialsReader.password("PARTNER_PASSWORD"));
		mdb_Shows_collection = MongoConnection.connect("test", "shows");

		partnerPage = new PartnerHomePage();
		showsModalPage = new ListOfShowsModalPage();
		addShowModalPage = new AddShowModalPage();
	}
	
	@AfterMethod
	public void tearDown() {
		MongoHelper.deleteOne(mdb_Shows_collection,"name",showData.getName());
	}
	
	@Test(priority=1,testName="Partner able to add show from partner page")
	public void TS01_Validate_partnerAbleToAddShow() {
		
		int theatreCount = 
		partnerPage
			.waitForPageLoad()
			.tbl_Theatres().getRowCount();
		
		if(theatreCount==0) {
			Assert.fail("No Theatre records found");
		}
		
		partnerPage
		    .btn_AddShows("PVR").click();
		waitForSeconds(5);

		int showsCountBefore = 
		showsModalPage
			.waitForTableToLoad()
		    .tbl_Shows().getRowCount();
		
		showsModalPage
			.btn_AddShow().click();
		
		LocalDate todayDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String showDate = todayDate.format(formatter);
		
		addShowModalPage
		    .txt_ShowName().setText(showData.getName())
		    .txt_ShowDate().setText(showDate)
		    .txt_ShowTime().setText(showData.getTime())
		    .txt_TicketPrice().setText(String.valueOf(showData.getTicketPrice()))
		    .txt_TotalSeats().setText(String.valueOf(showData.getTotalSeats()))
		    .selectFromVirtualDropdown(showData.getMovie())
		    .btn_AddTheShow().click();
		
		int showsCountAfter =
		showsModalPage
			.waitForTableToLoad()
			.tbl_Shows().getRowCount();		
		
		HashMap<String,String> addedShowRowData = 
		showsModalPage
		    .tbl_Shows().getRowRecordByValue(showData.getName());
		    
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String showDateUI = todayDate.format(formatter2);
		
		SoftAssert sa = new SoftAssert();
		sa.assertTrue(showsCountAfter > showsCountBefore, "Shows count should increase after adding a show.");
		sa.assertEquals(addedShowRowData.get("Show Name"), showData.getName(), "Show Name mismatch.");
		sa.assertEquals(addedShowRowData.get("Show Date"), showDateUI, "Show Date mismatch.");
		sa.assertTrue(addedShowRowData.get("Show Time").contains(showData.getTime()), "Show Time mismatch.");
		sa.assertEquals(addedShowRowData.get("Movie"), showData.getMovie(), "Movie name mismatch.");
		sa.assertEquals(Integer.parseInt(addedShowRowData.get("Ticket Price")), showData.getTicketPrice(), "Ticket Price mismatch.");
		sa.assertEquals(Integer.parseInt(addedShowRowData.get("Total Seats")), showData.getTotalSeats(), "Total Seats mismatch.");
		sa.assertEquals(Integer.parseInt(addedShowRowData.get("Available Seats")), showData.getTotalSeats(), "Available Seats should equal Total Seats for a newly added show.");
		sa.assertAll();
	}
}