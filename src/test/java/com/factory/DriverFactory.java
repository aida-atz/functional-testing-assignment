package com.factory;

import com.enums.Browser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverFactory {
    public static WebDriver getNewDriverInstance(Browser browser) {
        switch (browser) {
            case CHROME:
                return new ChromeDriver();
            case FIREFOX:
                return new FirefoxDriver();
            case EDGE:
                return new EdgeDriver();
            default:
                throw new IllegalArgumentException("Unsupported browser");
        }
    }
}