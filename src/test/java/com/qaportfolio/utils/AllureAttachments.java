package com.qaportfolio.utils;

import io.qameta.allure.Attachment;

public final class AllureAttachments {

    private AllureAttachments() {
    }

    @Attachment(value = "{name}", type = "image/png")
    public static byte[] attachScreenshot(String name, byte[] screenshot) {
        return screenshot;
    }

    @Attachment(value = "{name}", type = "text/html")
    public static String attachPageSource(String name, String pageSource) {
        return pageSource;
    }

    @Attachment(value = "{name}", type = "text/plain")
    public static String attachText(String name, String text) {
        return text;
    }
}
