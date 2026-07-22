package com.qaportfolio.utils;

import com.qaportfolio.driver.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ScreenshotHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScreenshotHelper.class);

    private ScreenshotHelper() {
    }

    public static void attachFailureArtifacts(String testName) {
        try {
            WebDriver driver = DriverManager.getDriver();

            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            AllureAttachments.attachScreenshot("Failure screenshot - " + testName, screenshot);
            AllureAttachments.attachPageSource("Page source - " + testName, driver.getPageSource());
        } catch (Exception exception) {
            LOGGER.error("Failed to attach failure artifacts for test: {}", testName, exception);
        }
    }
}
