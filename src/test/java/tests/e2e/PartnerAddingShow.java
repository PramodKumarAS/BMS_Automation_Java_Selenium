package tests.e2e;

import java.io.IOException;
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

import api.model.ShowModel;
import api.model.User;
import api.model.UsersList;
import base.BaseTest;
import db.MongoConnection;
import db.MongoUtils;
import driver.TestDataLoader;
import pages.AddShowModalPage;
import pages.ListOfShowsModalPage;
import pages.PartnerHomePage;

public class PartnerAddingShow extends BaseTest {
	User userData=null;
	ShowModel showData=null;
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
		
		try {			
			UsersList users = TestDataLoader.loadUsers("users.json");
			userData = users.getUsers().get(1);

			showData = TestDataLoader.loadShows("shows.json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		loginToApp(userData.getEmail(),userData.getPassword());
		mdb_Shows_collection = MongoConnection.connect("test", "shows");
	}
	
	@AfterMethod
	public void tearDown() {
		MongoUtils.deleteOne(mdb_Shows_collection,"name",showData.getShowName());
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
		    .txt_ShowName().setText(showData.getShowName())
		    .txt_ShowDate().setText(showDate)
		    .txt_ShowTime().setText(showData.getShowTime())
		    .txt_TicketPrice().setText(showData.getTicketPrice())
		    .txt_TotalSeats().setText(showData.getTotalSeats())
		    .selectFromVirtualDropdown(showData.getMovieName())
		    .btn_AddTheShow().click();
		
		waitForSeconds(10);
		int showsCountAfter = 
		showsModalPage
			.waitForTableToLoad()
			.tbl_Shows().getRowCount();		
		
		HashMap<String,String> addedShowRowData = 
		showsModalPage
		    .tbl_Shows().getRowRecordByValue(showData.getShowName());
		    
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String showDateUI = todayDate.format(formatter2);
		
		SoftAssert sa = new SoftAssert();
		sa.assertEquals(showsCountAfter, showsCountBefore+1);
		sa.assertEquals(addedShowRowData.get("Show Name"), showData.getShowName());
		sa.assertEquals(addedShowRowData.get("Show Date"), showDateUI);
		sa.assertTrue(addedShowRowData.get("Show Time").contains(showData.getShowTime()));
		sa.assertEquals(addedShowRowData.get("Movie"), showData.getMovieName());
		sa.assertEquals(addedShowRowData.get("Ticket Price"), showData.getTicketPrice());
		sa.assertEquals(addedShowRowData.get("Total Seats"), showData.getTotalSeats());
		sa.assertEquals(addedShowRowData.get("Available Seats"), showData.getTotalSeats());	
		sa.assertAll();		   		
	}
}