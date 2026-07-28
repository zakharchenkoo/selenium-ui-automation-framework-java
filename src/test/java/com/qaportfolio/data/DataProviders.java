package com.qaportfolio.data;

import org.testng.annotations.DataProvider;

public final class DataProviders {

    private DataProviders() {
    }

    @DataProvider(name = "tableRecords")
    public static Object[][] tableRecords() {
        return new Object[][]{
                {TestDataFactory.tableRecordData()},
                {TestDataFactory.tableRecordData()},
                {TestDataFactory.tableRecordData()}
        };
    }
}
