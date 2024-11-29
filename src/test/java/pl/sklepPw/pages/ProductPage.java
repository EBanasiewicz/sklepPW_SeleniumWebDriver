package pl.sklepPw.pages;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import pl.sklepPw.utils.SeleniumHelper;

public class ProductPage {

    @FindBy(id = "pa_rozmiar")
    private WebElement sizeSelect;

    @FindBy(id = "pa_rodzaj")
    private WebElement typeSelect;

    @FindBy(name = "quantity")
    private WebElement quantityInput;

    @FindBy(xpath = "//button[@class = 'single_add_to_cart_button button alt']")
    private WebElement submitButtonClickable;

    @FindBy(xpath = "//button[@class = 'single_add_to_cart_button button alt disabled wc-variation-selection-needed']")
    private WebElement submitButtonUnclickable;

    @FindBy(xpath = "//div[@class = 'woocommerce-message']")
    private WebElement confirmationMessage;

    @FindBy(linkText = "Zobacz koszyk")
    private WebElement goToCartButton;

    private WebDriver driver;
    private ExtentTest test;

    public ProductPage(WebDriver driver, ExtentTest test) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.test = test;
    }

    public ProductPage selectSizeFromTheList(String visibleText) {
        Select select = new Select(sizeSelect);
        select.selectByVisibleText(visibleText);
        test.log(Status.INFO, "Został wybrany rozmiar produktu z listy select: " + visibleText);
        return this;
    }

    public ProductPage selectTypeFromTheList(String visibleText) {
        Select select = new Select(typeSelect);
        select.selectByVisibleText(visibleText);
        test.log(Status.INFO, "Został wybrany typ produktu z listy select: " + visibleText);
        return this;
    }

    public ProductPage setProductQuantity(String quantity) {
        quantityInput.clear();
        quantityInput.sendKeys(quantity);
        test.log(Status.INFO, "Ustawianie ilości sztuk: " + quantity);
        return this;
    }

    public ProductPage addToCart() {
        SeleniumHelper.waitForClickable(submitButtonClickable, driver);
        submitButtonClickable.click();
        test.log(Status.INFO, "Produkt został dodany do koszyka");
        return this;
    }

    public ProductPage addToCartFail() {
        test.log(Status.INFO, "Dodawanie do koszyka produktu bez wybrania opcji");
        submitButtonUnclickable.click();
        return this;
    }

    public String getConfirmationMessage() {
        String message = confirmationMessage.getText();
        test.log(Status.INFO, "Pobieranie tekstu komunikatu błędu: " + message);
        return message;
    }

    public CartPage proceedToCart() {
        goToCartButton.click();
        test.log(Status.INFO, "Nastąpiło przejście do koszyka");
        return new CartPage(driver, test);
    }
}
