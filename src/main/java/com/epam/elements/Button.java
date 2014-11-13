package com.epam.elements;

import org.openqa.selenium.WebElement;

public class Button extends Element {

    public Button(WebElement element) {
        super(element);
    }

    public void click(){
        element.click();
    }

}
