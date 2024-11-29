package pl.sklepPw.pages;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pl.sklepPw.utils.SeleniumHelper;

public class MyAccountPage {

    private WebDriver driver;
    private ExtentTest test;

    public MyAccountPage(WebDriver driver, ExtentTest test) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.test = test;
    }

    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(name = "login")
    private WebElement loginButton;

    @FindBy(css = ".woocommerce-error  > li")
    private WebElement errorMessage;

    @FindBy(xpath = "//nav[@class = 'woocommerce-MyAccount-navigation']//a[text() = 'Wyloguj się']")
    private WebElement logoutButton;


    public MyAccountPage enterUsername(String username) {
        usernameInput.sendKeys(username);
        test.log(Status.INFO, "Została wpisana nazwa użytkownika");
        return this;
    }

    public MyAccountPage enterPassword(String password) {
        passwordInput.sendKeys(password);
        test.log(Status.INFO, "Zostało wpisane hasło");
        return this;
    }

    public MyAccountPage login() {
        loginButton.click();
        test.log(Status.INFO, "Kliknięcie przycisku login");
        return this;
    }

    public WebElement getLogoutButton() {
        SeleniumHelper.waitForClickable(logoutButton, driver);
        test.log(Status.INFO, "Kliknięcie przycisku logout");
        return logoutButton;
    }

    public String getErrorMessage() {
        test.log(Status.INFO, "Pobranie tekstu komunikatu błędu");
        return errorMessage.getText();
    }
}
