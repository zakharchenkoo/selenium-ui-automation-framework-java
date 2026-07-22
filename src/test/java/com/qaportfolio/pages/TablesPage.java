package com.qaportfolio.pages;

import com.qaportfolio.data.TableRecordData;
import com.qaportfolio.pages.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TablesPage extends BasePage {

    private static final String WEB_TABLES_PATH = "/webtables";

    private final By addButton = By.id("addNewRecordButton");
    private final By searchInput = By.id("searchBox");

    private final By firstNameInput = By.id("firstName");
    private final By lastNameInput = By.id("lastName");
    private final By emailInput = By.id("userEmail");
    private final By ageInput = By.id("age");
    private final By salaryInput = By.id("salary");
    private final By departmentInput = By.id("department");
    private final By submitButton = By.id("submit");

    private final By registrationFormModal = By.cssSelector(".modal-content");
    private final By pageBody = By.tagName("body");
    private final By editButton = By.cssSelector("span[id^='edit-record']");
    private final By deleteButton = By.cssSelector("span[id^='delete-record']");

    public TablesPage(WebDriver driver) {
        super(driver);
    }

    public TablesPage open() {
        openPath(WEB_TABLES_PATH);
        waitForVisible(searchInput);
        return this;
    }

    public TablesPage addRecord(TableRecordData recordData) {
        clearSearch();

        waitAndClick(addButton);
        fillRecordForm(recordData);
        submitRecordForm();

        clearSearch();
        searchByKeyword(recordData.email());
        waitUntilTableContains(recordData.email());

        return this;
    }

    public TablesPage editRecord(String existingEmail, TableRecordData updatedRecordData) {
        clearSearch();
        searchByKeyword(existingEmail);
        waitUntilTableContains(existingEmail);

        waitAndClick(editButton);
        fillRecordForm(updatedRecordData);
        submitRecordForm();

        clearSearch();
        searchByKeyword(updatedRecordData.email());
        waitUntilTableContains(updatedRecordData.email());

        return this;
    }

    public TablesPage deleteRecord(String email) {
        clearSearch();
        searchByKeyword(email);
        waitUntilTableContains(email);

        waitAndClick(deleteButton);
        waitUntilTableDoesNotContain(email);

        return this;
    }

    public TablesPage searchByKeyword(String keyword) {
        waitAndType(searchInput, keyword);
        waitUntilSearchValueIs(keyword);
        return this;
    }

    public TablesPage clearSearch() {
        WebElement search = waitForVisible(searchInput);
        scrollToElement(search);
        search.click();
        search.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        search.sendKeys(Keys.BACK_SPACE);

        return this;
    }

    public boolean isRecordDisplayed(String email) {
        return getCurrentPageText().contains(email);
    }

    public boolean isRecordDisplayed(TableRecordData recordData) {
        return isRecordDisplayed(recordData.email());
    }

    public String getRecordRowText(String email) {
        waitUntilTableContains(email);
        return getCurrentPageText();
    }

    public String getVisibleTableText() {
        return getCurrentPageText();
    }

    private void fillRecordForm(TableRecordData recordData) {
        waitAndType(firstNameInput, recordData.firstName());
        waitAndType(lastNameInput, recordData.lastName());
        waitAndType(emailInput, recordData.email());
        waitAndType(ageInput, String.valueOf(recordData.age()));
        waitAndType(salaryInput, String.valueOf(recordData.salary()));
        waitAndType(departmentInput, recordData.department());

        waitUntilInputValueIs(emailInput, recordData.email());
    }

    private void submitRecordForm() {
        waitAndClick(submitButton);
        waitForInvisible(registrationFormModal);
    }

    private void waitUntilTableContains(String expectedText) {
        logger.debug("Waiting until table contains text: {}", expectedText);

        try {
            wait.until(driver -> getCurrentPageText().contains(expectedText));
        } catch (TimeoutException exception) {
            throw new AssertionError(
                    "Table does not contain expected text: " + expectedText
                            + ". Visible table text: " + getCurrentPageText(),
                    exception
            );
        }
    }

    private void waitUntilTableDoesNotContain(String text) {
        logger.debug("Waiting until table does not contain text: {}", text);

        try {
            wait.until(driver -> !getCurrentPageText().contains(text));
        } catch (TimeoutException exception) {
            throw new AssertionError(
                    "Table still contains text: " + text
                            + ". Visible table text: " + getCurrentPageText(),
                    exception
            );
        }
    }

    private void waitUntilSearchValueIs(String expectedValue) {
        wait.until(driver -> expectedValue.equals(waitForVisible(searchInput).getDomProperty("value")));
    }

    private void waitUntilInputValueIs(By locator, String expectedValue) {
        wait.until(driver -> expectedValue.equals(waitForVisible(locator).getDomProperty("value")));
    }

    private String getCurrentPageText() {
        try {
            WebElement body = driver.findElement(pageBody);
            return getElementText(body);
        } catch (NoSuchElementException | StaleElementReferenceException exception) {
            return "";
        }
    }

    private String getElementText(WebElement element) {
        Object text = ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].innerText || arguments[0].textContent;",
                element
        );

        return text == null ? "" : text.toString().trim();
    }
}