package com.mytests.expresscart;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddProductTest {
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
    void addsProductToSystem() {
        // Given the user is on the administrative home page (/admin)
        driver.get("http://localhost:3000/admin"); // Adjust URL as needed

        // When the user enters "owner@test.com" in the "email address" field
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        emailField.sendKeys("owner@test.com");

        // And enters "e2eW3Bt3s71nGB3nchM4rK" in the "Password" field
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("e2eW3Bt3s71nGB3nchM4rK");

        // And clicks the "Sign in" button
        WebElement signInButton = driver.findElement(By.xpath("//button[contains(text(),'Sign in')]"));
        signInButton.click();

        // And clicks the "+" icon to the right of the link "Products"
        // WebElement productsLink =
        // wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Products")));
        WebElement plusIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(@class,'sidebar-link-addon text-muted') and @href='/admin/product/new']")));
        plusIcon.click();

        // And enters "NewProduct000" in the "Product title" field
        WebElement titleField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("productTitle")));
        titleField.sendKeys("NewProduct002");

        // And enters "15.95" in the "Product price" field
        WebElement priceField = driver.findElement(By.id("productPrice"));
        priceField.sendKeys("15.95");

        // And enters "Description for product 000" in the "Product description" field
        WebElement descField = driver.findElement(By.cssSelector("#editor-wrapper .note-editor .note-editing-area p"));
        descField.sendKeys("Description for product 000");

        // And clicks the "Add product" button
        WebElement addButton = driver.findElement(By.xpath("//button[contains(text(),'Add product')]"));
        addButton.click();

        // And clicks the "Products" link
        WebElement productsLinkAgain = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Products")));
        productsLinkAgain.click();

        // Then "NewProduct" is shown in the first row of the table
        // Then "NewProduct" is shown in the first row of the list
        WebElement firstRow = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(
                        "//ul[@class='list-group']//li[2]//div[@class='top-pad-8']//a")));
        Assertions.assertTrue(firstRow.getText().contains("NewProduct002"));

    }
}