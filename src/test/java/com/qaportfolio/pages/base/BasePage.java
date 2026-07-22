package com.qaportfolio.pages.base;

import com.qaportfolio.config.AppConfig;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(AppConfig.getInstance().explicitWait())
        );
    }

    protected void openPath(String path) {
        String baseUrl = AppConfig.getInstance().baseUrl();
        String normalizedPath = path.startsWith("/") ? path : "/" + path;

        logger.debug("Opening page path: {}", normalizedPath);
        driver.get(baseUrl + normalizedPath);
    }

    protected WebElement waitForVisible(By locator) {
        logger.debug("Waiting for element to be visible: {}", locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickable(By locator) {
        logger.debug("Waiting for element to be clickable: {}", locator);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitAndClick(By locator) {
        logger.debug("Clicking element: {}", locator);
        WebElement element = waitForClickable(locator);
        scrollToElement(element);

        try {
            element.click();
        } catch (ElementClickInterceptedException exception) {
            logger.debug("Regular click was intercepted. Clicking with JavaScript: {}", locator);
            clickWithJavaScript(element);
        }
    }

    protected void waitAndType(By locator, String text) {
        logger.debug("Typing text into element: {}", locator);
        WebElement element = waitForVisible(locator);
        scrollToElement(element);
        element.click();
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.BACK_SPACE);
        element.sendKeys(text);
    }

    protected boolean isElementDisplayed(By locator) {
        try {
            return waitForVisible(locator).isDisplayed();
        } catch (TimeoutException exception) {
            logger.debug("Element is not displayed: {}", locator);
            return false;
        }
    }

    protected String getText(By locator) {
        logger.debug("Getting text from element: {}", locator);
        return waitForVisible(locator).getText();
    }

    protected void scrollToElement(WebElement element) {
        logger.debug("Scrolling to element: {}", element);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                element
        );
    }

    protected void clickWithJavaScript(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    protected void waitForInvisible(By locator) {
        logger.debug("Waiting for element to be invisible: {}", locator);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected boolean isElementPresent(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        return !elements.isEmpty() && elements.getFirst().isDisplayed();
    }
}
