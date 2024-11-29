package pl.sklepPw.pages;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pl.sklepPw.utils.SeleniumHelper;

import java.util.List;


public class ProductListPage {

    private WebDriver driver;
    private ExtentTest test;

    @FindBy(className = "cart-contents")
    private WebElement miniCartIcon;

    @FindBy(className = "widget_shopping_cart_content")
    private WebElement miniCart;

    @FindBy(linkText = "Zobacz koszyk")
    private WebElement goToCartButton;

    @FindBy(className = "woocommerce-info")
    private WebElement searchInfoMessage;

    public ProductListPage(WebDriver driver, ExtentTest test) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.test = test;
    }

    public List<WebElement> returnRelevantSearchResults(String query) {
        By queryXpath = By.xpath("//h2[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'" + query + "')]");
        test.log(Status.INFO, "Zwracanie listy wyników wyszukiwania pasujących do zapytania");
        return driver.findElements(queryXpath);
    }

    public int returnNumberofIrrelevantSearchResults(String query) {
        By queryXpath = By.xpath("//h2[not(contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'" + query + "'))]");
        test.log(Status.INFO, "Zwracanie liczby błędnych wyników wyszukiwania: " + driver.findElements(queryXpath).size());
        return driver.findElements(queryXpath).size();
    }

    public ProductListPage addToCart(String product) {
        By productSelector = By.xpath("//h2[text()='" + product + "']//ancestor::a//following-sibling::a");
        SeleniumHelper.waitForClickable(productSelector, driver);
        driver.findElement(productSelector).click();
        test.log(Status.INFO, "Przedmiot został dodany do koszyka");
        return this;
    }

    public CartPage goToCart() {
        goToCartButton.click();
        test.log(Status.INFO, "Nastąpiło przejście do koszyka");
        return new CartPage(driver, test);
    }

    public ProductListPage hooverOnMiniCart() {
        miniCartIcon.sendKeys("");
        new Actions(driver).moveToElement(miniCartIcon).build().perform();
        test.log(Status.INFO, "Wyświetlenie minikoszyka");
        return this;
    }

    public WebElement getMiniCart() {
        return miniCart;
    }

    public WebElement getItemFromMinicart(String productName) {
        By productInMiniCart = By.xpath("//div[@class = 'widget_shopping_cart_content']//a[text()[contains(.,'" + productName + "')]]");
        test.log(Status.INFO, "Pobranie produktu z koszyka");
        return driver.findElement(productInMiniCart);
    }

    public boolean checkIfItemInMinicart(String searchedProduct) {
        test.log(Status.INFO, "Weryfikacja, czy produkt jest widoczny w minikoszyku");
        SeleniumHelper.waitForVisible(getItemFromMinicart(searchedProduct), driver);
        WebElement test = getItemFromMinicart(searchedProduct);
        return test.isDisplayed();
    }

    public String getSearchInfoMessage() {
        String message = searchInfoMessage.getText();
        test.log(Status.INFO, "Pobieranie tekstu komunikatu: " + message);
        return message;
    }

}
