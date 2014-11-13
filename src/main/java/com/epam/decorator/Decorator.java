package com.epam.decorator;

import com.epam.elements.BaseElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import org.openqa.selenium.support.pagefactory.internal.*;


import java.lang.reflect.*;
import java.util.List;

public class Decorator implements FieldDecorator{

    ElementLocatorFactory factory;

    public Decorator(SearchContext context) {
        factory = new DefaultElementLocatorFactory(context);
    }

    @Override
    public Object decorate(ClassLoader loader, Field field) {
        if(!(isWrapElement(field)||isListWrapElements(field))){
            return null;
        }
        ElementLocator locator = factory.createLocator(field);

        if (locator == null) {
            return null;
        }

        if(isWrapElement(field)){
            return proxyForElement(loader, locator, field.getType());
        }

        if(isListWrapElements(field)){
            Class<?> wrapClass = wrapListClass(field);
            return proxyForListElements(loader, locator, wrapClass);
        }

        return null;
    }



    private <T> List<T> proxyForListElements(ClassLoader loader, ElementLocator locator, Class<T> type) {
        InvocationHandler handler = new LocatingElementListHandler(locator, type);
        List<T> proxy = (List<T>) Proxy.newProxyInstance(
                loader, new Class[] {List.class}, handler);
        return proxy;



    }

    private <T> T proxyForElement(ClassLoader loader, ElementLocator locator, Class<T> type) {
        InvocationHandler handler = new LocatingElementHandler(locator);
        T proxy = (T) Proxy.newProxyInstance(
                loader, new Class[]{WebElement.class}, handler);
        try {
            return type.getConstructor(WebElement.class).newInstance(proxy);
        } catch (Exception e) {
           throw new RuntimeException(e);
        }
    }







    public boolean isWrapElement(Field field){
        if(BaseElement.class.isAssignableFrom(field.getType())){
            return true;
        }
        return false;
    }

    public boolean isListWrapElements(Field field){
        if(List.class.isAssignableFrom(field.getType())){
            return true;
        }
        return false;
    }

    public Class wrapListClass(Field field){
        Type genericType = field.getGenericType();
        if (!(genericType instanceof ParameterizedType)) {
            return null;
        }

        return (Class) ((ParameterizedType) genericType).getActualTypeArguments()[0];
    }

}
