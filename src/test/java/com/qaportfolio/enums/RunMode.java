package com.qaportfolio.enums;

public enum RunMode {
    LOCAL,
    REMOTE;

    public static RunMode from(String value) {
        if (value == null || value.isBlank()) {
            return LOCAL;
        }

        return RunMode.valueOf(value.trim().toUpperCase());
    }
}
