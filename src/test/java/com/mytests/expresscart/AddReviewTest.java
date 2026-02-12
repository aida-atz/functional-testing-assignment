package com.mytests.expresscart;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddReviewTest {
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
    void addsReviewToProduct() {
        // Given the user is on the home page
        driver.get("http://localhost:3000/"); // Adjust URL as needed

        // When the user clicks on the account icon (between language selector and cart)
        WebElement accountIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(@href, '/account')] | //button[contains(@aria-label, 'account')]")));
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
        WebElement productLink = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.linkText("NewProduct000")));
        productLink.click();

        // And clicks the "Add review" button
        WebElement addReviewButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[contains(text(),'Add review')]")));
        addReviewButton.click();

        // And enters "Review001" in the "Title:" field
        WebElement titleField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("review-title")));
        titleField.sendKeys("Review001");

        // And enters "Description001" in the "Description:" field
        WebElement descField = driver.findElement(By.id("review-description"));
        descField.sendKeys("Description001");

        // And enters "5" in the "Rating:" field
        WebElement ratingField = driver.findElement(By.id("review-rating"));
        ratingField.clear();
        ratingField.sendKeys("5");

        // And clicks the "Add review" button
        WebElement addReviewSubmit = driver.findElement(By.id("addReview"));
        addReviewSubmit.click();

        // And clicks on "Recent reviews"
        WebElement recentReviews = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Recent reviews')]")));
        recentReviews.click();

        // Then "Title: Review001" is shown on the page
        WebElement reviewTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//ul[@id='collapseReviews']//li//b[contains(text(),'Title:')]/..")));
        Assertions.assertTrue(reviewTitle.getText().contains("Title: Review001"));

        // And "Description: Description001" is shown on the page
        WebElement reviewDesc = driver.findElement(
                By.xpath(
                        "//ul[@id='collapseReviews']//li//b[contains(text(),'Description:')]/.."));
        Assertions.assertTrue(reviewDesc.getText().contains("Description: Description001"));

        // And "Rating: 5" is shown on the page
        WebElement reviewRating = driver.findElement(
                By.xpath("//ul[@id='collapseReviews']//li//b[contains(text(),'Rating:')]/.."));
        Assertions.assertTrue(reviewRating.getText().contains("Rating: 5"));

    }
}