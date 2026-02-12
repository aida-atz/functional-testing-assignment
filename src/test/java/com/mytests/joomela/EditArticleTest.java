package com.mytests.joomela;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.mytests.base.BaseTest;

public class EditArticleTest extends BaseTest {
    private JoomelaUtils webDriverUtils;

    @BeforeEach
    public void setUpUtils() {
        super.setUp();
        webDriverUtils = new JoomelaUtils(driver, wait);
    }

    @Test
    public void testEditArticle() {

        // the user logs in
        webDriverUtils.login("administrator", "e2eW3Bt3s71nGB3nchM4rK");

        // the user clicks on the post
        clickButtonWithWait(
                By.xpath("//div[h3[contains(text(),'Older Posts')]]//a[contains(text(),'Test Article 01')]"));

        // the user clicks the gear icon to the bottom right of "Test Article 01"
        clickButtonWithWait(
                By.xpath("//button[contains(@class,'dropdown-toggle')]//span[contains(@class,'icon-cog')]"));

        // the user clicks the "Edit" link
        clickButtonWithWait(By.xpath("//li[contains(@class,'edit-icon')]"));

        // the user enters "EDITED" in the main text editor
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("jform_articletext_ifr")));
        enterTextWithWait(By.tagName("body"),
                "EDITED");
        driver.switchTo().defaultContent();

        // the user clicks the "Save" button
        clickButtonDirectly(By.xpath("//button[@class='btn btn-primary']"));
    }

    @AfterEach
    public void tearDown() {
        super.tearDown();
    }

}
