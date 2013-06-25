package fw.basic;

import fw.Start;
import fw.basic.data.BaseDataProvider;
import fw.basic.data.GetTestDataProperties;
import fw.basic.helpers.NavigationHelper;
import fw.basic.helpers.WebDriverHelper;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import pages.login.LoginHelper;
import pages.main.MainPageHelper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 19.01.13
 * Time: 21:14
 * To change this template use File | Settings | File Templates.
 */
/**
 * Class for management of all existing helpers
 **/
public class ApplicationManager {

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(ApplicationManager.class);
    private WebDriverHelper webDriverHelper;
    private LoginHelper loginHelper;
    private NavigationHelper navigationHelper;
    private Start start;
    private MainPageHelper mainPageHelper;
    private static ApplicationManager singleton;
    private static String browserType;
    private static String serverSite;
    private static String saucelabsLogin;
    private static String saucelabsApiKey;
    private static String browserInfo;

    /**
     * Read properties from Maven command line
     **/
    public void setUpProperties (){
        String browser = "firefox";
        browserType =  System.getProperty("browser", browser).trim();
        LOG.info(getBrowserType());
        String site = GetTestDataProperties.getDataProperty(BaseDataProvider.LOOPME_BIZ);
        serverSite =  System.getProperty("site", site).trim();
        LOG.info(getServerSite());
        saucelabsLogin =  System.getProperty("sauceLogin")!= null
                ? System.getProperty("sauceLogin").trim() : null;
        saucelabsApiKey =  System.getProperty("sauceApiKey")!= null
                ? System.getProperty("sauceApiKey").trim() : null;
    }

    /**
     * Create instance of ApplicationManager
     **/
    public static ApplicationManager getInstance() {
        if (singleton == null){
            singleton = new ApplicationManager();
        }
        return singleton;
    }

    /**
     * Stop WebDriver and kill instance of ApplicationManager
     **/
    public void stop() {
        if (webDriverHelper != null) {
            webDriverHelper.stop();
        }
        if (singleton != null) {
            singleton = null;
        }
    }

    public  void getScreenshot(ITestResult result) throws IOException, ParseException {
        webDriverHelper.getScreenshot(result);
    }

    public WebDriverHelper getWebDriverHelper() throws MalformedURLException {
        if (webDriverHelper == null) {
            webDriverHelper = new WebDriverHelper(this);
        }
        return webDriverHelper;
    }
    public Start getStart() throws MalformedURLException {
        if (start == null) {
            start = new Start(this);
        }
        return start;
    }

    public LoginHelper getLoginHelper() throws MalformedURLException {
        if(loginHelper == null){
            loginHelper = new LoginHelper(this);
        }
        return loginHelper;
    }

    public NavigationHelper getNavigationHelper() throws MalformedURLException {
        if(navigationHelper == null){
            navigationHelper = new NavigationHelper(this);
        }
        return navigationHelper;
    }

    public MainPageHelper getMainPageHelper() throws MalformedURLException {
        if(mainPageHelper == null){
            mainPageHelper = new MainPageHelper(this);
        }
        return mainPageHelper;
    }

    public static String getBrowserType() {
        return browserType;
    }

    public static String getServerSite() {
        return serverSite;
    }

    public static String getSaucelabsLogin() {
        return saucelabsLogin;
    }

    public static String getSaucelabsApiKey() {
        return saucelabsApiKey;
    }

    public static String getBrowserInfo() {
        return browserInfo;
    }

    public static void setBrowserInfo(String browserInfo) {
        ApplicationManager.browserInfo = browserInfo;
    }
}
