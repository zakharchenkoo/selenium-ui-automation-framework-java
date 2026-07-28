package com.qaportfolio.pages;

import com.qaportfolio.data.StudentRegistrationData;
import com.qaportfolio.pages.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class FormPage extends BasePage {

    private static final String PRACTICE_FORM_PATH = "/automation-practice-form";

    private final By firstNameInput = By.id("firstName");
    private final By lastNameInput = By.id("lastName");
    private final By emailInput = By.id("userEmail");
    private final By mobileInput = By.id("userNumber");
    private final By dateOfBirthInput = By.id("dateOfBirthInput");
    private final By monthSelect = By.className("react-datepicker__month-select");
    private final By yearSelect = By.className("react-datepicker__year-select");
    private final By subjectsInput = By.id("subjectsInput");
    private final By uploadPictureInput = By.id("uploadPicture");
    private final By currentAddressInput = By.id("currentAddress");
    private final By stateInput = By.id("react-select-3-input");
    private final By cityInput = By.id("react-select-4-input");
    private final By submitButton = By.id("submit");
    private final By resultModalTitle = By.id("example-modal-sizes-title-lg");

    public FormPage(WebDriver driver) {
        super(driver);
    }

    public FormPage open() {
        openPath(PRACTICE_FORM_PATH);
        return this;
    }

    public FormPage fillStudentRegistrationForm(StudentRegistrationData data) {
        waitAndType(firstNameInput, data.firstName());
        waitAndType(lastNameInput, data.lastName());
        waitAndType(emailInput, data.email());
        selectGender(data.gender());
        waitAndType(mobileInput, data.mobileNumber());
        selectDateOfBirth(data.dateOfBirth().getDayOfMonth(), data.dateOfBirth().getMonth(), data.dateOfBirth().getYear());
        selectSubject(data.subject());
        selectHobby(data.hobby());
        uploadPicture(data.picturePath().toAbsolutePath().toString());
        waitAndType(currentAddressInput, data.currentAddress());
        selectState(data.state());
        selectCity(data.city());

        return this;
    }

    public FormPage fillRequiredFieldsOnly(String firstName, String lastName, String mobileNumber) {
        waitAndType(firstNameInput, firstName);
        waitAndType(lastNameInput, lastName);
        waitAndType(mobileInput, mobileNumber);
        return this;
    }

    public FormPage submit() {
        waitAndClick(submitButton);
        return this;
    }

    public boolean isResultModalDisplayed() {
        return isElementDisplayed(resultModalTitle);
    }

    public String getSubmittedValue(String label) {
        By valueLocator = By.xpath("//td[text()='" + label + "']/following-sibling::td");
        return getText(valueLocator);
    }

    private void selectGender(String gender) {
        By genderLabel = By.xpath("//label[text()='" + gender + "']");
        waitAndClick(genderLabel);
    }

    private void selectHobby(String hobby) {
        By hobbyLabel = By.xpath("//label[text()='" + hobby + "']");
        waitAndClick(hobbyLabel);
    }

    private void selectSubject(String subject) {
        waitAndType(subjectsInput, subject);
        waitForVisible(subjectsInput).sendKeys(Keys.ENTER);
    }

    private void uploadPicture(String absoluteFilePath) {
        logger.debug("Uploading file: {}", absoluteFilePath);
        waitForVisible(uploadPictureInput).sendKeys(absoluteFilePath);
    }

    private void selectState(String state) {
        waitAndType(stateInput, state);
        waitForVisible(stateInput).sendKeys(Keys.ENTER);
    }

    private void selectCity(String city) {
        waitAndType(cityInput, city);
        waitForVisible(cityInput).sendKeys(Keys.ENTER);
    }

    private void selectDateOfBirth(int day, Month month, int year) {
        waitAndClick(dateOfBirthInput);

        waitForVisible(monthSelect).sendKeys(month.getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        waitForVisible(yearSelect).sendKeys(String.valueOf(year));

        By dayLocator = By.xpath(
                "//div[contains(@class,'react-datepicker__day') " +
                        "and not(contains(@class,'outside-month')) " +
                        "and text()='" + day + "']"
        );

        waitAndClick(dayLocator);
    }
}
