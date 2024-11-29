package pl.sklepPw.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pl.sklepPw.listeners.TestListener;
import pl.sklepPw.pages.MainPage;
import pl.sklepPw.pages.ProductPage;
import pl.sklepPw.pages.ProductListPage;
import pl.sklepPw.utils.DriverFactory;
import pl.sklepPw.utils.SeleniumHelper;

import java.io.IOException;
import java.util.List;

@Listeners(TestListener.class)
public class CartTest extends BaseTest {

    //Przypadek testowy nr 3 ("Dodanie produktu do koszyka")
    @Test(description = "Dodawanie produktu do koszyka")
    public void addToCartSuccess() throws IOException {
        //arrange
        ExtentTest test = getExtentTest();
        test.log(Status.INFO, "Session ID: " + ((RemoteWebDriver) DriverFactory.getDriver()).getSessionId() + " | Thread ID: " + Thread.currentThread().getId());
        String querriedTerm = "kubek";
        String searchedProduct = "Kubek PW";
        ProductListPage searchResultsPage = new MainPage(getDriver(), test).dismissCookiesMessage().performSearch(querriedTerm);
        SoftAssert softAssert = new SoftAssert();

        //act
        List<WebElement> listOfItemsInTheCart = searchResultsPage.addToCart(searchedProduct)
                .goToCart()
                .getListofProductNames();

        //assert
        softAssert.assertEquals(listOfItemsInTheCart.size(), 1, "W koszyku jest niewłaściwa liczba przedmiotów (różna od 1)");
        softAssert.assertEquals(listOfItemsInTheCart.get(0).getText(), searchedProduct, "Nazwa przedmiotu zgodna z dodanym do koszyka");
        softAssert.assertAll();
    }

    //Przypadek testowy nr 6 ("Sprawdzenie zawartości minikoszyka")
    @Test(description = "Sprawdzanie zawartości minikoszyka")
    public void checkContentsOfMinicart() throws IOException {

        //arrange
        ExtentTest test = getExtentTest();
        test.log(Status.INFO, "Session ID: " + ((RemoteWebDriver) DriverFactory.getDriver()).getSessionId() + " | Thread ID: " + Thread.currentThread().getId());
        String querriedTerm = "kubek";
        String searchedProduct = "Kubek PW";
        ProductListPage productListPage = new MainPage(getDriver(), test).dismissCookiesMessage().performSearch(querriedTerm).addToCart(searchedProduct);
        SoftAssert softAssert = new SoftAssert();

        //act
        boolean itemsVisibleInMinicart = productListPage.hooverOnMiniCart().checkIfItemInMinicart(searchedProduct);

        //assert
        softAssert.assertTrue(productListPage.getMiniCart().isEnabled(), "Nie znaleziono minikoszyka");
        softAssert.assertTrue(productListPage.getMiniCart().isDisplayed(), "Minikoszyk nie jest widoczny");
        softAssert.assertTrue(itemsVisibleInMinicart, "Nie znaleziono elementu w koszyku");
        softAssert.assertAll();
    }

    //Przypadek testowy nr 4 ("Dodanie produktu do koszyka — produkt z opcjami — rozmiar, rodzaj")
    @Test(description = "Dodawanie do koszyka produktu z opcjami (rozmiar, typ)")
    public void addToCartProductWithOptionsSuccess() throws IOException {

        //arrange;
        ExtentTest test = getExtentTest();
        test.log(Status.INFO, "Session ID: " + ((RemoteWebDriver) DriverFactory.getDriver()).getSessionId() + " | Thread ID: " + Thread.currentThread().getId());
        SoftAssert softAssert = new SoftAssert();
        String searchedProduct = "Bluza bejsbolówka";
        String quantity = "2";
        String type = "Damska";
        String size = "XL";
        ProductPage productPage = new MainPage(getDriver(), test).dismissCookiesMessage().performSingleItemSearch(searchedProduct);

        //act
        String confirmationMessage = productPage.selectTypeFromTheList(type)
                .selectSizeFromTheList(size)
                .setProductQuantity(quantity)
                .addToCart()
                .getConfirmationMessage();
        List<WebElement> itemsInCart = productPage.proceedToCart()
                .getListofProductNames();

        //assert
        softAssert.assertEquals(confirmationMessage, "Zobacz koszyk\n" + quantity + " × „" + searchedProduct + "” zostało dodanych do koszyka.");
        softAssert.assertEquals(itemsInCart.get(0).getText(), searchedProduct + " - " + type + ", " + size);
        softAssert.assertAll();
    }

    //Przypadek testowy nr 5 ("Dodanie produktu do koszyka — produkt z opcjami — bez wybrania opcji")
    @Test(description = "Dodawanie do koszyka produktu z opcjami (rozmiar, typ) - przypadek negatywny")
    public void addToCartProductWithOptionsFail() throws IOException {

        //arrange
        ExtentTest test = getExtentTest();
        test.log(Status.INFO, "Session ID: " + ((RemoteWebDriver) DriverFactory.getDriver()).getSessionId() + " | Thread ID: " + Thread.currentThread().getId());
        SoftAssert softAssert = new SoftAssert();
        String searchedProduct = "Bluza bejsbolówka";
        ProductPage productPage = new MainPage(getDriver(), test).dismissCookiesMessage().performSingleItemSearch(searchedProduct);

        //act
        productPage.addToCartFail();
        String alertMessage = SeleniumHelper.handleAlert(driver);
        SeleniumHelper.takeScreenshot(driver);

        //assert
        softAssert.assertEquals(alertMessage, "Wybierz opcje produktu przed dodaniem go do koszyka.");
        softAssert.assertAll();
    }
}
