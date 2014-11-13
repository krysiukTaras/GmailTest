package com.epam.pages;

import com.epam.control.Constants;
import com.epam.decorator.Decorator;
import com.epam.elements.Button;
import com.epam.elements.MessageElement;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class InboxPage extends BasePage {

    @FindBy(xpath = Constants.INBOX_TABLE_XPATH)
    public List<MessageElement> messages;

    @FindBy(css = Constants.BUTTON_IMPORTANT)
    public Button important;


    public InboxPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new Decorator(driver), this);
    }

    public ImportantPage importantButtonClick(){
        important.click();
        return new ImportantPage(driver);
    }
    public List<MessageElement> getAllMessage(){
        return messages;
    }

    public boolean isDisplayed(){
        return important.isDisplayed();
    }


}
