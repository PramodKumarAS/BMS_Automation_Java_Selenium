package listeners;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import driver.DriverFactory;
import reporting.ExtentManager;

public class ExtentTestListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(
            result.getMethod().getMethodName()
        );
        // attach groups as tags
        String[] groups = result.getMethod().getGroups();
        if (groups.length > 0) {
            extentTest.assignCategory(groups);
        }
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().fail(result.getThrowable());
        
        // Capture screenshot and attach to report
        Object testInstance = result.getInstance();
        WebDriver driver = DriverFactory.getDriver();
        if (driver != null) {
            String base64 = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BASE64);
            test.get().addScreenCaptureFromBase64String(base64, "Failure Screenshot");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().skip(result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
