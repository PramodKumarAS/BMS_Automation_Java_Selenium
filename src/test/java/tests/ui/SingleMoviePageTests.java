package tests.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import base.BaseTest;
import pages.HomePage;
import pages.SingleMoviePage;

public class SingleMoviePageTests extends BaseTest{
	
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
		
		SingleMoviePage singleMoviePage = new SingleMoviePage(driver).waitForPageLoad();
		String out_MovieTitle = singleMoviePage.ele_SingleMovieTitle().getText();
		String out_MovieLangauge = singleMoviePage.ele_Langauge().getText();
		String out_MovieGenre = singleMoviePage.ele_Genre().getText();
		String out_MovieReleaseDate = singleMoviePage.ele_ReleaseDate().getText();
		String out_MovieDuration = singleMoviePage.ele_Duration().getText();
		boolean chooseTheDate_DateField_Exists = singleMoviePage.input_ChooseTheDate().exist();
		boolean NoTheareAvailableMsg_Exists = singleMoviePage.ele_NoTheatreAvailableMessage().exist();

		DateTimeFormatter dateFormatter =DateTimeFormatter.ofPattern("MMM dd yyyy");
		String formattedDateToday = LocalDate.now().format(dateFormatter);
		String normalizedDateInUI =out_MovieReleaseDate.replaceAll("(\\d+)(st|nd|rd|th)", "$1");
		
		SoftAssert saAssert = new SoftAssert();
		
		saAssert.assertEquals(out_MovieTitle, "Avengers: Endgame","Movie title is not matching");
		saAssert.assertEquals(out_MovieLangauge, "English","Movie Langauge is not matching");
		saAssert.assertEquals(out_MovieGenre, "Action","Movie Genre is not matching");
		saAssert.assertEquals(normalizedDateInUI, formattedDateToday,"Movie Release Date is not matching");
		saAssert.assertEquals(out_MovieDuration, "126 Minutes","Movie Duration is not matching");
		saAssert.assertTrue(chooseTheDate_DateField_Exists,"Choose the Date field is not displaying");
		saAssert.assertTrue(NoTheareAvailableMsg_Exists,"No Theares Avaialble message is not displaying");	
		saAssert.assertAll();
	}
}