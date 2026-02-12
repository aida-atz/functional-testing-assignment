package com.mytests.mediawiki;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.mytests.base.BaseTest;

public class CreatePageFromSourceTest extends BaseTest {
    private MediaWikiUtils webDriverUtils;

    @BeforeEach
    public void setUpUtils() {
        super.setUp();
        webDriverUtils = new MediaWikiUtils(driver, wait);
    }

    @Test
    public void testCreatePageFromSource() {
        // the user logs in
        webDriverUtils.login("admin", "e2eW3Bt3s71nGB3nchM4rK");

        // the user enters "Selenium WebDriver2" in the search bar
        enterTextWithWait(By.id("searchInput"), "Selenium WebDriver2");

        // the user presses Enter
        pressKey(By.id("searchInput"), Keys.RETURN);

        // the user clicks the "Selenium WebDriver2" link
        clickButtonWithWait(By.linkText("Selenium WebDriver2"));

        // the user clicks the "Selenium WebDriver2" link
        try {
            WebElement softwareTestingLink = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Selenium WebDriver2")));
            assertTrue(softwareTestingLink.isDisplayed());
            softwareTestingLink.click();
        } catch (Exception e) {
            clickButtonDirectly(By.id("ca-ve-edit"));
        }
        // a pop-up is shown inside the page
        try {
            WebElement popUp = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[contains(@id,'ooui-2')]")));
            assertTrue(popUp.isDisplayed());
            clickButtonWithWait(By.xpath("//span[contains(text(),'Start editing')]"));
        } catch (Exception e) {
            System.out.println("No notification to close.");
        }

        // the user clicks the "Create source" link
        clickButtonWithWait(By.id("ca-edit"));

        // the user enters the text of the page in the editor
        enterTextWithWait(By.id("wpTextbox1"), "the text of the page");

        // the user clicks the "Save page" button
        clickButtonWithWait(By.id("wpSave"));

        // the page is displayed with "Selenium WebDriver2" as title and the previously
        // inserted text as body
        assertTextInElement(By.cssSelector("#firstHeading .mw-page-title-main"), "Selenium WebDriver2");
        assertTextInElement(By.cssSelector("#mw-content-text .mw-parser-output p"), "the text of the page");

    }

    @AfterEach
    public void tearDown() {
        super.tearDown();
    }
}
