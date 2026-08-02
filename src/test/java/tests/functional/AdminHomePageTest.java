package tests.functional;

import java.time.Duration;

import config.CredentialsReader;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import base.BaseTest;
import driver.DriverFactory;
import ui.pages.HomePage;

public class AdminHomePageTest extends BaseTest {
	HomePage homePage;
	
	@BeforeMethod
	public void setUp() {
		loginToApp(CredentialsReader.username("ADMIN_EMAIL"),CredentialsReader.password("ADMIN_PASSWORD"));

		homePage = new HomePage();
	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		if(result.getStatus()==ITestResult.FAILURE) {			
			captureScreenShot("fail1");
		}
	}
	
	@Test (groups = {"smoke"},priority=1,testName="Validate Admin Home Page")
	public void TS01_Validate_userHomePageTest() {
				
		WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(),Duration.ofSeconds(45));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[normalize-space(text())='Admin Page']")));

		boolean isMoviesTab_Exists = homePage.ele_Movies().exist();		
		boolean isTheatresTab_Exists = homePage.ele_Theatres().exist();		
		boolean isUserMenu_Exists = homePage.ele_UserMenu().exist();
		
		waitForSeconds(5);
		homePage.ele_UserMenu().click();
		waitForSeconds(5);		
		
		boolean isAdminProfile_Exists = homePage.ele_UserProfile().exist();
		boolean isAdminLogout_Exists = homePage.ele_Logout().exist();

		SoftAssert sa = new SoftAssert();
		sa.assertTrue(isMoviesTab_Exists,"Movies Tab is not showing");
		sa.assertTrue(isTheatresTab_Exists,"Theatres Tab is not showing");
		sa.assertTrue(isUserMenu_Exists,"User Menu is not showing");
		sa.assertTrue(isAdminProfile_Exists,"Admin Profile is not showing");
		sa.assertTrue(isAdminLogout_Exists,"Admin Logout is not showing");

		sa.assertAll();
	}
}