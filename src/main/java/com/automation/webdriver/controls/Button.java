package com.automation.webdriver.controls;

import com.automation.pageObjects.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/*
 * Created by demidovskiy-r on 21.06.2015.
 */
public class Button extends Control {
    public Button(Page parent, WebElement webElement) {
        super(parent, webElement);
    }

    public Button(Page parent, By locator) {
        super(parent, locator);
    }
}