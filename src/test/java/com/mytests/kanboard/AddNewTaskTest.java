package com.mytests.kanboard;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddNewTaskTest {
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
    void addNewTaskToProject() {
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

        // And enters "task 3" in the "Title" field
        WebElement titleField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("title")));
        titleField.sendKeys("task 3");

        // And clicks the "Save" button
        WebElement saveButton = driver.findElement(By.xpath("//button[contains(text(),'Save')]"));
        saveButton.click();

        // Then "task 3" is shown on a yellow box
        WebElement yellowTaskBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(
                        "//div[@class='task-board-expanded']//div[@class='task-board-title']//a[contains(text(),'task 3')]")));
        Assertions.assertTrue(yellowTaskBox.isDisplayed());

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