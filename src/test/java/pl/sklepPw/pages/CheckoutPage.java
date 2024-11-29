package pl.sklepPw.pages;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage {

    private WebDriver driver;
    private ExtentTest test;

    public CheckoutPage(WebDriver driver, ExtentTest test) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.test = test;
    }

    public String getCurrentUrl() {
        test.log(Status.INFO, "Pobranie aktualnego URL");
        return driver.getCurrentUrl();
    }
}
