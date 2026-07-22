package com.qaportfolio.driver;

import com.qaportfolio.config.AppConfig;
import com.qaportfolio.enums.BrowserType;
import com.qaportfolio.enums.RunMode;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.net.URI;
import java.util.Map;

public final class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver createDriver() {
        AppConfig config = AppConfig.getInstance();
        BrowserType browser = BrowserType.from(config.browser());
        RunMode runMode = RunMode.from(config.runMode());

        return switch (runMode) {
            case LOCAL -> createLocalDriver(browser, config.headless());
            case REMOTE -> createRemoteDriver(browser, config);
        };
    }

    private static WebDriver createLocalDriver(BrowserType browser, boolean headless) {
        return switch (browser) {
            case CHROME -> {
                WebDriverManager.chromedriver().setup();
                yield new org.openqa.selenium.chrome.ChromeDriver(createChromeOptions(headless));
            }
            case FIREFOX -> {
                WebDriverManager.firefoxdriver().setup();
                yield new org.openqa.selenium.firefox.FirefoxDriver(createFirefoxOptions(headless));
            }
        };
    }

    private static WebDriver createRemoteDriver(BrowserType browser, AppConfig config) {
        try {
            return switch (browser) {
                case CHROME -> new RemoteWebDriver(
                        URI.create(config.remoteUrl()).toURL(),
                        createChromeOptions(config.headless())
                );
                case FIREFOX -> new RemoteWebDriver(
                        URI.create(config.remoteUrl()).toURL(),
                        createFirefoxOptions(config.headless())
                );
            };
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to create remote WebDriver.", exception);
        }
    }

    private static ChromeOptions createChromeOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--remote-allow-origins=*");

        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }

        options.setCapability("selenoid:options", Map.of(
                "enableVNC", true,
                "enableVideo", true,
                "screenResolution", "1920x1080x24"
        ));

        return options;
    }

    private static FirefoxOptions createFirefoxOptions(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();

        if (headless) {
            options.addArguments("-headless");
        }

        options.setCapability("selenoid:options", Map.of(
                "enableVNC", true,
                "enableVideo", true,
                "screenResolution", "1920x1080x24"
        ));

        return options;
    }
}
