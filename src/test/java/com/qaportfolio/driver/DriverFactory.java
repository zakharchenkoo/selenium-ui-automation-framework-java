package com.qaportfolio.driver;

import com.qaportfolio.config.AppConfig;
import com.qaportfolio.enums.BrowserType;
import com.qaportfolio.enums.RunMode;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.LocalFileDetector;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public final class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver createDriver() {
        AppConfig config = AppConfig.getInstance();
        BrowserType browserType = BrowserType.from(config.browser());
        RunMode runMode = RunMode.from(config.runMode());

        return switch (runMode) {
            case LOCAL -> createLocalDriver(browserType, config);
            case REMOTE -> createRemoteDriver(browserType, config);
        };
    }

    private static WebDriver createLocalDriver(BrowserType browserType, AppConfig config) {
        return switch (browserType) {
            case CHROME -> {
                WebDriverManager.chromedriver().setup();
                yield new ChromeDriver(createChromeOptions(config, false));
            }
            case FIREFOX -> {
                WebDriverManager.firefoxdriver().setup();
                yield new FirefoxDriver(createFirefoxOptions(config, false));
            }
        };
    }

    private static WebDriver createRemoteDriver(BrowserType browserType, AppConfig config) {
        boolean isSelenoid = "selenoid".equalsIgnoreCase(config.remoteProvider());

        MutableCapabilities options = switch (browserType) {
            case CHROME -> createChromeOptions(config, isSelenoid);
            case FIREFOX -> createFirefoxOptions(config, isSelenoid);
        };

        try {
            RemoteWebDriver remoteDriver = new RemoteWebDriver(new URL(config.remoteUrl()), options);
            remoteDriver.setFileDetector(new LocalFileDetector());
            return remoteDriver;
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Invalid remote URL: " + config.remoteUrl(), e);
        }
    }

    private static ChromeOptions createChromeOptions(AppConfig config, boolean isSelenoid) {
        ChromeOptions options = new ChromeOptions();

        options.setAcceptInsecureCerts(true);

        options.addArguments(
                "--disable-notifications",
                "--disable-popup-blocking",
                "--remote-allow-origins=*",
                "--ignore-certificate-errors",
                "--allow-insecure-localhost",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--window-size=1920,1080"
        );

        if (config.headless()) {
            options.addArguments("--headless=new");
        }

        if (isSelenoid) {
            addSelenoidOptions(options);
        }

        return options;
    }

    private static FirefoxOptions createFirefoxOptions(AppConfig config, boolean isSelenoid) {
        FirefoxOptions options = new FirefoxOptions();

        options.setAcceptInsecureCerts(true);

        if (config.headless()) {
            options.addArguments("-headless");
        }

        if (isSelenoid) {
            addSelenoidOptions(options);
        }

        return options;
    }

    private static void addSelenoidOptions(MutableCapabilities options) {
        options.setCapability("selenoid:options", Map.of(
                "enableVNC", true,
                "enableVideo", false,
                "screenResolution", "1920x1080x24"
        ));
    }
}
