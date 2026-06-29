package reporting;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

	private static ExtentReports extent;
	
	public static ExtentReports getInstance() {
		if(extent==null) {
            String reportPath ="target/extent-reports/ExtentReport.html";
            ExtentSparkReporter spark =new ExtentSparkReporter(reportPath);

            spark.config().setReportName("Automation Test Report");
            spark.config().setDocumentTitle("Selenium TestNG Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);
		}
		
		return extent;
	}
}