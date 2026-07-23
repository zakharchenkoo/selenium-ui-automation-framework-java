package com.qaportfolio.pages.base;

import com.qaportfolio.config.AppConfig;
import com.qaportfolio.driver.DriverFactory;
import com.qaportfolio.driver.DriverManager;
import com.qaportfolio.utils.ScreenshotHelper;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public abstract class BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        AppConfig config = AppConfig.getInstance();

        WebDriver driver = DriverFactory.createDriver();
        DriverManager.setDriver(driver);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.implicitWait()));
        driver.manage().window().maximize();
        driver.get(config.baseUrl());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver();
    }

    protected WebDriver driver() {
        return DriverManager.getDriver();
    }
}
