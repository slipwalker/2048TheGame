package com.automation.webdriver;

/*
 * Created by demidovskiy-r on 22.06.2015.
 */
public enum Browser {
    FIREFOX("firefox", "firefox"),
    CHROME("chrome", "chrome"),
    SAFARI("safari", "safari"),
    IE("ie", "internet explorer"),
    REMOTE_FIREFOX("remoteFirefox", "firefox"),
    REMOTE_IE("remoteIE", "internet explorer"),
    REMOTE_CHROME("remoteChrome", "chrome"),
    REMOTE_SAFARI("remoteSafari", "safari");

    private final String browser;
    private final String browserName;

    Browser(String browser, String browserName) {
        this.browser = browser;
        this.browserName = browserName;
    }

    @Override
    public String toString() {
        return browser;
    }

    public String getBrowserName() {
        return browserName;
    }

    public static Browser getByValue(String value) {
        for (Browser element : values()) {
            if (element.toString().equalsIgnoreCase(value)) {
                return element;
            }
        }
        throw new IllegalArgumentException("Unknown browser: " + value);
    }
}