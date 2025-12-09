package com.selenium.demo.BMSProject.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.selenium.demo.BMSProject.BasePage;

public class ForgetPasswordPage extends BasePage {
	public ForgetPasswordPage(WebDriver driver){
		super(driver);
	}
	
	public WebElement txt_Email() {
		return driver.findElement(By.id("email"));
	}
	
	public WebElement btn_SendOTP() {
		return driver.findElement(By.xpath("//*[normalize-space(text())='SEND OTP']"));
	}
	
	public WebElement lnk_LoginHere() {
		return driver.findElement(By.xpath("//*[normalize-space(text())='Login Here']"));
	}
	
}
