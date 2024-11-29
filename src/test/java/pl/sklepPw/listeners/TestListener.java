package pl.sklepPw.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import pl.sklepPw.tests.BaseTest;

public class TestListener extends BaseTest implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {

        String testName = result.getMethod().getDescription();  // Pobranie opisu z adnotacji @Test(description="")

        // Ustawienie niestandardowej nazwy testu
        if (testName == null || testName.isEmpty()) {
            testName = result.getMethod().getMethodName();  // Użycie nazwy metody, jeśli brak opisu
        }

        // Utworzenie testu z niestandardową nazwą w ExtentReports
        ExtentTest test = extentReports.createTest(testName);
        setExtentTest(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        getExtentTest().pass("Test zakończony pomyślnie.");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        getExtentTest().fail("Test zakończony niepowodzeniem. Szczegóły: " + result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        getExtentTest().skip("Test został pominięty.");
    }

    @Override
    public void onFinish(ITestContext context) {
        extentReports.flush();
    }
}