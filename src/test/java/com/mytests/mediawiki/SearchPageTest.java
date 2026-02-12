package com.mytests.mediawiki;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import com.mytests.base.BaseTest;

public class SearchPageTest extends BaseTest {
    @BeforeEach
    public void setUpUtils() {
        super.setUp();
    }

    @Test
    public void testSearchPage() {
        // the user is on the home page
        driver.get("http://localhost:8080");

        // the user enters "Software testing" in the search bar
        enterTextWithWait(By.id("searchInput"), "Software testing");

        // the user presses Enter
        pressKey(By.id("searchInput"), Keys.RETURN);

        // the page "Software testing" is displayed
        assertTextInElement(By.xpath("//h1[contains(@id,'firstHeading')]//span[contains(@class,'mw-page-title-main')]"),
                "Software testing");
    }

    @AfterEach
    public void tearDown() {
        super.tearDown();
    }

}
