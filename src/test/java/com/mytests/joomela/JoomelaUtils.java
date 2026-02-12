package com.mytests.joomela;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mytests.base.BaseTest;

public class JoomelaUtils extends BaseTest {

    public JoomelaUtils(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void login(String username, String password) {
        driver.get("http://localhost:8080");
        clickButtonWithWait(By.linkText("Author Login"));
        enterTextWithWait(By.id("username"), username);
        enterTextDirectly(By.id("password"), password);
        clickButtonDirectly(By.xpath("//button[contains(normalize-space(.), 'Log in')]"));
    }

    public void logout() {
        clickButtonWithWait(By.xpath("//a[contains(normalize-space(text()), 'Log out')]"));
    }
    // Other utility methods like clickButton, getElementText, etc.
}
