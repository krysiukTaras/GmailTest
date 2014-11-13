package com.epam.pages;


import com.epam.control.Constants;
import com.epam.decorator.Decorator;
import com.epam.elements.Button;
import com.epam.elements.TextBox;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class LoginPage extends BasePage {

    @FindBy(id = Constants.INPUT_EMAIL_FIELD_ID)
    private TextBox loginField;

    @FindBy(id = Constants.INPUT_PASSWORD_FIELD_ID)
    private TextBox passwordField;

    @FindBy(id = Constants.SIGN_IN_BUTTON_ON_LOGIN_ID)
    private Button signInButton;

    @FindBy(id = "account-chooser-link")
    private Button differentAcountButton;

    @FindBy(id = "account-chooser-add-account")
    private Button addAccountButton;

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new Decorator(driver), this);
    }

    public void setLoginField(String login){
        loginField.setText(login);
    }

    public void setPasswordField(String password) {
        passwordField.setText(password);
    }

    public HomePage signInButtonClick(){
        signInButton.click();
        return new HomePage(driver);
    }

    public boolean isDisplayed(){
        return loginField.isDisplayed();
    }

    public LoginPage differentAcountButtonClick(){
        differentAcountButton.click();
        return new LoginPage(driver);
    }

    public LoginPage addAccountButtonClick(){
        addAccountButton.click();
        return new LoginPage(driver);
    }
}
