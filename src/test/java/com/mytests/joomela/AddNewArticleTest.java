package com.mytests.joomela;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.mytests.base.BaseTest;

import static org.junit.jupiter.api.Assertions.*;

public class AddNewArticleTest extends BaseTest {
    private JoomelaUtils webDriverUtils;

    @BeforeEach
    public void setUpUtils() {
        super.setUp();
        webDriverUtils = new JoomelaUtils(driver, wait);
    }

    @Test
    public void testAddNewArticle() {

        // the user logs in
        webDriverUtils.login("administrator", "e2eW3Bt3s71nGB3nchM4rK");

        // the user clicks on the "create a post" link
        clickButtonWithWait(By.linkText("Create a Post"));

        // the user enters "Test Article 01" in the title field
        enterTextWithWait(By.id("jform_title"), "Test Article 01");

        // the user enters "This is the body of the first article for testing the
        // platform" in the main text editor
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("jform_articletext_ifr")));
        enterTextWithWait(By.tagName("body"),
                "This is the body of the first article for testing the platform");
        driver.switchTo().defaultContent();

        // the user clicks the "Save" button
        clickButtonDirectly(By.xpath("//button[@class='btn btn-primary']"));

        // the user waits for the article to be saved and checks that the article is
        WebElement firstArticleTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'leading-0')]//div[contains(@class,'page-header')]//h2//a")));
        WebElement firstArticleBody = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'leading-0')]//p")));

        assertEquals("Test Article 01", firstArticleTitle.getText());
        assertEquals("This is the body of the first article for testing the platform", firstArticleBody.getText());

        // the user logs out
        webDriverUtils.logout();
    }

    @AfterEach
    public void tearDown() {
        super.tearDown();
    }
}
