import com.epam.bo.GmailBO;
import com.epam.model.Message;
import com.epam.model.User;
import com.epam.pool.WebDriverPool;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class TestGmailBrowsers {

    WebDriver driver;
    @DataProvider(name = "data", parallel = false)
    public Object[][] createData() {
        Object[][] users={
                {"kolatest1@gmail.com","kolakola"},
        };
        return(users);
    }

    @Parameters("browser")
    @BeforeClass
    public void beforeClass(String browser){
        if(browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        }else if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("acceptSslCerts", "true");
            driver = new ChromeDriver(capabilities);
        }

    }

    @Test(dataProvider = "data", threadPoolSize = 4)
    public void testGmail(String email, String password){
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
    }


    @AfterClass
    public void afterClass(){
        driver.close();
    }

}
