package com.automation.webdriver.controls;

import com.automation.pageObjects.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/*
 * Created by demidovskiy-r on 21.06.2015.
 *
 * Base class for UI controls.
 */
public abstract class Control {
    protected Page parent;
    protected WebElement webElement;
    protected By locator;

    public Control(Page parent, WebElement webElement) {
        this.parent = parent;
        this.webElement = webElement;
    }

    public Control(Page parent, By locator) {
        this.parent = parent;
        this.locator = locator;
    }

    public Page click() {
        init().click();
        return parent;
    }

    public WebElement getWebElement() {
        return init();
    }

    protected WebElement init() {
        if (webElement == null && locator != null) {
            webElement = parent.getDriver().findElement(locator);
        }
        return webElement;
    }
}