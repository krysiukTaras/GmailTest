package com.epam.elements;

import com.epam.control.Constants;
import com.epam.decorator.Decorator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MessageElement extends Element {

    @FindBy(css = "div.pG")
    private CheckBox important;

    String mailFrom;
    String subject;

    public MessageElement(WebElement element) {
        super(element);
        PageFactory.initElements(new Decorator(element), this);
        mailFrom = element.findElement(By.cssSelector("div.yW span")).getAttribute("email");
        subject = element.findElement(By.cssSelector(Constants.MESSAGE_SUBJECT_CSS)).getText();
    }

    public void clickTeachGmail(){
        important.check();
    }

    public boolean isImportant(){
        try{
            important.element.findElement(By.cssSelector("div.pH-A7"));
            return false;
        } catch (NoSuchElementException e){
            return true;
        }
    }

    public String getMailFrom(){
        return mailFrom;
    }

    public String getMailSubject(){
        return subject;
    }
}
