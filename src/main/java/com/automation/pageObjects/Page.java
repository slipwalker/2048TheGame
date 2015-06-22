package com.automation.pageObjects;

import com.automation.factoryComponents.LoadableComponent;
import com.automation.webdriver.ExtendedWebDriver;

/*
 * Created by demidovskiy-r on 21.06.2015.
 *
 * Abstract class representation of a Page in the UI
 */
public abstract class Page<T> implements LoadableComponent {
	protected ExtendedWebDriver web;

	public Page(ExtendedWebDriver web) {
		this.web = web;
		get();
		init();
	}

	public abstract void init();

	public T get() {
		try {
			isLoaded();
			return (T) this;
		} catch (Throwable e) {
			load();
		}
		isLoaded();
		return (T) this;
	}

	public ExtendedWebDriver getDriver() {
		return web;
	}
}