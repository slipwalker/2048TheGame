package com.automation.factoryComponents;

/*
 * Created by demidovskiy-r on 21.06.2015.
 *
 * Interface for implementing loadable behavior of pages
 */
public interface LoadableComponent<T> {
    public void load();
    public void unload();
    public void isLoaded() throws java.lang.Error;
    public T get();
}