package com.mytests.joomela;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import com.mytests.base.BaseTest;
import java.util.ArrayList;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static org.junit.jupiter.api.Assertions.*;

public class AddUserTest extends BaseTest {
    @BeforeEach
    public void setUpUtils() {
        super.setUp();
    }

    @Test
    public void testAddNewUser() {

        // Given the user is on the home page
        driver.get("http://localhost:8080");

        // click on the "Author Login" link
        clickButtonWithWait(By.linkText("Author Login"));

        // the user enters "administrator" in the "Username" field
        enterTextWithWait(By.id("username"), "administrator");

        // the user enters "e2eW3Bt3s71nGB3nchM4rK" in the "Password" field
        enterTextDirectly(By.id("password"), "e2eW3Bt3s71nGB3nchM4rK");

        // click the "Log In" button
        clickButtonDirectly(By.xpath("//button[contains(normalize-space(.), 'Log in')]"));

        // click the "Site Administrator" link
        clickButtonWithWait(By.linkText("Site Administrator"));

        // Switch to the new tab (since a new tab opens)
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        // the user enters "administrator" in the "Username" field
        enterTextWithWait(By.id("mod-login-username"), "administrator");

        // the user enters "e2eW3Bt3s71nGB3nchM4rK" in the "Password" field
        enterTextDirectly(By.id("mod-login-password"), "e2eW3Bt3s71nGB3nchM4rK");

        // the user clicks the "Log in" button
        clickButtonDirectly(By.xpath("//button[contains(normalize-space(.), 'Log in')]"));

        // the user clicks the "Users" link
        WebElement usersLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//h2[contains(text(),'Users')]/following-sibling::ul/li/a[contains(., 'Users')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", usersLink);

        // the user clicks the "New" button
        clickButtonWithWait(By.xpath("//div[@id='toolbar-new']//button"));

        // the user enters "Test User" in the "Name" field
        enterTextWithWait(By.id("jform_name"), "Test User");

        // the user enters "tuser01" in the "Login Name" field
        enterTextDirectly(By.id("jform_username"), "tuser01");

        // the user enters "tpassword" in the "Password" field
        enterTextDirectly(By.id("jform_password"), "tpassword");

        // the user enters "tpassword" in the "Confirm Password" field
        enterTextDirectly(By.id("jform_password2"), "tpassword");

        // the user enters "testmail@example.com" in the "Email" field
        enterTextDirectly(By.id("jform_email"), "testmail01@example.com");

        // the user clicks the "Save & Close" button
        clickButtonDirectly(By.id("toolbar-save"));

        // Verify that the new user appears in the table
        WebElement lastRow = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//table[@id='userList']/tbody/tr[last()]")));
        assertTrue(lastRow.getText().contains("Test User"));
        assertTrue(lastRow.getText().contains("tuser01"));
        assertTrue(lastRow.getText().contains("testmail01@example.com"));
    }

    @AfterEach
    public void tearDown() {
        super.tearDown();
    }
}
