package tests;

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

public class PartnerHomePageTest extends BaseTest {
	HomePage homePage;
	
	@BeforeClass
	public void setUp() {
		loginToApp("pkPartner@gmail.com","14036");
	}
	
	@BeforeMethod
	public void initPages() {
		homePage = new HomePage(driver);
	}
	
	@Test (priority=1,testName="Validate Partner Home Page")
	public void TS01_Validate_userHomePageTest() {
				
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(45));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[normalize-space(text())='Partner Page']")));

		boolean isTheatresTab_Exists = homePage.ele_Theatres().exist();		
		boolean isAddTheatreButton_Exists = homePage.btn_AddTheatre().exist();		
		boolean isUserMenu_Exists = homePage.ele_UserMenu().exist();
		
		waitForSeconds(5);
		homePage.ele_UserMenu().click();
		waitForSeconds(5);		
		
		boolean isPartnerProfile_Exists = homePage.ele_UserProfile().exist();
		boolean isPartnerLogout_Exists = homePage.ele_Logout().exist();

		SoftAssert sa = new SoftAssert();
		sa.assertTrue(isTheatresTab_Exists,"Theatres Tab is not showing");
		sa.assertTrue(isAddTheatreButton_Exists,"Add Theatre Button is not showing");
		sa.assertTrue(isUserMenu_Exists,"User Menu is not showing");
		sa.assertTrue(isPartnerProfile_Exists,"Partner Profile is not showing");
		sa.assertTrue(isPartnerLogout_Exists,"Partner Logout is not showing");

		sa.assertAll();
	}
}
