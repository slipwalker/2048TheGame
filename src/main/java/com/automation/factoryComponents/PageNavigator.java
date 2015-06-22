package com.automation.factoryComponents;

import com.automation.pageObjects.HomePage;
import com.automation.webdriver.ExtendedWebDriver;
import org.apache.log4j.Logger;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/*
 * Created by demidovskiy-r on 21.06.2015.
 *
 * Navigates to particular page and then returns related instance
 */
public class PageNavigator extends PageCreator {
    private final Map<Class, String> pageLinksMap = new HashMap<>();
    private final static Logger log = Logger.getLogger(PageNavigator.class);

    public PageNavigator(ExtendedWebDriver webDriver, URL baseUrl) {
        super(webDriver);
        initPageLinksMap(baseUrl);
    }

    private void initPageLinksMap(URL baseUrl) {
        pageLinksMap.put(HomePage.class, baseUrl + "/2048/");
    }

    public HomePage getHomePage() {
        navigateTo(HomePage.class);
        return super.getHomePage();
    }

    public void navigateTo(Class pageClassToProxy) {
        navigateTo(pageClassToProxy, "");
    }

    public void navigateTo(Class pageClassToProxy, String... urlParam) {
        String desiredUrl = String.format(pageLinksMap.get(pageClassToProxy), urlParam);
        if (!isItCurrentUrl(desiredUrl)) {
            webDriver.get(desiredUrl);
        }
    }

    public boolean isItCurrentUrl(String desiredUrl) {
        return webDriver.getCurrentUrl().equalsIgnoreCase(desiredUrl);
    }
}