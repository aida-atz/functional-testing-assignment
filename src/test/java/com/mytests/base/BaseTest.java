package com.mytests.base;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// import com.mytests.joomela.JoomelaUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import com.enums.Browser;
import com.factory.DriverFactory;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    // protected JoomelaUtils webDriverUtils;
    protected Browser browser = Browser.CHROME; // Default browser

    public void setUp() {
        driver = DriverFactory.getNewDriverInstance(browser);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void waitForAndPerformAction(By locator, String action, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

        if (action.equals("sendKeys")) {
            element.clear();
            element.sendKeys(text);
        } else if (action.equals("click")) {
            element.click();
        }
    }

    public void enterTextDirectly(By locator, String text) {
        WebElement element = driver.findElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    public void clickButtonDirectly(By locator) {
        WebElement button = driver.findElement(locator);
        button.click();
    }

    public void enterTextWithWait(By locator, String text) {
        waitForAndPerformAction(locator, "sendKeys", text);
    }

    public void clickButtonWithWait(By locator) {
        waitForAndPerformAction(locator, "click", null);
    }

    public void assertTextInElement(By locator, String expectedText) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        String actualText = element.getText();
        assertTrue(actualText.contains(expectedText));
    }

    public void pressKey(By locator, Keys key) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.sendKeys(key);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
