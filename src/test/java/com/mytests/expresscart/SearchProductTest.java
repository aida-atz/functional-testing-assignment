package com.mytests.expresscart;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SearchProductTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeAll
    void setUp() {
        // System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterAll
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void searchesProductInStore() {
        // Given the user is on the home page
        driver.get("http://localhost:3000/"); // Adjust URL as needed

        // When the user enters "NewProduct000" in the "Search shop" field
        WebElement searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("frm_search")));
        searchField.clear();
        searchField.sendKeys("NewProduct000");

        // And clicks the "Search" button
        WebElement searchButton = driver.findElement(
                By.xpath("//button[contains(text(),'Search')]"));
        searchButton.click();

        // Then "NewProduct000" is shown to the right of "Search results:"
        WebElement searchResults = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(
                        "//div[contains(@class,'product-layout product-results')]//h1//strong")));
        Assertions.assertTrue(searchResults.getText().contains("NewProduct000"));

        // And "NewProduct000" is the only product shown in the results
        List<WebElement> productResults = driver.findElements(
                By.xpath("//*[contains(@class,'product-title') and contains(text(),'NewProduct000')]"));
        Assertions.assertEquals(1, productResults.size());
    }
}
