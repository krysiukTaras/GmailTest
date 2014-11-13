package com.epam.elements;


import org.openqa.selenium.WebElement;

public class CheckBox extends Element {

    public CheckBox(WebElement element) {
        super(element);
    }

    public void check(){
        element.click();
    }

}
