package com.mytests.expresscart;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddExistingUserFailsTest {
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
    void addExistingUserFails() {
        // Given the user is on the administrative home page (/admin)
        driver.get("http://localhost:3000/admin"); // Change URL as needed

        // When the user enters "owner@test.com" in the "email address" field
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        emailField.sendKeys("owner@test.com");

        // And enters "e2eW3Bt3s71nGB3nchM4rK" in the "Password" field
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("e2eW3Bt3s71nGB3nchM4rK");

        // And clicks the "Sign in" button
        WebElement signInButton = driver.findElement(By.xpath("//button[contains(text(),'Sign in')]"));
        signInButton.click();

        // And clicks the "+" icon to the right of the link "Users"
        WebElement usersLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Users")));
        WebElement plusIcon = usersLink
                .findElement(
                        By.xpath("//a[contains(@class,'sidebar-link-addon text-muted') and @href='/admin/user/new']"));
        plusIcon.click();

        // And enters "TestUser000" in the "Users name" field
        WebElement userNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("usersName")));
        userNameField.sendKeys("TestUser000");

        // And enters "test000@test.com" in the "User email" field
        WebElement userEmailField = driver.findElement(By.id("userEmail"));
        userEmailField.sendKeys("test000@test.com");

        // And enters "e2eW3Bt3s71nGB3nchM4rK" in the "User password" field
        WebElement userPasswordField = driver.findElement(By.id("userPassword"));
        userPasswordField.sendKeys("e2eW3Bt3s71nGB3nchM4rK");

        // And enters "e2eW3Bt3s71nGB3nchM4rK" in the "Password confirm" field
        WebElement passwordConfirmField = driver
                .findElement(By.xpath("//input[@type='password' and @data-match='#userPassword']"));
        passwordConfirmField.sendKeys("e2eW3Bt3s71nGB3nchM4rK");

        // And clicks the "Create" button
        WebElement createButton = driver.findElement(By.xpath("//button[contains(text(),'Create')]"));
        createButton.click();

        // Then "A user with that email address already exists" is shown in a red alert
        // at the bottom of the table
        // Wait for the alert to appear (should be visible for about 2 seconds)
        WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(
                        "//*[contains(@class,'alert') and contains(@class,'alert-danger') and contains(text(),'A user with that email address already exists')]")));
        Assertions.assertTrue(alert.isDisplayed(), "Red alert for existing user is not shown");
        Assertions.assertTrue(alert.getText().contains("A user with that email address already exists"));

        // Then the user clicks the "Logout" link
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath(
                        "//*[contains(@class,'alert') and contains(@class,'alert-danger') and contains(text(),'A user with that email address already exists')]")));
        WebElement logoutButton = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/admin/logout']")));
        logoutButton.click();
    }
}
