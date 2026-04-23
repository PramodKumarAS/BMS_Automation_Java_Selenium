package tests.e2e;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

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
	User userData=null;
	Show showData=null;
 	PartnerHomePage partnerPage;
	ListOfShowsModalPage showsModalPage;
	AddShowModalPage addShowModalPage;
	MongoCollection<Document> mdb_Shows_collection=null;

	@BeforeMethod
	public void setUp() {
		partnerPage = new PartnerHomePage();
		showsModalPage = new ListOfShowsModalPage();
		addShowModalPage = new AddShowModalPage();
	}
	
	@BeforeClass
	public void oneTimeSetUp() {
		
		UsersList users = TestDataLoader.loadUsers("users.json");
		userData = users.getUsers().get(1);

		showData = TestDataLoader.loadShows("shows.json");
		
		loginToApp(userData.getEmail(),userData.getPassword());
		mdb_Shows_collection = MongoConnection.connect("test", "shows");
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
		
		waitForSeconds(10);
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
		sa.assertEquals(showsCountAfter, showsCountBefore+1);
		sa.assertEquals(addedShowRowData.get("Show Name"), showData.getName());
		sa.assertEquals(addedShowRowData.get("Show Date"), showDateUI);
		sa.assertTrue(addedShowRowData.get("Show Time").contains(showData.getTime()));
		sa.assertEquals(addedShowRowData.get("Movie"), showData.getMovie());
		sa.assertEquals(addedShowRowData.get("Ticket Price"), showData.getTicketPrice());
		sa.assertEquals(addedShowRowData.get("Total Seats"), showData.getTotalSeats());
		sa.assertEquals(addedShowRowData.get("Available Seats"), showData.getTotalSeats());	
		sa.assertAll();		   		
	}
}