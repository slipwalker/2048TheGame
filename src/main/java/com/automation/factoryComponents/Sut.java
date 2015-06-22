package com.automation.factoryComponents;

import com.automation.webdriver.Browser;
import com.automation.webdriver.ExtendedWebDriver;
import org.apache.log4j.Logger;
import java.io.File;
import java.net.URL;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * Created by demidovskiy-r on 21.06.2015.
 *
 * Class manages creating browser instance and navigator/creator of pages
 */
public class Sut {
    private static Logger log = Logger.getLogger(Sut.class);
    private static Lock webDriverLock = new ReentrantLock();
    private ExtendedWebDriver webDriver;
    private PageNavigator pageNavigator;
    private PageCreator pageCreator;
    private URL siteUrl;
    private Browser siteBrowser;
    private String tempFolder;

    public Sut(URL siteUrl, Browser desiredBrowser, String tempFolder) {
        this.siteUrl = siteUrl;
        this.siteBrowser = desiredBrowser;
        this.tempFolder = tempFolder;
    }

    public void stop() {
        getWebDriver().quit();
    }

    public ExtendedWebDriver getWebDriver() {
        if (webDriver == null) {
            webDriverLock.lock();
            log.info("Starting browser " + siteBrowser + " ...");
            try {
                webDriver = new ExtendedWebDriver(siteBrowser, new File(tempFolder));
            } finally {
                webDriverLock.unlock();
            }
        }
        return webDriver;
    }

    public PageNavigator getPageNavigator() {
        if (pageNavigator == null) {
            pageNavigator = new PageNavigator(getWebDriver(), this.siteUrl);
        }
        return pageNavigator;
    }

    public PageCreator getPageCreator() {
        if (pageCreator == null) {
            pageCreator = new PageCreator(getWebDriver());
        }
        return pageCreator;
    }
}