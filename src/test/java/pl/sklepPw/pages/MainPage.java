package pl.sklepPw.pages;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pl.sklepPw.utils.SeleniumHelper;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.ElementNotInteractableException;

public class MainPage {

    @FindBy(className = "search-field")
    private WebElement searchBar;

    @FindBy(linkText = "Moje konto")
    private WebElement myAccountPageButton;

    @FindBy(linkText = "Zamknij")
    private WebElement cookiesCloseButton;

    private WebDriver driver;
    private ExtentTest test;

    public MainPage(WebDriver driver, ExtentTest test) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.test = test;
    }

    public ProductListPage performSearch(String querriedItem) {
        SeleniumHelper.waitForVisible(searchBar, driver);
        searchBar.click();
        searchBar.sendKeys(querriedItem);
        searchBar.sendKeys(Keys.ENTER);
        test.log(Status.INFO, "Wyszukano przedmiot: " + querriedItem);
        return new ProductListPage(driver, test);
    }

    public ProductPage performSingleItemSearch(String querriedItem) {
        SeleniumHelper.waitForVisible(searchBar, driver);
        searchBar.click();
        searchBar.sendKeys(querriedItem);
        searchBar.sendKeys(Keys.ENTER);
        test.log(Status.INFO, "Wyszukano przedmiot (tylko jeden pasujący wynik): " + querriedItem);
        return new ProductPage(driver, test);
    }

    public MyAccountPage goToMyAccountPage() {
        myAccountPageButton.click();
        test.log(Status.INFO, "Przejście do strony Moje Konto");
        return new MyAccountPage(driver, test);
    }

    public MainPage dismissCookiesMessage() {
        try {
            cookiesCloseButton.click();
        } catch (NoSuchElementException | ElementNotInteractableException e) {
        }
        return this;
    }
}
