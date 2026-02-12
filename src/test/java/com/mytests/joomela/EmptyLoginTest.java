package com.mytests.joomela;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;

import com.mytests.base.BaseTest;

import static org.junit.jupiter.api.Assertions.*;

public class EmptyLoginTest extends BaseTest {
    private JoomelaUtils webDriverUtils;

    @BeforeEach
    public void setUpUtils() {
        super.setUp();
        webDriverUtils = new JoomelaUtils(driver, wait);
    }

    @Test
    public void testEmptyLogin() {

        // the user logs in with empty credentials
        webDriverUtils.login("", "");

        // Then the username field should be highlighted as invalid
        // and an error message should be displayed
        WebElement usernameInput = driver.findElement(By.id("username"));
        assertTrue(usernameInput.getAttribute("class").contains("invalid"), "Please fill out this field.");

    }

    @AfterEach
    public void tearDown() {
        super.tearDown();
    }

}
