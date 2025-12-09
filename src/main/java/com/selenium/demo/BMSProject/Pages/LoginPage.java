package com.selenium.demo.BMSProject.Pages;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.selenium.demo.BMSProject.base.BasePage;

public class LoginPage extends BasePage {
		
	public LoginPage(WebDriver driver){
		super(driver);
	}
	
    public WebElement ele_loginPageHeader()  { 
    	return driver.findElement(By.xpath("//h2[normalize-space(text())='🎬 BookMyShow']"));
    };
    
    public WebElement txt_EmailField() {
    	return driver.findElement(By.id("email"));
    }
    
    public WebElement txt_PasswordField() {
    	return driver.findElement(By.id("password"));
    }
    
    public WebElement btn_Login() {
    	return driver.findElement(By.xpath("//button[@type='submit']"));
    }
    public WebElement btn_NewRegister() {
    	return driver.findElement(By.xpath("//a[normalize-space(text())='Register']"));
    }
    public WebElement btn_FogotPassword() {
    	return driver.findElement(By.xpath("//a[normalize-space(text())='Forgot Password?']"));
    }
    public WebElement ele_LeftCarousel() {
    	return driver.findElement(By.xpath("//img[@alt='carousel-left']"));
    }
    public WebElement ele_RightCarousel() {
    	return driver.findElement(By.xpath("//img[@alt='carousel-right']"));
    }

    public boolean Exists() {
    	return true;
    }
    
	
	public WebElement waitForElementVisible(By locator,int sec) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(sec));		
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));	
	}
}
