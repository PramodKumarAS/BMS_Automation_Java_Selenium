package tests.ui;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import base.BaseTest;
import dataProviders.LoginDataProvider;
import pages.HomePage;
import pages.LoginPage;

public class LoginWithDifferentUsersTests extends BaseTest{
    LoginPage loginPage = null;
    HomePage homePage =null;
    
    @BeforeMethod
    public void initPages() {
        loginPage = new LoginPage();
        homePage = new HomePage();
    }
    
    @Test (groups = {"smoke"},dataProvider="login-data-provider",dataProviderClass=LoginDataProvider.class)
	public void TS01_login(String userEmail,String password,String userName,By waitLocator) {
		
        driver.get(baseURL);
		
		loginPage
		   .txt_EmailField().setText(userEmail)
		   .txt_PasswordField().setText(password)
	       .btn_Login().click();
		
		WebDriverWait wait1 = new WebDriverWait(driver,Duration.ofSeconds(45));
		wait1.until(ExpectedConditions.visibilityOfElementLocated(waitLocator));
		
		String user1Name = homePage
		   .ele_UserMenu().getText();
		
		SoftAssert sa = new SoftAssert();
		sa.assertEquals(user1Name, userName,"User Name is not displaying as expected");
		
	}
}
