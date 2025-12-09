package com.selenium.demo.BMSProject.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.selenium.demo.BMSProject.base.BasePage;

public class RegisterPage extends BasePage{

	public RegisterPage(WebDriver driver){
		super(driver);
	}
	
	public WebElement txt_RegisterFullName() {
		return driver.findElement(By.id("name"));
	}
	
	public WebElement txt_RegisterMail(){
		return driver.findElement(By.id("email"));
	}
	public WebElement txt_RegisterPassword() {
		return driver.findElement(By.id("password"));
	}
	public WebElement ele_RegisterAsAdmin() {
		return driver.findElement(By.id("isAdmin"));
	}
	public WebElement ele_RegisterAsPartner () {
		return driver.findElement(By.id("isPartner"));
	}
	public WebElement btn_Register() {
		return driver.findElement(By.xpath("//*[normalize-space(text())='Register']/ancestor::button"));
	}
	public WebElement lnk_LoginNow() {
		return driver.findElement(By.xpath("//*[normalize-space(text())='Login now']"));
	}

}
