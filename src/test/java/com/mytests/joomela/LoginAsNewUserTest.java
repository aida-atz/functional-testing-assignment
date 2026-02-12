package com.mytests.joomela;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static org.junit.jupiter.api.Assertions.*;

import com.mytests.base.BaseTest;

public class LoginAsNewUserTest extends BaseTest {

    private JoomelaUtils webDriverUtils;

    @BeforeEach
    public void setUpUtils() {
        super.setUp();
        webDriverUtils = new JoomelaUtils(driver, wait);
    }

    @Test
    public void testLoginAsNewUser() {

        // the user logs in
        webDriverUtils.login("tuser01", "tpassword");

        // Then verify "Test User" appears as the value of the "Name" field
        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("jform_name")));
        String nameText = nameInput.getAttribute("value");
        assertEquals("Test User", nameText);

        clickButtonWithWait(By.linkText("Author Login"));
    
        clickButtonWithWait(By.xpath("//button[contains(normalize-space(.), 'Log out')]"));

    }

    @AfterEach
    public void tearDown() {
    super.tearDown();
    }
}
