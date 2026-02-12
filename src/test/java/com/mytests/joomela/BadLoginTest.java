package com.mytests.joomela;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.mytests.base.BaseTest;

public class BadLoginTest extends BaseTest {
    private JoomelaUtils webDriverUtils;

    @BeforeEach
    public void setUpUtils() {
        super.setUp();
        webDriverUtils = new JoomelaUtils(driver, wait);
    }

    @Test
    public void testBadLogin() {
        // the user logs in with an incorrect password
        webDriverUtils.login("administrator","wrongpassword");

        // verify error message in a yellow box
        WebElement errorBox = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert-message")));
        String errorMessage = errorBox.getText();
        assertTrue(errorMessage.contains("Username and password do not match or you do not have an account yet."));
    }

    @AfterEach
    public void tearDown() {
        super.tearDown();
    }
}
