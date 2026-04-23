package tests.e2e;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.bson.Document;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.mongodb.client.MongoCollection;

import api.model.Movie;
import api.model.UsersList;
import base.BaseTest;
import data.MongoConnection;
import data.MongoHelper;
import data.TestDataLoader;
import ui.pages.AddMovieModalPage;
import ui.pages.AdminHomePage;

public class AdminAddMovieTest extends BaseTest {
	
	AdminHomePage adminHomePage =null;
	AddMovieModalPage addMovieModalPage=null;
	UsersList users =null;
	Movie movieData=null;
	MongoCollection<Document> mdb_Shows_collection=null;

	@BeforeClass
	public void beforeClass() {
		users = TestDataLoader.loadUsers("users.json");
		movieData = TestDataLoader.loadMovies("movie.json");
		
		loginToApp(users.getUsers().get(2).getEmail(),users.getUsers().get(2).getPassword());	
		mdb_Shows_collection = MongoConnection.connect("test", "movies");		
	}
	
	@BeforeMethod
	public void setUp() {
		adminHomePage = new AdminHomePage();
		addMovieModalPage = new AddMovieModalPage();
	}
	
	@AfterMethod
	public void tearDown() {
		MongoHelper.deleteOne(mdb_Shows_collection,"movieName",movieData.getMovieName());		
	}
	
	@Test
	public void TS01_Validate_addMovieTest() {
		adminHomePage
			.btn_AddMovie().click();
		
		LocalDate today = LocalDate.now();
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("M/d/yyyy");
		
		String formatDateTodayLocal = today.format(formatter1);
		String formatDateTodayUS = today.format(formatter2);
		
		addMovieModalPage
		   .txt_MovieName().setText(movieData.getMovieName())
		   .txt_Description().setText(movieData.getDescription())
		   .txt_MovieDuration().setText(movieData.getDuration()+"")
		   .selectFromVirtualDropdown("language", movieData.getLanguage())
		   .txt_ReleaseDate().setText(formatDateTodayLocal)
		   .selectFromVirtualDropdown("genre", movieData.getGenre())
		   .txt_PosterUrl().setText(movieData.getPoster())
		   .btn_Submit().click();
		
		adminHomePage		
		   .goToNextPageUntilRowFound(movieData.getMovieName());
			
		HashMap<String,String>rowData = 
		adminHomePage
		   .tbl_Movies().getRowRecordByValue(movieData.getMovieName());
		
		SoftAssert sa = new SoftAssert();
		sa.assertEquals(rowData.get("Movie Name"), movieData.getMovieName(),"Movie name is not matching");
		sa.assertEquals(rowData.get("Description"), movieData.getDescription(),"Description is not matching");
		sa.assertEquals(rowData.get("Duration"), movieData.getDuration() + " Min","Duration is not matching");
		sa.assertEquals(rowData.get("Genre"), movieData.getGenre(),"Genre is not matching");
		sa.assertEquals(rowData.get("Language"), movieData.getLanguage(),"Language is not matching");
		sa.assertEquals(rowData.get("Release Date"), formatDateTodayUS,"Release Date is not matching");
		sa.assertAll();		   				
	}	
}