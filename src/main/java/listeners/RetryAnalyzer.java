package listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int count = 0;
    private static final int MAX_RETRY = 2;

    @Override
    public boolean retry(ITestResult result) {
        if (count < MAX_RETRY) {
            count++;
            System.out.println("============================================");
            System.out.println("RETRYING TEST : " + result.getName());
            System.out.println("RETRY ATTEMPT : " + count + " of " + MAX_RETRY);
            System.out.println("FAILURE REASON: " + result.getThrowable().getMessage());
            System.out.println("============================================");
            return true;
        }
        return false;
    }
}