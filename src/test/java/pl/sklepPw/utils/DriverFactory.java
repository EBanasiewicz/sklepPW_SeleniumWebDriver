package pl.sklepPw.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.IOException;

public class DriverFactory {

    private static final Logger logger = LogManager.getLogger();
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() throws IOException {

        String name = PropertiesLoader.loadProperty("browser.name");

        if (driver.get() == null) {
            if (name.equals("firefox")) {
                logger.info("Testy zostaną uruchomione w: Firefox");
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments("-width=" + PropertiesLoader.loadProperty("browser.width"));
                options.addArguments("-height=" + PropertiesLoader.loadProperty("browser.height"));
                options.addArguments("--disable-web-security");
                options.addArguments("--no-proxy-server");
                options.addArguments("--headless");
                driver.set(new FirefoxDriver(options));

            } else {
                logger.info("Testy zostaną uruchomione w: Chrome");
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("--disable-web-security");
                options.addArguments("--no-proxy-server");
                options.addArguments("--headless=new");
                options.addArguments("--window-size=" + PropertiesLoader.loadProperty("browser.width") + "," + PropertiesLoader.loadProperty("browser.height"));
                driver.set(new ChromeDriver(options));
            }
        }
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
