package com.mytests.joomela;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.mytests.base.BaseTest;

public class AdminLoginTest extends BaseTest {
    @BeforeEach
    public void setUpUtils() {
        super.setUp();
    }

    @Test
    public void testAdminLogin() {
        // Navigate to the Joomla admin login page
        driver.get("http://localhost:8080/administrator");

        // Wait for the username input field to be visible and enter the username
        enterTextWithWait(By.id("mod-login-username"), "administrator");

        // Wait for the password input field to be visible and enter the password
        enterTextDirectly(By.id("mod-login-password"), "e2eW3Bt3s71nGB3nchM4rK");

        // click the "Log In" button
        clickButtonDirectly(By.xpath("//button[contains(normalize-space(.), 'Log in')]"));

        // click on the user menu toggle to open the dropdown
        clickButtonWithWait(By.cssSelector(".icon-user"));

        // checking if the login was successful
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//ul[contains(@class,'dropdown-menu')]//strong[contains(text(),'Super User')]")));
        assertEquals("Super User", nameField.getText());

        // click on the "Logout" link
        clickButtonWithWait(By.linkText("Logout"));

    }

    @AfterEach
    public void tearDown() {
        super.tearDown();
    }
}
