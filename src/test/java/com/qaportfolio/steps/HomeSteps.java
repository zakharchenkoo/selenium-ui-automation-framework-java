package com.qaportfolio.steps;

import com.qaportfolio.pages.HomePage;
import io.qameta.allure.Step;

import static org.assertj.core.api.Assertions.assertThat;

public class HomeSteps {

    private final HomePage homePage;

    public HomeSteps(HomePage homePage) {
        this.homePage = homePage;
    }

    @Step("Verify that DemoQA home page is loaded")
    public HomeSteps verifyHomePageIsLoaded() {
        assertThat(homePage.isLoaded())
                .as("DemoQA home page should be loaded")
                .isTrue();

        return this;
    }

    @Step("Verify that category '{categoryName}' is visible")
    public HomeSteps verifyCategoryIsVisible(String categoryName) {
        assertThat(homePage.isCategoryVisible(categoryName))
                .as("Category '%s' should be visible", categoryName)
                .isTrue();

        return this;
    }
}
