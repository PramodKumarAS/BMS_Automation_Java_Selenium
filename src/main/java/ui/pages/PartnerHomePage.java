package ui.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ui.base.BasePage;
import ui.components.Button;
import ui.components.Element;
import ui.components.Table;

public class PartnerHomePage extends BasePage<PartnerHomePage> {
	
	public Element<PartnerHomePage> ele_Heading() {
		return element().setProperties(By.xpath("//h1[normalize-space(text())='Partner Page']"));
	}
	
	public Button<PartnerHomePage> btn_AddTheatre() {
		return button().setProperties(By.xpath("//*[normalize-space(text())='Add Theatre']/ancestor::button"));
	}
	
	public Table<PartnerHomePage> tbl_Theatres() {
		return table().setProperties(By.xpath("//table"));
	}	
	
	public Button<PartnerHomePage> btn_AddShows(String showName){
		return button().setProperties(By.xpath("//td[normalize-space(text())='"+ showName + "']/ancestor::tr//*[normalize-space(text())='+ Shows']/ancestor::button"));
	}
	
	public PartnerHomePage waitForPageLoad() {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(45));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class='ant-table-tbody']//tr//td//button")));
		
		return this;
	}
}
