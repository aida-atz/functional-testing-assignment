package com.mytests.mediawiki;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.mytests.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CloseInitialEditorPopupTest extends BaseTest {
    private MediaWikiUtils webDriverUtils;

    @BeforeEach
    public void setUpUtils() {
        super.setUp();
        webDriverUtils = new MediaWikiUtils(driver, wait);
    }

    @Test
    public void testCloseInitialEditorPopup() {
        // the user logs in
        webDriverUtils.login("User001", "e2eW3Bt3s71nGB3nchM4rK");

        // the user enters "Software testing" in the search bar
        enterTextWithWait(By.id("searchInput"), "Software testing");

        // the user presses Enter
        pressKey(By.id("searchInput"), Keys.RETURN);

        // the user clicks the "Software testing" link
        clickButtonDirectly(By.linkText("Software testing"));

        // a pop-up is shown inside the page
        WebElement popUp = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[contains(@id,'ooui-2')]")));
        assertTrue(popUp.isDisplayed());

        // the user clciks the "Start editing" button
        clickButtonWithWait(By.xpath("//span[contains(text(),'Start editing')]"));
    }

    @AfterEach
    public void tearDown() {
        super.tearDown();
    }

}