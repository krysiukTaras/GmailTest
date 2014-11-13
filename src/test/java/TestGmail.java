import com.epam.bo.GmailBO;
import com.epam.model.Message;
import com.epam.model.User;
import com.epam.pool.WebDriverPool;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;


public class TestGmail {

    WebDriverPool pool;


    @DataProvider(name = "data", parallel = true)
    public Object[][] createData() {
        Object[][] users={
                {"sdrivertestmail@gmail.com","testtest123"},
                {"dzzyurak@gmail.com","Qwe123Qwe123"},
                {"mikus.misha@gmail.com","TestPassword1111"},
                {"sdrivertestmail1@gmail.com","testtest123"},
                {"sdrivertestmail2@gmail.com","testtest123"},
                {"sdrivertestmail3@gmail.com","testtest123"},
                {"kolatest1@gmail.com","kolakola"},
        };
        return(users);
    }


    @BeforeClass
    public void beforeClass(){
        pool  = new WebDriverPool(4);
    }


    @Test(dataProvider = "data", threadPoolSize = 4)
    public void testGmail(String email, String password){

        WebDriver driver = pool.getDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        GmailBO gmail =  new GmailBO(driver);
        Message msg;

        assertTrue(gmail.openLoginPage(), "Don't open login page");

        assertTrue(gmail.login(new User(email, password)), "user not login");

        assertTrue(gmail.openGmailInbox(), "Don't open gmail inbox");

        msg = gmail.findNotImportantMessage();
        assertNotNull(msg, "don't find not important message");

        assertTrue(gmail.teachGmail(msg),"don't teach" );

        assertTrue(gmail.openImportant(), "important folder not open");

        assertTrue(gmail.verifyMessageIsInImportantList(msg), "message not in important");

        gmail.teachGmailNotImportant(msg);
        assertTrue(gmail.verifyMessageDisappears(msg), "message not disappears");

        pool.releaseDriver(driver);
    }


    @AfterClass
    public void afterClass(){
        pool.closeAll();
    }

}
