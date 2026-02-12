package com.mytests.mediawiki;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.mytests.base.BaseTest;

public class MediaWikiUtils extends BaseTest {
    public MediaWikiUtils(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void login(String username, String password) {
        driver.get("http://localhost:8080");
        clickButtonWithWait(By.cssSelector("#pt-login"));
        enterTextWithWait(By.cssSelector("#wpName1"), username);
        enterTextWithWait(By.cssSelector("#wpPassword1"), password);
        clickButtonDirectly(By.cssSelector("#wpLoginAttempt"));
    }
}
