package com.qaportfolio.steps;

import io.qameta.allure.Step;
import com.qaportfolio.data.TableRecordData;
import com.qaportfolio.pages.TablesPage;

import static org.assertj.core.api.Assertions.assertThat;

public class TableSteps {

    private final TablesPage tablesPage;

    public TableSteps(TablesPage tablesPage) {
        this.tablesPage = tablesPage;
    }

    @Step("Open web tables page")
    public TableSteps openWebTablesPage() {
        tablesPage.open();
        return this;
    }

    @Step("Add new table record")
    public TableSteps addNewRecord(TableRecordData recordData) {
        tablesPage.addRecord(recordData);
        return this;
    }

    @Step("Edit table record with email: {existingEmail}")
    public TableSteps editRecord(String existingEmail, TableRecordData updatedRecordData) {
        tablesPage.editRecord(existingEmail, updatedRecordData);
        return this;
    }

    @Step("Delete table record with email: {email}")
    public TableSteps deleteRecord(String email) {
        tablesPage.deleteRecord(email);
        return this;
    }

    @Step("Search table by keyword: {keyword}")
    public TableSteps searchByKeyword(String keyword) {
        tablesPage.searchByKeyword(keyword);
        return this;
    }

    @Step("Verify that table record is displayed")
    public TableSteps verifyRecordIsDisplayed(TableRecordData recordData) {
        assertThat(tablesPage.isRecordDisplayed(recordData))
                .as(
                        "Record with email '%s' should be displayed. Visible table text: %s",
                        recordData.email(),
                        tablesPage.getVisibleTableText()
                )
                .isTrue();

        String rowText = tablesPage.getRecordRowText(recordData.email());

        assertThat(rowText)
                .as("Record row should contain first name")
                .contains(recordData.firstName());

        assertThat(rowText)
                .as("Record row should contain last name")
                .contains(recordData.lastName());

        assertThat(rowText)
                .as("Record row should contain email")
                .contains(recordData.email());

        assertThat(rowText)
                .as("Record row should contain age")
                .contains(String.valueOf(recordData.age()));

        assertThat(rowText)
                .as("Record row should contain salary")
                .contains(String.valueOf(recordData.salary()));

        assertThat(rowText)
                .as("Record row should contain department")
                .contains(recordData.department());

        return this;
    }

    @Step("Verify that table record with email '{email}' is not displayed")
    public TableSteps verifyRecordIsNotDisplayed(String email) {
        assertThat(tablesPage.isRecordDisplayed(email))
                .as("Record with email '%s' should not be displayed", email)
                .isFalse();

        return this;
    }
}
