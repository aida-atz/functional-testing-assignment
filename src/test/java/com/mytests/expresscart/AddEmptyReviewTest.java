package com.mytests.expresscart;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddEmptyReviewTest {
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
    void addEmptyReviewFails() {
        // Given the user is on the home page
        driver.get("http://localhost:3000/"); // Adjust URL as needed

        // When the user clicks on the account icon (between language selector and cart)
        WebElement accountIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//a[contains(@href, '/account')] | //button[contains(@aria-label, 'account')]")
        ));
        accountIcon.click();

        // And enters "test@test.com" in the "email address" field
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        emailField.sendKeys("test@test.com");

        // And enters "e2eW3Bt3s71nGB3nchM4rK" in the "Password" field
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("e2eW3Bt3s71nGB3nchM4rK");

        // And clicks the "Sign in" button
        WebElement signInButton = driver.findElement(By.xpath("//button[contains(text(),'Sign in')]"));
        signInButton.click();

        // And goes to the home page of the site
        WebElement homeLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Home")));
        homeLink.click();

        // And clicks the "NewProduct000" link
        WebElement productLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("NewProduct000")));
        productLink.click();

        // And clicks the "Add review" button
        WebElement addReviewButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//button[contains(text(),'Add review')]")
        ));
        addReviewButton.click();

        // And clicks the "Add review" button again (without entering any data)
        WebElement addReviewSubmit = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.id("addReview")
        ));
        addReviewSubmit.click();

        // Then "Please supply a review title" is shown in a red bar to the bottom of the screen
        WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//*[contains(@class,'alert') and contains(@class,'alert-danger') and contains(text(),'Please supply a review title')]")
        ));
        Assertions.assertTrue(alert.isDisplayed(), "\"Please supply a review title\" alert is not shown");
    }
}
