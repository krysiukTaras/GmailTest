package com.epam.elements;

import org.openqa.selenium.WebElement;


public class TextBox extends Element {

    public TextBox(WebElement element) {
        super(element);
    }

    public void setText(String text){
        element.clear();
        element.sendKeys(text);
    }

    public String getText(){
        return element.getText();
    }
}
