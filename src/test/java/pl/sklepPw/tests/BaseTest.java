package pl.sklepPw.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import pl.sklepPw.utils.DriverFactory;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;


import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    static protected WebDriver driver; //static ze względu na przekazywanie Base drivera w fluent approach
    protected static ExtentSparkReporter sparkReporter;
    protected static ExtentReports extentReports;

    protected ExtentTest test;

    protected static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    private long suiteStartTime;  // Zmienna przechowująca czas startu
    private long suiteEndTime;    // Zmienna przechowująca czas zakończenia

    @BeforeSuite
    public void setUpReport() {
        sparkReporter = new ExtentSparkReporter("index.html");
        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        suiteStartTime = System.currentTimeMillis();
    }

    @AfterSuite
    public void tearDownReport() {

        suiteEndTime = System.currentTimeMillis();
        long totalDuration = suiteEndTime - suiteStartTime;

        // Dodanie czasu całkowitego do raportu ExtentReports
        extentReports.createTest("Podsumowanie testów")
                .info("Całkowity czas wykonania zestawu testów: " + totalDuration + " ms" + " (" + (float) totalDuration / 1000 + ") s");
        extentReports.flush();
    }

    @BeforeMethod
    public void setup() throws IOException {
        driver = DriverFactory.getDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.sklep.pw.edu.pl");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    // Ustawienie ExtentTest dla bieżącego wątku
    public void setExtentTest(ExtentTest test) {
        extentTest.set(test);
    }

    // Pobranie bieżącego ExtentTest
    public ExtentTest getExtentTest() {
        return extentTest.get();
    }

    public WebDriver getDriver() throws IOException {
        return DriverFactory.getDriver();
    }
}

