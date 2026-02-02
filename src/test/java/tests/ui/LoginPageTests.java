package tests.ui;

import org.openqa.selenium.By;
import pages.*;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import base.BaseTest;

public class LoginPageTests extends BaseTest{
    LoginPage loginPage = null;
    RegisterPage registerPage = null;
    ForgetPasswordPage forgetPasswordPage=null;
	
    @BeforeMethod
    public void initPages() {
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
        forgetPasswordPage = new ForgetPasswordPage(driver);
    }

    @Test(priority=1,testName="Login Page UI Test")
    public void TS01_Validate_loginPageTest() {
    	SoftAssert sa = new SoftAssert();

        driver.get(baseURL);
        
        boolean isloginPageHeader_Exists=loginPage.ele_loginPageHeader().exist();
        boolean isEmailTextField_Exists=loginPage.txt_EmailField().exist();
        boolean isPasswordTextField_Exists=loginPage.txt_PasswordField().exist();
        boolean isLoginButton_Exists=loginPage.btn_Login().exist();
        boolean isNewRegisterButton_Exists=loginPage.btn_NewRegister().exist();
        boolean isFogotPasswordButton_Exists=loginPage.btn_FogotPassword().exist();
            
        loginPage.waitForElementVisible(By.xpath("//img[@alt='carousel-left']"),10);
        
        boolean isLeftCarousel_Exists=loginPage.ele_LeftCarousel().exist();
        boolean isRightCarousel_Exists=loginPage.ele_RightCarousel().exist();

        sa.assertTrue(isloginPageHeader_Exists,"Login Page Header is not showing");
        sa.assertTrue(isEmailTextField_Exists,"Email Text Field is not showing");
        sa.assertTrue(isPasswordTextField_Exists,"Password Text Field is not showing");
        sa.assertTrue(isLoginButton_Exists,"Login Button is not showing");
        sa.assertTrue(isNewRegisterButton_Exists,"New Register Button is not showing");
        sa.assertTrue(isFogotPasswordButton_Exists,"Fogot Password Button is not showing");
        sa.assertTrue(isLeftCarousel_Exists,"Left Carousel Image is not showing");
        sa.assertTrue(isRightCarousel_Exists,"Right Carousel Image is not showing");

        sa.assertAll();    
    } 
    
    @Test(priority=2,testName="Register Page UI Test")
    public void TS02_Validate_registerPageTest() {
    	SoftAssert sa = new SoftAssert();

    	driver.get(baseURL);
    	loginPage.btn_NewRegister().click();
    	
        loginPage.waitForElementVisible(By.xpath("//*[normalize-space(text())='Register to BookMyShow']"), 10);

    	boolean isRegisterFullNameTextField_Exists = registerPage.txt_RegisterFullName().exist();
    	boolean isRegisterMailTextField_Exists = registerPage.txt_RegisterMail().exist();
    	boolean isRegisterPasswordTextField_Exists = registerPage.txt_RegisterPassword().exist();
    	boolean isRegisterAsAdminRadioButton_Exists = registerPage.ele_RegisterAsAdmin().exist();
    	boolean isRegisterAsPartnerRadioButton_Exists = registerPage.ele_RegisterAsPartner().exist();
    	boolean isRegisterButton_Exists = registerPage.btn_Register().exist();
    	boolean isLoginNowButton_Exists = registerPage.lnk_LoginNow().exist();

    	sa.assertTrue(isRegisterFullNameTextField_Exists,"Register Full Name Text field is not showing");
    	sa.assertTrue(isRegisterMailTextField_Exists,"Register Email Text field is not showing");
    	sa.assertTrue(isRegisterPasswordTextField_Exists,"Register Password Text field is not showing");
    	sa.assertTrue(isRegisterAsAdminRadioButton_Exists,"Register As Admin Radio button is not showing");
    	sa.assertTrue(isRegisterAsPartnerRadioButton_Exists,"Register As Partner Radio button is not showing");
    	sa.assertTrue(isRegisterButton_Exists,"Register button is not showing");
    	sa.assertTrue(isLoginNowButton_Exists,"Login now link is not showing");
    	
    	sa.assertAll();
    }
    
    @Test(priority=3,testName="Forget Password Page UI Test")
    public void TS03_Validate_forgetPasswordPageTest() {
    	SoftAssert sa = new SoftAssert();

    	driver.get(baseURL);
    	loginPage
    			.btn_FogotPassword().click();

    	boolean isEmailTextField_Exists = forgetPasswordPage.txt_Email().exist();   			                                             			
    	boolean isSendOTPButton_Exists = forgetPasswordPage.btn_SendOTP().exist();
    	boolean isLoginHereLink_Exists = forgetPasswordPage.lnk_LoginHere().exist();
    	
    	sa.assertTrue(isEmailTextField_Exists,"Email Text Field is not showing");
    	sa.assertTrue(isSendOTPButton_Exists,"Send OTP button is not showing");
    	sa.assertTrue(isLoginHereLink_Exists,"Login Here Link is not showing");
    	sa.assertAll();
    }
}