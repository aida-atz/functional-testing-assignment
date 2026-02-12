package com.mytests.mediawiki;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.mytests.base.BaseTest;

public class CreateTemplateTest extends BaseTest {
    private MediaWikiUtils webDriverUtils;

    @BeforeEach
    public void setUpUtils() {
        super.setUp();
        webDriverUtils = new MediaWikiUtils(driver, wait);
    }

    @Test
    public void testCreateTemplate() {
        // the user logs in
        webDriverUtils.login("admin", "e2eW3Bt3s71nGB3nchM4rK");

        // enters "Template:Software" in the search bar
        enterTextWithWait(By.id("searchInput"), "Template:Software");

        // the user presses Enter
        pressKey(By.id("searchInput"), Keys.RETURN);

        // he user clicks the "Template:Software" link
        clickButtonWithWait(By.linkText("Template:Software"));

        // the user enters "Developer: {{{dev}}} Latest version: {{{ver}}}" in the
        // editor
        enterTextWithWait(By.id("wpTextbox1"), "Developer: {{{dev}}} Latest version: {{{ver}}}");

        // the user enters "Page created" in the sumamry
        enterTextWithWait(By.id("wpSummary"), "Page created");

        // the user clicks the "Save page" button
        clickButtonWithWait(By.id("wpSave"));

        // the page is displayed with "Template:Software" as title and the previously
        // inserted text as body
        // assertTextInElement(By.xpath("h1[@id='firstHeading']//span"), null);
        WebElement namespace = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("#firstHeading .mw-page-title-namespace")));
        WebElement separator = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("#firstHeading .mw-page-title-separator")));
        WebElement mainTitle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#firstHeading .mw-page-title-main")));
        String fullTitle = namespace.getText() + separator.getText() + mainTitle.getText();
        assertEquals("Template:Software", fullTitle);
        assertTextInElement(By.cssSelector("#mw-content-text .mw-parser-output p"),
                "Developer: {{{dev}}} Latest version: {{{ver}}}");
    }

    @AfterEach
    public void tearDown() {
        super.tearDown();
    }
}
