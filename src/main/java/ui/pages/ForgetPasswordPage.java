package ui.pages;

import org.openqa.selenium.By;

import ui.base.BasePage;
import ui.components.*;

public class ForgetPasswordPage extends BasePage<ForgetPasswordPage> {
	
	public Input<ForgetPasswordPage> txt_Email() {
	    return input().setProperties(By.id("email"));
	}

	public Button<ForgetPasswordPage> btn_SendOTP() {
	    return button().setProperties(By.xpath("//*[normalize-space(text())='SEND OTP']"));
	}
	
	public Link<ForgetPasswordPage> lnk_LoginHere() {
		return link().setProperties(By.xpath("//*[normalize-space(text())='Login Here']"));
	}
}