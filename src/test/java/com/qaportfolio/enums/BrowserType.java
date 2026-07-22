package com.qaportfolio.enums;

public enum BrowserType {
    CHROME,
    FIREFOX;

    public static BrowserType from(String value) {
        if (value == null || value.isBlank()) {
            return CHROME;
        }

        return BrowserType.valueOf(value.trim().toUpperCase());
    }
}
