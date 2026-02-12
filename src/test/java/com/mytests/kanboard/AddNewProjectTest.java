package com.mytests.kanboard;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddNewProjectTest {
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
    void addNewProject() {
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

        // And clicks the "New project" link
        WebElement newProjectLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.linkText("New project")));
        newProjectLink.click();

        // And enter "Test 2" in the "Name" field
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name")));
        nameField.sendKeys("Test 2");

        // And clicks the "Save" button
        WebElement saveButton = driver.findElement(By.xpath("//button[contains(text(),'Save')]"));
        saveButton.click();

        // Then "Test 2" is shown to the right of the "KB" logo
        wait.until(driver -> {
            WebElement updatedProjectName = driver.findElement(
                    By.xpath("//div[@class='title-container']//h1//span[@class='title']"));
            return updatedProjectName.getText().trim().equals("Test 2");
        });
        WebElement projectName = driver.findElement(
                By.xpath("//div[@class='title-container']//h1//span[@class='title']"));
        Assertions.assertEquals("Test 2", projectName.getText().trim());

        // And "This project is open" is shown below "Summary"
        WebElement summary = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//ul[@class='panel']//li[1]//strong")));
        Assertions.assertEquals(summary.getText(),"This project is open");

        // Then the user clicks on the "A" icon in the top-right corner of the screen
        WebElement userIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='dropdown']//a[contains(@class,'dropdown-menu-link-icon') and @href='#']//div[contains(@class,'avatar avatar-20 avatar-inline')]")));
        userIcon.click();

        // And clicks the "Logout" link
        WebElement logoutLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.linkText("Logout")));
        logoutLink.click();
    }
}