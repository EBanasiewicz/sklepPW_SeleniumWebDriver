package pl.sklepPw.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pl.sklepPw.listeners.TestListener;
import pl.sklepPw.pages.CheckoutPage;
import pl.sklepPw.pages.MainPage;
import pl.sklepPw.utils.DriverFactory;

import java.io.IOException;

@Listeners(TestListener.class)
public class CheckoutTest extends BaseTest {

    //Przypadek testowy nr 9 ("Przejście do ekranu płatności")
    @Test(description = "Przejście do checkoutu")
    public void proceedToCheckout() throws IOException {
        //arrange
        ExtentTest test = getExtentTest();
        test.log(Status.INFO, "Session ID: " + ((RemoteWebDriver) DriverFactory.getDriver()).getSessionId() + " | Thread ID: " + Thread.currentThread().getId());
        SoftAssert softAssert = new SoftAssert();
        String searchedProduct = "Kubek PW";

        //act
        CheckoutPage checkoutPage = new MainPage(getDriver(), test).dismissCookiesMessage().performSingleItemSearch(searchedProduct)
                .addToCart()
                .proceedToCart()
                .goToCheckout();

        //assert
        softAssert.assertEquals(checkoutPage.getCurrentUrl(), "https://www.sklep.pw.edu.pl/zamowienie");
        softAssert.assertAll();
    }
}
