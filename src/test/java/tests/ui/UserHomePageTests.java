package tests.ui;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import base.BaseTest;
import pages.HomePage;

public class UserHomePageTests extends BaseTest {
	HomePage homePage;
	
	@BeforeClass
	public void setUp() {
		loginToApp();
	}
	
	@BeforeMethod
	public void initPages() {
		homePage = new HomePage(driver);
	}
	
	@Test (priority=1,testName="Validate User Home Page")
	public void TS01_Validate_userHomePageTest() {
				
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(45));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@alt='Movie Poster']")));

		boolean isSearchMovie_Exists = homePage.txt_SearchMovies().exist();
			
		boolean isMoviePoster_Exists = homePage.ele_MoviesPoster("Avengers: Endgame").exist();
		boolean isUserMenu_Exists = homePage.ele_UserMenu().exist();
		
		waitForSeconds(5);
		homePage.ele_UserMenu().click();
		waitForSeconds(5);		
		
		boolean isUserProfile_Exists = homePage.ele_UserProfile().exist();
		boolean isUserLogout_Exists = homePage.ele_Logout().exist();

		SoftAssert sa = new SoftAssert();
		sa.assertTrue(isSearchMovie_Exists,"Search Movie Input is not showing");
		sa.assertTrue(isMoviePoster_Exists,"Movie poster is not showing");
		sa.assertTrue(isUserMenu_Exists,"User Menu is not showing");
		sa.assertTrue(isUserProfile_Exists,"User Profile is not showing");
		sa.assertTrue(isUserLogout_Exists,"User Logout is not showing");

		sa.assertAll();
	}
}
