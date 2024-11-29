package pl.sklepPw.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pl.sklepPw.listeners.TestListener;
import pl.sklepPw.models.User;
import pl.sklepPw.pages.MainPage;
import pl.sklepPw.pages.MyAccountPage;
import pl.sklepPw.utils.DriverFactory;

import java.io.IOException;

@Listeners(TestListener.class)
public class LoginTest extends BaseTest {

    //Przypadek testowy nr 7 ("Logowanie — z poprawnymi danymi")
    @Test(description = "Logowanie z poprawnymi danymi", enabled = true)
    public void loginWithCorrectData() throws IOException {
        //arrange
        ExtentTest test = getExtentTest();
        test.log(Status.INFO, "Session ID: " +
                ((RemoteWebDriver) DriverFactory.getDriver()).getSessionId() +
                " | Thread ID: " + Thread.currentThread().getId());
        User user = new User();
        SoftAssert softAssert = new SoftAssert();

        //act
        MyAccountPage myAccountPage = new MainPage(getDriver(), test)
                .goToMyAccountPage()
                .enterUsername(user.getEmailAddress())
                .enterPassword(user.getPassword())
                .login();

        //assert
        softAssert.assertTrue(myAccountPage.getLogoutButton().isDisplayed());
        softAssert.assertAll();
    }

    //Przypadek testowy nr 8 ("Logowanie — z niepoprawnymi danymi")
    @Test(description = "Logowanie z niepoprawnym hasłem", enabled = true)
    public void loginWithIncorrectPassword() throws IOException {
        //arrange
        ExtentTest test = getExtentTest();
        test.log(Status.INFO, "Session ID: " + ((RemoteWebDriver) DriverFactory.getDriver()).getSessionId() + " | Thread ID: " + Thread.currentThread().getId());
        User user = new User();

        //act
        MyAccountPage myAccountPage = new MainPage(getDriver(), test)
                .goToMyAccountPage()
                .enterUsername(user.getEmailAddress())
                .enterPassword("test")
                .login();
        //assert
        Assert.assertTrue(myAccountPage.getErrorMessage().contains("wpisana nazwa użytkownika lub hasło jest nieprawidłowe"));
    }
}
