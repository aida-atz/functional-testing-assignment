package com.mytests.expresscart;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddNewProdToCartTest {
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
    void addsProductToCart() {
        // Given the user is on the home page
        driver.get("http://localhost:3000/"); // Adjust URL as needed

        // When the user clicks the "NewProduct000" link
        WebElement productLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.linkText("NewProduct000")));
        productLink.click();

        // And clicks the "Add to cart" button
        WebElement addToCartButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//button[contains(text(),'Add to cart')]")));
        addToCartButton.click();

        // And clicks the "Home" link
        WebElement homeLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.linkText("Home")));
        homeLink.click();

        // Then "1" is shown in the red square to the right of the "Cart" link
        WebElement cartBadge = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//a[contains(@href,'/cart')]//span[contains(@class,'badge') or contains(@class,'cart-badge')]")));
        Assertions.assertEquals("1", cartBadge.getText().trim(), "Cart badge does not show 1");

        // When the user clicks on the "Cart" link
        WebElement cartLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//a[contains(@href,'/checkout/cart')]")));
        cartLink.click();

        // Then "NewProduct000" is shown in the "Cart contents"
        WebElement cartContents = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//*[contains(@class,'cart')]//*[contains(text(),'NewProduct000')]")));
        Assertions.assertTrue(cartContents.isDisplayed(), "NewProduct000 is not shown in the cart contents");
    }
}