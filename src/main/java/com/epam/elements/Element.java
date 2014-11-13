package com.epam.elements;

import org.openqa.selenium.WebElement;

public class Element  implements BaseElement {
    public WebElement element;

    public Element(WebElement element) {
        this.element = element;
    }

    public boolean isDisplayed(){
        return element.isDisplayed();
    }

    public boolean isEnabled(){
        return element.isEnabled();
    }
}
