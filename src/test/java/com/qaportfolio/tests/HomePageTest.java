package com.qaportfolio.tests;

import com.qaportfolio.pages.HomePage;
import com.qaportfolio.pages.base.BaseTest;
import com.qaportfolio.steps.HomeSteps;
import io.qameta.allure.*;
import org.testng.annotations.Test;

@Epic("DemoQA UI")
@Feature("Home Page")
public class HomePageTest extends BaseTest {

    @Test(
            groups = {"smoke", "regression"},
            description = "DemoQA home page should display main category cards"
    )
    @Story("Home page category cards")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldDisplayMainCategoryCards() {
        HomePage homePage = new HomePage(driver());
        HomeSteps homeSteps = new HomeSteps(homePage);

        homeSteps
                .verifyHomePageIsLoaded()
                .verifyCategoryIsVisible("Elements")
                .verifyCategoryIsVisible("Forms")
                .verifyCategoryIsVisible("Alerts, Frame & Windows");
    }
}
