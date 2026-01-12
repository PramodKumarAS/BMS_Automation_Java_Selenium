package tests;

import org.testng.annotations.*;

import base.BaseTest;
import pages.HomePage;

public class UserE2E extends BaseTest{
	
	@BeforeClass
	public void beforeClass() {
		loginToApp();
	}
	
	@Test(priority=1,testName="User Booking a show")
	public void bookShow() {
		
		HomePage homePage = new HomePage(driver);
		homePage
		   .txt_SearchMovies().setText("Avengers: Endgame")
		   .ele_MoviesPoster("Avengers: Endgame").click();
		
		
	}
}
