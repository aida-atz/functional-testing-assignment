package com.mytests.expresscart;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddEmptyProductTest {
    static private WebDriver driver;
    static private WebDriverWait wait;

     @BeforeAll
    static void setUp() {
        // System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterAll
    static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void addEmptyProductFails() {
        // Given the user is on the administrative home page (/admin)
        driver.get("http://localhost:3000/admin"); // Adjust URL as needed

        // When the user enters "owner@test.com" in the "email address" field
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        emailField.sendKeys("owner@test.com");

        // And enters "e2eW3Bt3s71nGB3nchM4rK" in the "Password" field
        WebElement passwordField = driver.findElement(By.xpath("//input[@id='password']"));
        passwordField.sendKeys("e2eW3Bt3s71nGB3nchM4rK");

        // And clicks the "Sign in" button
        WebElement signInButton = driver.findElement(By.xpath("//button[contains(text(),'Sign in')]"));
        signInButton.click();

        // And clicks the "+" icon to the right of the link "Products"
        WebElement productsLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Products")));
        WebElement plusIcon = productsLink.findElement(
                By.xpath("//a[contains(@class,'sidebar-link-addon text-muted') and @href='/admin/product/new']"));
        plusIcon.click();

        // And clicks the "Add product" button (without entering any data)
        WebElement addButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[contains(text(),'Add product')]")));
        addButton.click();

        // Then the fields "Product title" and "Product price" are highlighted in red
        WebElement titleField = driver.findElement(By.xpath("//input[@id='productTitle']/.."));
        WebElement priceField = driver.findElement(By.xpath("//input[@id='productPrice']/../.."));

        String titleClass = titleField.getAttribute("class");
        String priceClass = priceField.getAttribute("class");

        // Check if the class attribute contains "is-invalid" or similar (adjust as
        // needed)
        boolean titleHighlighted = titleClass != null && titleClass.contains("has-error")
                && titleClass.contains("has-danger");
        boolean priceHighlighted = priceClass != null && priceClass.contains("has-error")
                && priceClass.contains("has-danger");

        Assertions.assertTrue(titleHighlighted);
        Assertions.assertTrue(priceHighlighted);
    }
}