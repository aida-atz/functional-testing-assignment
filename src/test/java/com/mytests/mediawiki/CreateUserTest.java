package com.mytests.mediawiki;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import com.mytests.base.BaseTest;

public class CreateUserTest extends BaseTest {
    private MediaWikiUtils webDriverUtils;

    @BeforeEach
    public void setUpUtils() {
        super.setUp();
        webDriverUtils = new MediaWikiUtils(driver, wait);
    }

    @Test
    public void testCreateUser() {
        // the user logs in
        webDriverUtils.login("admin", "e2eW3Bt3s71nGB3nchM4rK");

        // the user clicks the "Special pages" link
        clickButtonDirectly(By.cssSelector("#t-specialpages"));

        // the user clicks the "Create account" link
        clickButtonDirectly(By.linkText("Create account"));

        // the user enters "User001" in the "Username" field
        enterTextWithWait(By.cssSelector(("#wpName2")), "User001");

        // the user enters "e2eW3Bt3s71nGB3nchM4rK" in the "Password" field
        enterTextDirectly(By.cssSelector("#wpPassword2"), "e2eW3Bt3s71nGB3nchM4rK");

        // the user enters "e2eW3Bt3s71nGB3nchM4rK" in the "Confirm Password" field
        enterTextDirectly(By.cssSelector("#wpRetype"), "e2eW3Bt3s71nGB3nchM4rK");

        // the user enters "Real Name 001" in the "Real Name" field
        enterTextDirectly(By.cssSelector("#wpRealName"), "Real Name 001");

        // the user clicks the "Create Account" button
        clickButtonDirectly(By.cssSelector("#wpCreateaccount"));

        // Then "The user account for User001 (talk) has been created." is displayed
        assertTextInElement(By.xpath("//div[contains(@id,'mw-content-text')]//p//a[1]"), "User001");
    }

    @AfterEach
    public void tearDown() {
        super.tearDown();
    }

}
