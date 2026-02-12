package com.mytests.expresscart;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginUserTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeAll
    void setUp() {
        // System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterAll
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void loginAsNewlyAddedUser() {
        // Given the user is on the administrative home page (/admin)
        driver.get("http://localhost:3000/admin"); // Change URL as needed

        // When the user enters "test000@test.com" in the "email address" field
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        emailField.sendKeys("test000@test.com");

        // And enters "password" in the "Password" field
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("e2eW3Bt3s71nGB3nchM4rK");

        // And clicks the "Sign in" button
        WebElement signInButton = driver.findElement(By.xpath("//button[contains(text(),'Sign in')]"));
        signInButton.click();

        // Then "Dashboard" is shown on the page
        WebElement dashboardElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Dashboard')]")));
        Assertions.assertTrue(dashboardElement.isDisplayed(), "\"Dashboard\" is not shown on the page");

        // Then the user clicks the "Logout" link
        WebElement logoutLink = driver.findElement(By.linkText("Logout"));
        logoutLink.click();
    }
}