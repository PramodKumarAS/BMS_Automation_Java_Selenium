package com.selenium.demo.BMSProject.base;


import org.openqa.selenium.*;

public class BasePage {
	public WebDriver driver;
	
	public BasePage(WebDriver driver){
		this.driver=driver;
	}
	
	//Avoid using Thread.Sleep it slows down
	public void waitForSeconds(int seconds) {
		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
