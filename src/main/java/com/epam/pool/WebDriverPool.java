package com.epam.pool;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class WebDriverPool {

    Map<WebDriver, Boolean> drivers;
    public  int poolSize;
    private Lock lock;

    public WebDriverPool(int poolSize) {
       lock = new ReentrantLock();
       this.poolSize = poolSize;
    }


    public WebDriver getDriver() {
        lock.lock();
        if (drivers == null){
            drivers = new HashMap<WebDriver, Boolean>();
            WebDriver driver  =  new FirefoxDriver();
            drivers.put(driver, true);
            lock.unlock();
            return driver;
        }
        while(true) {
            for (Map.Entry<WebDriver, Boolean> driver : drivers.entrySet()) {
                if (!driver.getValue()){
                    drivers.put(driver.getKey(), true);
                    lock.unlock();
                return driver.getKey();}
            }

            if (drivers.size() < poolSize) {
                WebDriver driver = new FirefoxDriver();
                drivers.put(driver, true);
                lock.unlock();
                return driver;
            }
            lock.unlock();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
        }
    }


    public void releaseDriver(WebDriver driver) {
        lock.lock();
        driver.manage().deleteAllCookies();
        drivers.put(driver, false);
        lock.unlock();
    }


    public void releaseAll() {
        for (Map.Entry<WebDriver, Boolean> d: drivers.entrySet()){
            d.setValue(false);
        }
    }

    public void closeAll() {
        for (Map.Entry<WebDriver, Boolean> d: drivers.entrySet()){
            d.getKey().close();
            d.getKey().quit();
        }
    }

}
