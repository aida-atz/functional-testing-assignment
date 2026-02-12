package com.mytests.mediawiki;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import com.mytests.base.BaseTest;

public class EditPageTest extends BaseTest {
    private MediaWikiUtils webDriverUtils;

       @BeforeEach
        public void setUpUtils() {
            super.setUp();
                webDriverUtils = new MediaWikiUtils(driver, wait);
        }

    @Test
    public void testEditPage() {
        // the user logs in
        webDriverUtils.login("admin", "e2eW3Bt3s71nGB3nchM4rK");

        // the user enters "Software testing" in the search bar
        enterTextWithWait(By.id("searchInput"), "Software testing");

        // the user presses Enter
        pressKey(By.id("searchInput"), Keys.RETURN);

        // the user clicks the "Edit" link
        clickButtonWithWait(By.id("ca-ve-edit"));

        // the user enters the additional text at the end of the editor
        enterTextWithWait(By.id("mwAg"), "the additional text");

        // the user clicks the "Save" button
        clickButtonWithWait(By.xpath(
                "//a[contains(@class,'oo-ui-tool-link ve-ui-toolbar-saveButton') and @title='Save changes… Alt+Shift+S']"));

        // the user enters "Page created" in the sumamry
        enterTextWithWait(By.xpath("//textarea[@class='oo-ui-inputWidget-input']"), "Page created");

        // the user clicks the "Save page" button
        clickButtonWithWait(By.xpath(
                "//a[@class='oo-ui-buttonElement-button' and @role='button']//span[contains(text(),'Save changes')]"));

        // the page is displayed with "Software testing" as title and the full text as
        // body
        assertTextInElement(
                By.xpath("//h1[contains(@id,'firstHeading')]//span[contains(@class,'mw-page-title-main')]"),
                "Software testing");
                assertTextInElement(By.xpath("//div[@class='mw-parser-output']//p"), "the additional text");

    }

    @AfterEach
    public void tearDown() {
        super.tearDown();
    }

}
