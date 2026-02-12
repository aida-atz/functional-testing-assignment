
package com.mytests.expresscart;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddEmptyUserTest {
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
    void triesToAddEmptyUserAndFails() {
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

        // And clicks the "Create" button (without filling any fields)
        WebElement createButton = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Create')]")));
        createButton.click();

        // Then all the fields are highlighted in red
        List<By> requiredFields = Arrays.asList(
                By.id("usersName"),
                By.id("userEmail"),
                By.id("userPassword"),
                By.xpath("//input[@type='password' and @data-match='#userPassword']"));

        for (By locator : requiredFields) {
            WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

            WebElement parentDiv = field.findElement(By.xpath(".."));

            String classAttr = parentDiv.getAttribute("class");

            Assertions.assertTrue(classAttr.contains("has-error") && classAttr.contains("has-danger"),
                    "The parent div for " + locator + " does not have the expected error classes.");
        }

        // Then the user clicks the "Logout" link
        WebElement logoutLink = driver.findElement(By.linkText("Logout"));
        logoutLink.click();
    }
}
