package com.automation.factoryComponents;

import com.automation.pageObjects.HomePage;
import com.automation.webdriver.ExtendedWebDriver;

/*
 * Created by demidovskiy-r on 21.06.2015.
 *
 * Creator has returned instance of particular page
 */
public class PageCreator extends PageFactory {

    public PageCreator(ExtendedWebDriver webDriver) {
        super(webDriver);
    }

    public HomePage getHomePage() {
        return getPage(HomePage.class);
    }
}