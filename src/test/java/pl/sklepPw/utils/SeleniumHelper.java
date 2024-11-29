package pl.sklepPw.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SeleniumHelper {

    public static void waitForClickable(WebElement element, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForClickable(By locator, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void waitForVisible(WebElement element, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForVisible(By locator, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static String handleAlert(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));  // Oczekiwanie na alert przez maksymalnie 10 sekund
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String alertMessage = alert.getText();
            alert.accept();
            return alertMessage;
        } catch (Exception e) {
            throw new RuntimeException("Nie można przełączyć się na alert: " + e.getMessage());
        }
    }

    public static void takeScreenshot(WebDriver driver) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        LocalDateTime now = LocalDateTime.now();
        String date = now.toString();
        int endTheString = date.indexOf("T");
        int randNumber = (int) (Math.random() * 1000);
        String randNumberToString = String.valueOf(randNumber);
        String processedDate = date.substring(0, endTheString) + randNumberToString;
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile, new File("src/test/resources/screenshots/scrnshoot" + processedDate + ".png"));
    }
}
