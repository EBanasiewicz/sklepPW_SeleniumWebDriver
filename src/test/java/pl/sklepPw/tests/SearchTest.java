package pl.sklepPw.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pl.sklepPw.listeners.TestListener;
import pl.sklepPw.pages.MainPage;
import pl.sklepPw.pages.ProductListPage;
import pl.sklepPw.utils.DriverFactory;
import pl.sklepPw.utils.SeleniumHelper;

import java.io.IOException;
import java.util.List;

@Listeners(TestListener.class)
public class SearchTest extends BaseTest {

    //Przypadek testowy nr 1 ("Wyszukanie produktu na stronie — produkt istniejący")
    @Test(description = "Wyszukiwanie zakończone sukcesem (są wyniki)")
    public void searchSuccess() throws IOException {
        //arrange
        ExtentTest test = getExtentTest();
        test.log(Status.INFO, "Session ID: " + ((RemoteWebDriver) DriverFactory.getDriver()).getSessionId() + " | Thread ID: " + Thread.currentThread().getId());
        SoftAssert softAssert = new SoftAssert();
        String querriedTerm = "bluza";

        //act
        ProductListPage searchResultsPage = new MainPage(getDriver(), test).dismissCookiesMessage().performSearch(querriedTerm);
        List<WebElement> correctSearchResults = searchResultsPage.returnRelevantSearchResults(querriedTerm);
        int numOfIncorrectResults = searchResultsPage.returnNumberofIrrelevantSearchResults(querriedTerm);

        //assert
        softAssert.assertNotEquals(correctSearchResults.size(), 0);
        softAssert.assertEquals(numOfIncorrectResults, 0);
        softAssert.assertAll();
    }

    //Przypadek testowy nr 2 ("Wyszukanie produktu na stronie — produkt nieistniejący")
    @Test(description = "Wyszukiwanie produktu - brak wyników wyszukiwania")
    public void searchNoProductsFound() throws IOException {
        //arrange
        ExtentTest test = getExtentTest();
        test.log(Status.INFO, "Session ID: " + ((RemoteWebDriver) DriverFactory.getDriver()).getSessionId() + " | Thread ID: " + Thread.currentThread().getId());
        String querriedTerm = "test12345";

        //act
        ProductListPage searchResultsPage = new MainPage(getDriver(), test).dismissCookiesMessage().performSearch(querriedTerm);

        //assert
        Assert.assertTrue(searchResultsPage.getSearchInfoMessage().contains("Nie znaleziono produktów, których szukasz"));
    }
}