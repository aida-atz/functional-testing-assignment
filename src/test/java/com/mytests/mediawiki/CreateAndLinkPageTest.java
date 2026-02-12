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

public class CreateAndLinkPageTest extends BaseTest {
        private MediaWikiUtils webDriverUtils;

        @BeforeEach
        public void setUpUtils() {
                super.setUp();
                webDriverUtils = new MediaWikiUtils(driver, wait);
        }

        @Test
        public void testCreateAndLinkPage() {
                // the user logs in
                webDriverUtils.login("admin", "e2eW3Bt3s71nGB3nchM4rK");

                // the user enters "E2E Web Testing7" in the search bar
                enterTextWithWait(By.id("searchInput"), "E2E Web Testing7");

                // the user presses Enter
                pressKey(By.id("searchInput"), Keys.RETURN);

                // the user clicks the "E2E Web Testing7" link
                clickButtonWithWait(By.linkText("E2E Web Testing7"));

                // the user closes the notification if it appears
                try {
                        WebElement startEditingButton = wait
                                        .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                                                        "//div[contains(@class,'oo-ui-messageDialog-content')]//div[@class='oo-ui-window-foot']//span[contains(text(),'Start editing')]")));
                        assertTrue(startEditingButton.isDisplayed());
                        startEditingButton.click();
                } catch (Exception e) {
                        System.out.println("No notification to close.");
                }

                // the user enters the first part of the text in the editor [[
                clickButtonDirectly(By.id("content"));
                enterTextWithWait(
                                By.xpath("//div[contains(@class, 've-ce-branchNode ve-ce-documentNode') and @role='textbox']//p"),
                                "the first part of the text in the editor [[");

                // the user enters "Software testing" in the popup search bar
                enterTextWithWait(By.xpath("//input[@class='oo-ui-inputWidget-input'and @type='search']"),
                                "Software testing");

                // the user clicks the "Software testing" link in the popup
                clickButtonWithWait(By.xpath("//a[@class='oo-ui-labelElement-label' and @title='Software testing']"));

                // the user clicks the editor after the "Software testing" link
                clickButtonDirectly(
                                By.xpath("//div[contains(@class, 've-ce-branchNode ve-ce-documentNode') and @role='textbox']//p"));

                // the user enters the last part of the text
                WebElement editorText = wait
                                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                                                "//div[contains(@class, 've-ce-branchNode ve-ce-documentNode') and @role='textbox']//p")));
                editorText.sendKeys(" the last part of the text");

                // the user clicks the "Save" button
                clickButtonDirectly(By.xpath(
                                "//a[contains(@class,'oo-ui-tool-link ve-ui-toolbar-saveButton') and @title='Save page… Alt+Shift+S']"));

                // the user enters "Page created" in the sumamry
                enterTextWithWait(By.xpath("//textarea[@class='oo-ui-inputWidget-input']"), "Page created");

                // the user clicks the "Save page" button
                clickButtonDirectly(By.xpath(
                                "//a[@class='oo-ui-buttonElement-button' and @role='button']//span[contains(text(),'Save page')]"));
                // the page is displayed with "E2E Web Testing7" as title
                assertTextInElement(By.xpath("//h1//span[@class='mw-page-title-main']"), "E2E Web Testing7");

                // the page created in the previous test case is displayed
                assertTextInElement(By.xpath("//div[@class='mw-parser-output']//p"),
                                "the first part of the text in the editor Software testing the last part of the text");
        }

        @AfterEach
        public void tearDown() {
                super.tearDown();
        }

}
