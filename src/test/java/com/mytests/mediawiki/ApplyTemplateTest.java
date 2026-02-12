package com.mytests.mediawiki;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import com.mytests.base.BaseTest;

public class ApplyTemplateTest extends BaseTest {
    private MediaWikiUtils webDriverUtils;

    @BeforeEach
    public void setUpUtils() {
        super.setUp();
        webDriverUtils = new MediaWikiUtils(driver, wait);
    }

    @Test
    public void testApplyTemplate() {
        // the user logs in
        webDriverUtils.login("admin", "e2eW3Bt3s71nGB3nchM4rK");

        // the user enters "Selenium WebDriver" in the search bar
        enterTextWithWait(By.id("searchInput"), "Selenium WebDriver");

        // the user presses Enter
        pressKey(By.id("searchInput"), Keys.RETURN);

        // the user clicks the "Edit source" link
        clickButtonWithWait(By.id("ca-edit"));

        // the user enters "{{Software|dev=Selenium|ver=3.141.59}}" at the beginning of
        // the page
        enterTextWithWait(By.id("wpTextbox1"), "{{Software|dev=Selenium|ver=3.141.59}}");

        // the user clicks the "Save changes" button
        clickButtonWithWait(By.id("wpSave"));

        // the page is displayed with "Selenium WebDriver"
        assertTextInElement(By.cssSelector("#firstHeading .mw-page-title-main"), "Selenium WebDriver");

        // And "Developer: Selenium Latest version: 3.141.59" is shown at the beginning
        // of the body
        assertTextInElement(By.cssSelector("#mw-content-text .mw-parser-output p"),
                "Developer: Selenium Latest version: 3.141.59");

    }

    @AfterEach
    public void tearDown() {
        super.tearDown();
    }

}
