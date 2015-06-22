package com.automation.pageObjects;

import java.net.MalformedURLException;
import java.net.URL;
import com.automation.webdriver.Browser;
import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import com.automation.utils.PropertyLoader;
import com.automation.factoryComponents.Sut;

/*
 * Created by demidovskiy-r on 21.06.2015.
 *
 * Base class for all the test classes.
 */

public class TestBase {
	protected final static Logger log = Logger.getLogger(TestBase.class);
	private static ThreadLocal<Sut> sut = new ThreadLocal<>();
	private final static String siteUrl;
	private final static String browserName;
	private final static String tempFolder;

	static {
		siteUrl = PropertyLoader.loadProperty("site.url");
		browserName = PropertyLoader.loadProperty("browser.name");
		tempFolder = PropertyLoader.loadProperty("tests.temp_folder");
	}

	@AfterSuite(alwaysRun = true)
	public void tearDown() {
		stopSut();
	}

	@AfterMethod
	public void setScreenshot(ITestResult result) {
		getSut().getWebDriver().setScreenshot(result);
	}

	protected static Sut getSut() {
		Sut currentSut = sut.get();
		if (currentSut == null) {
			try {
				currentSut = new Sut(new URL(siteUrl), Browser.getByValue(browserName), tempFolder);
				sut.set(currentSut);
			} catch (MalformedURLException ex) {
				ex.printStackTrace();
			}
		}
		return currentSut;
	}

	protected static void stopSut() {
		Sut currentSut = sut.get();
		if (currentSut != null) {
			currentSut.stop();
			sut.remove();
		}
	}
}