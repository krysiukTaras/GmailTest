package com.epam.bo;

import com.epam.control.Constants;
import com.epam.elements.MessageElement;
import com.epam.model.Message;
import com.epam.model.User;
import com.epam.pages.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.util.List;


//add in iss53 branch 
//second iss53 branch


public class GmailBO {

    private static final Logger LOG = LogManager.getLogger("Gmail");


    HomePage homePage;
    LoginPage loginPage;
    InboxPage inboxPage;
    ImportantPage importantPage;


    public GmailBO(WebDriver driver) {
        driver.get(Constants.URL);
        homePage = new HomePage(driver);
        LOG.info("open google.com");
    }

    public boolean openLoginPage(){
        if(homePage.isLoged()){
            homePage = homePage.emailLinkClick();
            loginPage = homePage.addAccountClick();
            try{
                loginPage = loginPage.differentAcountButtonClick();
            } catch (Exception e){
            }
            loginPage = loginPage.addAccountButtonClick();
            if (loginPage.isDisplayed()){
                LOG.info("open login page");
                return true;
            }
            LOG.info(" not open login page");
            return false;
        } else{
        loginPage = homePage.signInClick();
        if (loginPage.isDisplayed()){
            LOG.info("open login page");
            return true;
        }
        LOG.info(" not open login page");
        return false;
        }
    }

    public boolean login(User user){
        loginPage.setLoginField(user.getEmail());
        loginPage.setPasswordField(user.getPassword());
        homePage = loginPage.signInButtonClick();
        if(homePage.isLoged()){
            LOG.info("login user " + user);
            return true;
        }
        LOG.info("user not login" + user);
        return false;
    }


    public boolean openGmailInbox(){
        inboxPage = homePage.gmailLinkClick();
        if(inboxPage.isDisplayed()){
            LOG.info("open gmail inbox");
            return true;
        }
        LOG.info("not open gmail inbox");
        return false;
    }


    public Message findNotImportantMessage() {
        for (MessageElement message : inboxPage.getAllMessage()) {
            if (!message.isImportant()) {
                Message msg = new Message();
                msg.setMailFrom(message.getMailFrom());
                msg.setSubject(message.getMailSubject());
                LOG.info("find not important message");
                return msg;
            }

        }
        LOG.info("don't find not important message");
        return null;

    }

    public boolean teachGmail(Message msg){
        findMessageInList(inboxPage.getAllMessage(), msg).clickTeachGmail();
        LOG.info("click teach gmail");
        return findMessageInList(inboxPage.getAllMessage(), msg).isImportant();
    }

    public boolean openImportant(){
        importantPage = inboxPage.importantButtonClick();
        if(importantPage.isDisplayed()){
            LOG.info("open important folder");
            return true;
        }
        LOG.info("open important folder");
        return false;
    }

    public boolean verifyMessageIsInImportantList(Message msg){
        if(findMessageInList(importantPage.getAllMessages(),msg)!=null){
            LOG.info("message is in important list");
            return true;
        }
        LOG.info("message don't add to important list");
        return false;
    }

    public void teachGmailNotImportant(Message msg){
        findMessageInList(importantPage.getAllMessages(),msg).clickTeachGmail();
        LOG.info("teach gmail not important");
    }


    public boolean verifyMessageDisappears(Message msg){
        if(findMessageInList(importantPage.getAllMessages(),msg)==null){
            LOG.info("message disappears from list");
            return true;
        }
        LOG.info("message not disappears from list");
        return false;
    }


    private MessageElement findMessageInList( List<MessageElement> messageList, Message message){
        for (MessageElement msg : messageList) {
            if((msg.getMailFrom().equals(message.getMailFrom())) &&( msg.getMailSubject().equals(message.getSubject()))  ){
                return msg;
            }
        }
        return null;
    }

}
