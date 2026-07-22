package com.qaportfolio.tests;

import com.qaportfolio.data.TableRecordData;
import com.qaportfolio.data.TestDataFactory;
import com.qaportfolio.pages.TablesPage;
import com.qaportfolio.pages.base.BaseTest;
import com.qaportfolio.steps.TableSteps;
import io.qameta.allure.*;
import org.testng.annotations.Test;

@Epic("DemoQA UI")
@Feature("Web Tables")
public class WebTablesTest extends BaseTest {

    @Test(
            groups = {"smoke", "regression", "tables"},
            description = "User should be able to add a new record to the web table"
    )
    @Story("Add table record")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldAddNewRecordToWebTable() {
        TableRecordData recordData = TestDataFactory.tableRecordData();

        TableSteps tableSteps = new TableSteps(new TablesPage(driver()));

        tableSteps
                .openWebTablesPage()
                .addNewRecord(recordData)
                .verifyRecordIsDisplayed(recordData);
    }

    @Test(
            groups = {"regression", "tables"},
            description = "User should be able to edit an existing table record"
    )
    @Story("Edit table record")
    @Severity(SeverityLevel.NORMAL)
    public void shouldEditExistingRecordInWebTable() {
        TableRecordData originalRecord = TestDataFactory.tableRecordData();
        TableRecordData updatedRecord = TestDataFactory.tableRecordData();

        TableSteps tableSteps = new TableSteps(new TablesPage(driver()));

        tableSteps
                .openWebTablesPage()
                .addNewRecord(originalRecord)
                .editRecord(originalRecord.email(), updatedRecord)
                .verifyRecordIsDisplayed(updatedRecord)
                .verifyRecordIsNotDisplayed(originalRecord.email());
    }

    @Test(
            groups = {"regression", "tables"},
            description = "User should be able to delete a table record"
    )
    @Story("Delete table record")
    @Severity(SeverityLevel.NORMAL)
    public void shouldDeleteRecordFromWebTable() {
        TableRecordData recordData = TestDataFactory.tableRecordData();

        TableSteps tableSteps = new TableSteps(new TablesPage(driver()));

        tableSteps
                .openWebTablesPage()
                .addNewRecord(recordData)
                .verifyRecordIsDisplayed(recordData)
                .deleteRecord(recordData.email())
                .verifyRecordIsNotDisplayed(recordData.email());
    }

    @Test(
            groups = {"regression", "tables"},
            description = "User should be able to search table records by keyword"
    )
    @Story("Search table record")
    @Severity(SeverityLevel.NORMAL)
    public void shouldSearchRecordByKeyword() {
        TableRecordData recordData = TestDataFactory.tableRecordData();

        TableSteps tableSteps = new TableSteps(new TablesPage(driver()));

        tableSteps
                .openWebTablesPage()
                .addNewRecord(recordData)
                .searchByKeyword(recordData.email())
                .verifyRecordIsDisplayed(recordData);
    }
}
