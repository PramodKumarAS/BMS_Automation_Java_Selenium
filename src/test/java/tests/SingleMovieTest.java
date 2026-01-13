package tests;

import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import base.BaseTest;
import pages.HomePage;
import pages.SingleMoviePage;

public class SingleMovieTest extends BaseTest{
	
	@BeforeClass
	public void beforeClass() {
		loginToApp();
	}
	
	@Test(priority=1,testName="Validation of single movie page")
	public void singleMovie() {
		
		HomePage homePage = new HomePage(driver).waitForPageToLoad();
		homePage
		   .txt_SearchMovies().setText("Avengers: Endgame")
		   .ele_MoviesPoster("Avengers: Endgame").click();
		
		SingleMoviePage singleMoviePage = new SingleMoviePage(driver);
		String out_MovieTitle = singleMoviePage.ele_SingleMovieTitle().getText();
		String out_MovieLangauge = singleMoviePage.ele_Langauge().getText();
		String out_MovieGenre = singleMoviePage.ele_Genre().getText();
		String out_MovieReleaseDate = singleMoviePage.ele_ReleaseDate().getText();
		String out_MovieDuration = singleMoviePage.ele_Duration().getText();
		boolean chooseTheDate_DateField_Exists = singleMoviePage.input_ChooseTheDate().exist();
		boolean NoTheareAvailableMsg_Exists = singleMoviePage.ele_NoTheatreAvailableMessage().exist();

		SoftAssert saAssert = new SoftAssert();
		
		saAssert.assertEquals(out_MovieTitle, "Avengers: Endgame","Movie title is not matching");
		saAssert.assertEquals(out_MovieLangauge, "English","Movie Langauge is not matching");
		saAssert.assertEquals(out_MovieGenre, "Action","Movie Genre is not matching");
		saAssert.assertEquals(out_MovieReleaseDate, "Jan 13th 2026","Movie Release Date is not matching");
		saAssert.assertEquals(out_MovieDuration, "126 Minutes","Movie Duration is not matching");
		saAssert.assertTrue(chooseTheDate_DateField_Exists,"Choose the Date field is not displaying");
		saAssert.assertTrue(NoTheareAvailableMsg_Exists,"No Theares Avaialble message is not displaying");	
		saAssert.assertAll();
	}
}