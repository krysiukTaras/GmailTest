package com.epam.pages;


import com.epam.control.Constants;
import com.epam.decorator.Decorator;
import com.epam.elements.Button;
import com.epam.elements.MessageElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ImportantPage extends BasePage {

    @FindBy(xpath = Constants.IMPORTANT_TABLE_XPATH)
    private List<MessageElement> messages;

    @FindBy(css = Constants.BUTTON_INBOX)
    public Button inbox;

    public ImportantPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new Decorator(driver), this);
    }

    public List<MessageElement> getAllMessages(){
        return messages;
    }

    public boolean isDisplayed(){
        return inbox.isDisplayed();
    }


}
