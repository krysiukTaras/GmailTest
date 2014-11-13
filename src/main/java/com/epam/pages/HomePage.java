package com.epam.pages;


import com.epam.control.Constants;
import com.epam.decorator.Decorator;
import com.epam.elements.Button;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class HomePage extends BasePage {

    @FindBy(id = Constants.SIGN_IN_BUTTON_ID)
    public Button signInButton;

    @FindBy(linkText = Constants.GMAIL_LINK_TEXT)
    public Button gmail;

    @FindBy(css = "a.gb_A.gb_fa.gb_f")
    public Button emailLink;

    @FindBy(css = "a.gb_Wb.gb_V")
    public Button addAcount;

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new Decorator(driver), this);
    }

    public LoginPage signInClick(){
        signInButton.click();
        return new LoginPage(driver);
    }

    public InboxPage gmailLinkClick(){
        gmail.click();
        return new InboxPage(driver);
    }
    public boolean isLoged(){
        try{
            return emailLink.isDisplayed();
        }
        catch(NoSuchElementException e){
            return false;
        }
    }

    public HomePage emailLinkClick(){
        emailLink.click();
        return new HomePage(driver);
    }

    public LoginPage addAccountClick(){
        addAcount.click();
        return new LoginPage(driver);
    }
}
