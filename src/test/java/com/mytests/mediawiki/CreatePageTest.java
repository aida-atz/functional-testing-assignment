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

public class CreatePageTest extends BaseTest {
    private MediaWikiUtils webDriverUtils;

    @BeforeEach
    public void setUpUtils() {
        super.setUp();
        webDriverUtils = new MediaWikiUtils(driver, wait);
    }

    @Test
    public void testCreatePage() {
        // the user logs in
        webDriverUtils.login("User001", "e2eW3Bt3s71nGB3nchM4rK");

        // the user enters "Software testing" in the search bar
        enterTextWithWait(By.id("searchInput"), "Software testing");

        // the user presses Enter
        pressKey(By.id("searchInput"), Keys.RETURN);

        // the user clicks the "Software testing" link
        // clickButtonWithWait(By.linkText("Software testing"));
        try {
            WebElement softwareTestingLink = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Software testing")));
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

        // the user enters the text of the page in the editor
        enterTextDirectly(By.xpath("//div[@role='textbox']//p"), "This is the body of the Software testing page.");

        // the user clicks the "Save" button
        clickButtonDirectly(By.xpath("//a//span[contains(text(),'Save page…')]"));

        // the user enters "Page created" in the sumamry
        enterTextWithWait(By.xpath("//textarea[contains(@class,'oo-ui-inputWidget-input')]"), "Page created");

        // the user clicks the "Save page" button
        clickButtonWithWait(
                By.xpath("//a[contains(@class,'oo-ui-buttonElement-button')]//span[contains(text(),'Save page')]"));

        // the page is displayed with "Software testing" as title and the previously
        // inserted text as body
        assertTextInElement(By.xpath("//h1[contains(@id,'firstHeading')]//span[contains(@class,'mw-page-title-main')]"),
                "Software testing");
        assertTextInElement(By.xpath("//div[@id='mw-content-text']//div[@class='mw-parser-output']//p"),
                "This is the body of the Software testing page.");

    }

    @AfterEach
    public void tearDown() {
        super.tearDown();
    }

}
