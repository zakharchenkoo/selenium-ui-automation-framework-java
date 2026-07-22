package com.qaportfolio.pages;

import com.qaportfolio.pages.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private final By categoryCards = By.cssSelector(".category-cards .card");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isElementDisplayed(categoryCards);
    }

    public boolean isCategoryVisible(String categoryName) {
        By categoryLocator = By.xpath(
                "//div[contains(@class,'card')]//h5[text()='" + categoryName + "']"
        );

        return isElementDisplayed(categoryLocator);
    }
}
