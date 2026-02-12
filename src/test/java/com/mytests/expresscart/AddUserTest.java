package com.mytests.expresscart;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddUserTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeAll
    void setUp() {
        // Set path to chromedriver if not in PATH
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
    void addsUserToSystem() {
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
        userNameField.sendKeys("TestUser002");

        // And enters "test000@test.com" in the "User email" field
        WebElement userEmailField = driver.findElement(By.id("userEmail"));
        userEmailField.sendKeys("test002@test.com");

        // And enters "password" in the "User password" field
        WebElement userPasswordField = driver.findElement(By.id("userPassword"));
        userPasswordField.sendKeys("password");

        // And enters "password" in the "Password confirm" field
        WebElement passwordConfirmField = driver
                .findElement(By.xpath("//input[@type='password' and @data-match='#userPassword']"));
        passwordConfirmField.sendKeys("password");

        // And clicks the "Create" button
        WebElement createButton = driver.findElement(By.xpath("//button[contains(text(),'Create')]"));
        createButton.click();

        // And clicks the "Users" link
        usersLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Users")));
        usersLink.click();

        // Then "User: TestUser000 - (test000@test.com)\nRole: User" is shown in the
        // third row of the table
        WebElement table = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='list-group']")));
        java.util.List<WebElement> rows = table.findElements(By.tagName("li"));
        WebElement lastRow = rows.get(rows.size() - 1);
        // String expectedText = "User: TestUser000 - (test000@test.com)\nRole: User";
        Assertions.assertTrue(lastRow.getText().contains("User: TestUser002 - (test002@test.com)"));
        Assertions.assertTrue(lastRow.getText().contains("Role: User"));

        // Then the user clicks the "Logout" link
        WebElement logoutLink = driver.findElement(By.linkText("Logout"));
        logoutLink.click();
    }
}