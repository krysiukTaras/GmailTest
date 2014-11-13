package com.epam.decorator;


import com.epam.elements.BaseElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class LocatingElementListHandler implements InvocationHandler {

    private final ElementLocator locator;
    private final Class<?> wrapType;


    public LocatingElementListHandler(ElementLocator locator, Class<?> wrapType) {
        this.locator = locator;
        this.wrapType = wrapType;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<BaseElement> elementList = new ArrayList<BaseElement>();
        for (WebElement e : locator.findElements()) {
            elementList.add((BaseElement) wrapType.getConstructor(WebElement.class).newInstance(e));
        }
        try {
            return method.invoke(elementList, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
