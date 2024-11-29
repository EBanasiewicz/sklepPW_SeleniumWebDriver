package pl.sklepPw.pages;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage {

    @FindAll({@FindBy(xpath = "//tr[contains(@class,'cart-item')]//td[@class = 'product-name']//a")})
    private List<WebElement> productNamesList;

    @FindBy(css = ".checkout-button.button.alt.wc-forward")
    private WebElement checkoutButton;

    private WebDriver driver;
    private ExtentTest test;

    public CartPage(WebDriver driver, ExtentTest test) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.test = test;
    }

    public List<WebElement> getListofProductNames() {
        return productNamesList;
    }

    public CheckoutPage goToCheckout() {
        checkoutButton.click();
        test.log(Status.INFO, "Przejście do checkoutu");
        return new CheckoutPage(driver, test);
    }
}
