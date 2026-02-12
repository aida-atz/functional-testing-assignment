package com.mytests.joomela;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.mytests.base.BaseTest;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class BadSiteAdminLoginTest extends BaseTest {
    private JoomelaUtils webDriverUtils;

    @BeforeEach
    public void setUpUtils() {
        super.setUp();
        webDriverUtils = new JoomelaUtils(driver, wait);
    }

    @Test
    public void testBadSiteAdminLogin() {

        // the user logs in
        webDriverUtils.login("administrator", "e2eW3Bt3s71nGB3nchM4rK");

        // the user clicks the "Site Administrator" link
        clickButtonWithWait(By.linkText("Site Administrator"));

        // Switch to the new tab (since a new tab opens)
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        // the user enters "administrator" in the "Username" field
        enterTextWithWait(By.id("mod-login-username"), "administrator");

        // the user enters "wrongpassword" in the "Password" field
        enterTextWithWait(By.id("mod-login-password"), "wrongpassword");

        // the user clicks the "Log in" button
        clickButtonDirectly(By.xpath("//button[contains(normalize-space(.), 'Log in')]"));

        // Verify that the login failed by checking for an error message
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//div[@id='system-message-container']//div//div[contains(@class,'alert-message')]")));
        String errorText = errorMessage.getText();
        assertEquals("Username and password do not match or you do not have an account yet.", errorText);
    }

    @AfterEach
    public void tearDown() {
        super.tearDown();
    }
}
