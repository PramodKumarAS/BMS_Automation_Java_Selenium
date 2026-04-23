package ui.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ui.base.BasePage;
import ui.components.Button;
import ui.components.Element;
import ui.components.Table;

public class ListOfShowsModalPage extends BasePage<ListOfShowsModalPage> {
	
	public Element<ListOfShowsModalPage> ele_Heading(){
		return element().setProperties(By.xpath(".//*[@class='ant-modal-title']"));
	}
	
	public Button<ListOfShowsModalPage> btn_AddShow(){
		return button().setProperties(By.xpath("//*[normalize-space(text())='Add Show']/ancestor::button"));
	}
	
	public Table<ListOfShowsModalPage> tbl_Shows(){
		return table().setProperties(By.xpath("//*[@class='ant-modal-body']//table"));
	}
	
	public Button<ListOfShowsModalPage> btn_CloseModal(){
		return button().setProperties(By.xpath("//button[@aria-label='Close']"));
	}
	
	public ListOfShowsModalPage waitForPageLoad() {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(45));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ant-modal-body']//table")));
		
		return this;
	}
	
	public ListOfShowsModalPage waitForTableToLoad() {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(45));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class='ant-modal-body']//table//tbody//tr//td//*[@aria-label='edit']")));
		
		return this;
	}	
}