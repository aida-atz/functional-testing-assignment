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

public class AddCategoryTest extends BaseTest {
        private MediaWikiUtils webDriverUtils;

        @BeforeEach
        public void setUpUtils() {
                super.setUp();
                webDriverUtils = new MediaWikiUtils(driver, wait);
        }

        @Test
        public void testAddCategory() {
                // the user logs in
                webDriverUtils.login("admin", "e2eW3Bt3s71nGB3nchM4rK");

                // the user enters "Selenium WebDriver2" in the search bar
                enterTextWithWait(By.id("searchInput"), "Selenium WebDriver2");

                // the user presses Enter
                pressKey(By.id("searchInput"), Keys.RETURN);

                // the user clicks the "Edit" link
                clickButtonWithWait(By.id("ca-ve-edit"));

                // the user clicks the icon with three lines
                clickButtonWithWait(
                                By.xpath("//div[@title='Page options']//span[@class='oo-ui-popupToolGroup-handle']"));

                // the user clicks "Categories"
                clickButtonWithWait(By.xpath("//span[contains(@class,'oo-ui-tool-name-categories')]"));

                // the user enters "Browser automation tools" in the "Add a category" field
                enterTextWithWait(
                                By.xpath("//div[contains(@class,'ve-ui-mwCategoryInputWidget')]//input[@aria-label='Add a category']"),
                                "Browser automation tools");

                // the user presses Enter
                pressKey(By.xpath(
                                "//div[contains(@class,'ve-ui-mwCategoryInputWidget')]//input[@aria-label='Add a category']"),
                                Keys.RETURN);

                // the user clicks the "Apply changes" button
                clickButtonWithWait(By.xpath(
                                "//div[@class='oo-ui-processDialog-actions-primary']//span[contains(@class,'oo-ui-buttonElement')]"));

                // the user clicks the "Save" button
                clickButtonWithWait(By.xpath(
                                "//a[contains(@class,'oo-ui-tool-link ve-ui-toolbar-saveButton') and @title='Save changes… Alt+Shift+S']"));

                // the user enters "Added category" in the sumamry
                enterTextWithWait(By.xpath("//textarea[@class='oo-ui-inputWidget-input']"), "Added category");

                // the user clicks the "Save page" button
                clickButtonWithWait(By.xpath(
                                "//a[@class='oo-ui-buttonElement-button' and @role='button']//span[contains(text(),'Save changes')]"));

                // the page has title "Selenium WebDriver2"
                assertTextInElement(
                                By.xpath("//h1[contains(@id,'firstHeading')]//span[contains(@class,'mw-page-title-main')]"),
                                "Selenium WebDriver2");

                // "Category: Browser automation tools" is displayed at the end of the page
                WebElement categoryText = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//div[@id='mw-normal-catlinks']//a[contains(text(),'Category')]")));
                String category = categoryText.getText();
                assertTrue(category.contains("Category"));
                WebElement categoryTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//div[@id='mw-normal-catlinks']//li/a[contains(text(), 'Browser automation tools')]")));
                String title = categoryTitle.getText();
                assertTrue(title.equals("Browser automation tools"));
        }

        @AfterEach
        public void tearDown() {
                super.tearDown();
        }
}
