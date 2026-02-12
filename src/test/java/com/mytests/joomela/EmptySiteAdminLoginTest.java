package com.mytests.joomela;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import com.mytests.base.BaseTest;

public class EmptySiteAdminLoginTest extends BaseTest {
    private JoomelaUtils webDriverUtils;

    @BeforeEach
    public void setUpUtils() {
        super.setUp();
        webDriverUtils = new JoomelaUtils(driver, wait);
    }

    @Test
    public void testEmptySiteAdminLogin() {

        // the user logs in
        webDriverUtils.login("administrator", "e2eW3Bt3s71nGB3nchM4rK");

        // the user clicks the "Site Administrator" link
        clickButtonWithWait(By.linkText("Site Administrator"));

        // Switch to the new tab (since a new tab opens)
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        // the user clicks the "Log in" button
        clickButtonDirectly(By.xpath("//button[contains(normalize-space(.), 'Log in')]"));

        // Then check if "Empty password not allowed." is shown in a yellow box
        WebElement alertMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(
                        "//div[contains(@class, 'alert-message') and contains(text(), 'Empty password not allowed.')]")));
        String alertText = alertMessage.getText();
        assertTrue(alertText.contains("Empty password not allowed."));
    }

    @AfterEach
    public void tearDown() {
        super.tearDown();
    }
}
