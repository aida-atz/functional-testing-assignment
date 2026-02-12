package com.mytests.kanboard;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddEmptyTaskTest {
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
    void addEmptyTaskFails() {
        // Given the user is on the login page (/login)
        driver.get("http://localhost:8080/login"); // Adjust URL as needed

        // When the user enters "admin" in the "Username" field
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        usernameField.sendKeys("admin");

        // And enters "admin" in the "Password" field
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("admin");

        // And clicks the "Sign in" button
        WebElement signInButton = driver.findElement(By.xpath("//button[contains(text(),'Sign in')]"));
        signInButton.click();

        // And clicks the "Test 2" link
        WebElement projectLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.linkText("Test 2")));
        projectLink.click();

        // And clicks the gear icon to the left of the screen
        WebElement gearIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(
                        "//div[@class='project-header']//div[@class='dropdown-component']//a[contains(@class,'dropdown-menu')]")));
        gearIcon.click();

        // And clicks the "Add a new task" link
        WebElement addTaskLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.linkText("Add a new task")));
        addTaskLink.click();

        // And clicks the "Save" button (without entering a title)
        WebElement saveButton = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Save')]")));
        saveButton.click();

        // Then "The title is req uired" is shown below the "Title" field
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//ul[@class='form-errors']//li[contains(text(),'The title is required')]")));
        Assertions.assertTrue(errorMsg.isDisplayed(),
                "\"The project name is required\" is not shown below the Title field");

        // clicks on the cancle button to close the dialog
        WebElement cancelButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='form-actions']//a[contains(text(),'cancel')]")));
        cancelButton.click();
        
        // Then the user clicks on the "A" icon in the top-right corner of the screen
        WebElement userIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(
                        "//div[@class='dropdown']//a[contains(@class,'dropdown-menu-link-icon') and @href='#']//div[contains(@class,'avatar avatar-20 avatar-inline')]")));
        userIcon.click();

        // And clicks the "Logout" link
        WebElement logoutLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.linkText("Logout")));
        logoutLink.click();
    }
}