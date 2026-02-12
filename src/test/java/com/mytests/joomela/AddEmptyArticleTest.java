package com.mytests.joomela;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.mytests.base.BaseTest;
import static org.junit.jupiter.api.Assertions.*;

public class AddEmptyArticleTest extends BaseTest {
    private JoomelaUtils webDriverUtils;

    @BeforeEach
    public void setUpUtils() {
        super.setUp();
        webDriverUtils = new JoomelaUtils(driver, wait);
    }

    @Test
    public void testAddEmptyArticle() {

        // the user logs in
        webDriverUtils.login("administrator", "e2eW3Bt3s71nGB3nchM4rK");

        // the user clicks on the "create a post" link
        clickButtonWithWait(By.linkText("Create a Post"));

        // the user clicks the "Save" button
        clickButtonDirectly(By.xpath("//button[@class='btn btn-primary']"));

        // the user waits for the error message to appear
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'alert-error')]//div")));
        String errorText = errorMessage.getText();
        assertTrue(errorText.contains("Invalid field:  Title "));

    }

    @AfterEach
    public void tearDown() {
        super.tearDown();
    }
}
