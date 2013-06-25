package fw.basic.helpers;


import com.opera.core.systems.OperaDriver;
import fw.basic.ApplicationManager;
import fw.basic.data.GetTestDataProperties;
import fw.basic.data.BaseDataProvider;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;


/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 19.01.13
 * Time: 21:25
 * To change this template use File | Settings | File Templates.
 */

/**
 * Class for init and working with WebDriver
 **/
public class WebDriverHelper implements BaseDataProvider {

    private static WebDriver driver;
    private StringBuffer verificationErrors = new StringBuffer();
    private final ApplicationManager manager;

    /**Init WebDriver*/
    public WebDriverHelper(ApplicationManager manager) throws MalformedURLException {
        if (ApplicationManager.getBrowserType() != null
                && ApplicationManager.getBrowserType().equals("firefox")
                && ApplicationManager.getSaucelabsLogin() == null
                && ApplicationManager.getSaucelabsApiKey() == null) {
            initLocalFireFox();
        } else if (ApplicationManager.getBrowserType() != null
                && ApplicationManager.getBrowserType().equals("firefox")
                && ApplicationManager.getSaucelabsApiKey() != null
                && ApplicationManager.getSaucelabsApiKey()!= null) {
            initCloudFireFox();
        } else if (ApplicationManager.getBrowserType() != null
                && ApplicationManager.getBrowserType().equals("firefoxProfile")
                && ApplicationManager.getSaucelabsLogin() == null
                && ApplicationManager.getSaucelabsApiKey() == null) {
            initLocalFireFoxWithSomeProfile();
        } else if (ApplicationManager.getBrowserType() != null
                && ApplicationManager.getBrowserType().equals("firefoxPC")
                && ApplicationManager.getSaucelabsApiKey() == null
                && ApplicationManager.getSaucelabsApiKey() == null) {
            initLocalFireFoxFromPC();
        } else if (ApplicationManager.getBrowserType() != null
                && ApplicationManager.getBrowserType().equals("chrome")
                && ApplicationManager.getSaucelabsLogin() == null
                && ApplicationManager.getSaucelabsApiKey() == null) {
            initLocalChrome();
        } else if (ApplicationManager.getBrowserType() != null
                && ApplicationManager.getBrowserType().equals("chrome")
                && ApplicationManager.getSaucelabsApiKey() != null
                && ApplicationManager.getSaucelabsApiKey() != null) {
            initCloudChrome();
        } else if (ApplicationManager.getBrowserType() != null
                && ApplicationManager.getBrowserType().equals("chromeOptional")
                && ApplicationManager.getSaucelabsApiKey() == null
                && ApplicationManager.getSaucelabsApiKey() == null) {
            initLocalChromeWithOptions();
        } else if (ApplicationManager.getBrowserType() != null
                && ApplicationManager.getBrowserType().equals("ie")
                && ApplicationManager.getSaucelabsLogin() == null
                && ApplicationManager.getSaucelabsApiKey() == null) {
            initLocalInternetExplorer();
        } else if (ApplicationManager.getBrowserType() != null
                && ApplicationManager.getBrowserType().equals("ie")
                && ApplicationManager.getSaucelabsApiKey() != null
                && ApplicationManager.getSaucelabsApiKey() != null){
            initCloudInternetExplorer();
        } else if (ApplicationManager.getBrowserType() != null
                && ApplicationManager.getBrowserType().equals("phantom")
                && ApplicationManager.getSaucelabsLogin() == null
                && ApplicationManager.getSaucelabsApiKey() == null) {
            initPhantomJsDriver();
        } else if (ApplicationManager.getBrowserType() != null
                && ApplicationManager.getBrowserType().equals("opera")
                && ApplicationManager.getSaucelabsLogin() == null
                && ApplicationManager.getSaucelabsApiKey() == null) {
            initOperaDriver();
        } else if (ApplicationManager.getBrowserType() != null
                && ApplicationManager.getBrowserType().equals("opera")
                && ApplicationManager.getSaucelabsApiKey() != null
                && ApplicationManager.getSaucelabsApiKey()!= null) {
            initCloudOperaDriver();
        } else if (ApplicationManager.getBrowserType() != null
                && ApplicationManager.getBrowserType().equals("safari")
                && ApplicationManager.getSaucelabsLogin() == null
                && ApplicationManager.getSaucelabsApiKey() == null) {
            initSafariDriver();
        } else if (ApplicationManager.getBrowserType() != null
                && ApplicationManager.getBrowserType().equals("safari")
                && ApplicationManager.getSaucelabsApiKey() != null
                && ApplicationManager.getSaucelabsApiKey()!= null) {
            initCloudSafariDriver();
        }
        this.manager = manager;
    }

    /** Close existing WebDriver*/
    public void stop() {
        driver.manage().deleteAllCookies();
        driver.quit();
        String verificationErrorsString = verificationErrors.toString();
        if(!"".equals(verificationErrorsString)){
            fail(verificationErrorsString);
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    private void initLocalFireFox() {
        DesiredCapabilities capabilFireFox = DesiredCapabilities.firefox();
        driver = new FirefoxDriver(capabilFireFox);
        //((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().getSize();
        driver.manage().window().maximize();
        ApplicationManager.getInstance().setBrowserInfo(getBrowserInfo());
        driver.get(ApplicationManager.getServerSite());
    }

    private void initCloudFireFox() throws MalformedURLException {
        DesiredCapabilities capabilFireFox = DesiredCapabilities.firefox();
        capabilFireFox.setCapability("selenium-version", GetTestDataProperties.getDataProperty(SOUCELABS_SELENIUM_VERSION));
        capabilFireFox.setCapability("platform", GetTestDataProperties.getDataProperty(SOUCELABS_OS_WINDOWS7_PLATFORM));
        capabilFireFox.setCapability("version", GetTestDataProperties.getDataProperty(SOUCELABS_FIREFOXE_VERSION));
        capabilFireFox.setCapability("name", "Firefox " +
                GetTestDataProperties.getDataProperty(SOUCELABS_FIREFOXE_VERSION) + "tests");
        driver = new RemoteWebDriver(
                new URL("http://"+ ApplicationManager.getSaucelabsLogin() +":"+ ApplicationManager.getSaucelabsApiKey()
                        + "@ondemand.saucelabs.com:80/wd/hub"),capabilFireFox);
        ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().getSize();
        driver.manage().window().maximize();
        ApplicationManager.getInstance().setBrowserInfo(getBrowserInfo());
        driver.get(ApplicationManager.getServerSite());
    }

    private void initLocalFireFoxWithSomeProfile() {
        File profileDir = new File(GetTestDataProperties.getDataProperty(FireFoxProfileFromComputer));
        FirefoxProfile profile = new FirefoxProfile(profileDir);
        driver = new FirefoxDriver(profile);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().getSize();
        driver.manage().window().maximize();
        ApplicationManager.getInstance().setBrowserInfo(getBrowserInfo());
        driver.get(ApplicationManager.getServerSite());
    }

    private void initLocalFireFoxFromPC() {
        FirefoxBinary firefoxBinary = new FirefoxBinary(new File(GetTestDataProperties.getDataProperty(FireFoxLocationFromPC)));
        FirefoxProfile profile = new FirefoxProfile();
        driver = new FirefoxDriver(firefoxBinary, profile);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().getSize();
        driver.manage().window().maximize();
        ApplicationManager.getInstance().setBrowserInfo(getBrowserInfo());
        driver.get(ApplicationManager.getServerSite());
    }

    private void initLocalChrome() {
        DesiredCapabilities capabilChrome = DesiredCapabilities.chrome();
        driver =new ChromeDriver(capabilChrome);
        //((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().getSize();
        driver.manage().window().maximize();
        ApplicationManager.getInstance().setBrowserInfo(getBrowserInfo());
        driver.get(ApplicationManager.getServerSite());
    }

    private void initLocalChromeWithOptions() {
        ChromeOptions options = new ChromeOptions();
        options.setBinary(new File(GetTestDataProperties.getDataProperty(ChromeLocationFromPC)));
        driver = new ChromeDriver(options);
        //((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().getSize();
        driver.manage().window().maximize();
        ApplicationManager.getInstance().setBrowserInfo(getBrowserInfo());
        driver.get(ApplicationManager.getServerSite());
    }

    private void initCloudChrome() throws MalformedURLException {
        DesiredCapabilities capabilChrome = DesiredCapabilities.chrome();
        capabilChrome.setCapability("nativeEvents", true);
        capabilChrome.setCapability("selenium-version", GetTestDataProperties.getDataProperty(SOUCELABS_SELENIUM_VERSION));
        capabilChrome.setCapability("platform", GetTestDataProperties.getDataProperty(SOUCELABS_OS_WINDOWS7_PLATFORM));
        capabilChrome.setCapability("name", "Chrome tests");
        driver = new RemoteWebDriver(
                new URL("http://"+ ApplicationManager.getSaucelabsLogin() +":"+ ApplicationManager.getSaucelabsApiKey()
                        + "@ondemand.saucelabs.com:80/wd/hub"), capabilChrome);
        ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        ApplicationManager.getInstance().setBrowserInfo(getBrowserInfo());
        driver.get(ApplicationManager.getServerSite());
    }

    private void initLocalInternetExplorer() {
        DesiredCapabilities capabilIe = DesiredCapabilities.internetExplorer();
        driver = new InternetExplorerDriver(capabilIe);
        //((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        ApplicationManager.getInstance().setBrowserInfo(getBrowserInfo());
        driver.get(ApplicationManager.getServerSite());
    }

    private void initCloudInternetExplorer() throws MalformedURLException {
        DesiredCapabilities capabilIe = DesiredCapabilities.internetExplorer();
        capabilIe.setCapability("nativeEvents", true);
        capabilIe.setCapability("selenium-version", GetTestDataProperties.getDataProperty(SOUCELABS_SELENIUM_VERSION));
        capabilIe.setCapability("platform", GetTestDataProperties.getDataProperty(SOUCELABS_OS_WINDOWS7_PLATFORM));
        capabilIe.setCapability("version", GetTestDataProperties.getDataProperty(SOUCELABS_IE_VERSION));
        capabilIe.setCapability("name", "Internet Explorer " +
                GetTestDataProperties.getDataProperty(SOUCELABS_IE_VERSION) + "tests");
        driver = new RemoteWebDriver(
                new URL("http://"+ ApplicationManager.getSaucelabsLogin() +":"+ ApplicationManager.getSaucelabsApiKey()
                        + "@ondemand.saucelabs.com:80/wd/hub"), capabilIe);
        ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        ApplicationManager.getInstance().setBrowserInfo(getBrowserInfo());
        driver.get(ApplicationManager.getServerSite());
    }

    private void initPhantomJsDriver() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("phantomjs");
        driver = new RemoteWebDriver(new URL("http://127.0.0.1:4444/"),capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        ApplicationManager.getInstance().setBrowserInfo(getBrowserInfo());
        driver.get(ApplicationManager.getServerSite());
    }


    private void initOperaDriver() {
        DesiredCapabilities capabilities = DesiredCapabilities.opera();
        driver = new OperaDriver(capabilities);
        //((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().getSize();
        driver.manage().window().maximize();
        ApplicationManager.getInstance().setBrowserInfo(getBrowserInfo());
        driver.get(ApplicationManager.getServerSite());
    }

    private void initCloudOperaDriver() throws MalformedURLException {
        DesiredCapabilities capabilOpera = DesiredCapabilities.opera();
        capabilOpera.setCapability("nativeEvents", true);
        capabilOpera.setCapability("selenium-version", GetTestDataProperties.getDataProperty(SOUCELABS_SELENIUM_VERSION));
        capabilOpera.setCapability("platform", GetTestDataProperties.getDataProperty(SOUCELABS_OS_WINDOWS7_PLATFORM));
        capabilOpera.setCapability("version", GetTestDataProperties.getDataProperty(SOUCELABS_OPERA_VERSION));
        capabilOpera.setCapability("name", "Opera " +
                GetTestDataProperties.getDataProperty(SOUCELABS_OPERA_VERSION) + "tests");
        driver = new RemoteWebDriver(
                new URL("http://"+ ApplicationManager.getSaucelabsLogin() +":"+ ApplicationManager.getSaucelabsApiKey()
                        + "@ondemand.saucelabs.com:80/wd/hub"), capabilOpera);
        ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        ApplicationManager.getInstance().setBrowserInfo(getBrowserInfo());
        driver.get(ApplicationManager.getServerSite());
    }

    private void initSafariDriver() throws MalformedURLException {
        driver = new SafariDriver();
        //((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().getSize();
        driver.manage().window().maximize();
        ApplicationManager.getInstance().setBrowserInfo(getBrowserInfo());
        driver.get(ApplicationManager.getServerSite());
    }

    private void initCloudSafariDriver() throws MalformedURLException {
        DesiredCapabilities capabilsafari = DesiredCapabilities.safari();
        capabilsafari.setCapability("nativeEvents", true);
        capabilsafari.setCapability("selenium-version", GetTestDataProperties.getDataProperty(SOUCELABS_SELENIUM_VERSION));
        capabilsafari.setCapability("platform", GetTestDataProperties.getDataProperty(SOUCELABS_MACOS_PLATFORM));
        capabilsafari.setCapability("version", GetTestDataProperties.getDataProperty(SOUCELABS_SAFARI_VERSION));
        capabilsafari.setCapability("name", "Safari " +
                GetTestDataProperties.getDataProperty(SOUCELABS_SAFARI_VERSION) + "tests");
        driver = new RemoteWebDriver(
                 new URL("http://"+ ApplicationManager.getSaucelabsLogin() +":"+ ApplicationManager.getSaucelabsApiKey()
                        + "@ondemand.saucelabs.com:80/wd/hub"), capabilsafari);
        ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        ApplicationManager.getInstance().setBrowserInfo(getBrowserInfo());
        driver.get(ApplicationManager.getServerSite());
    }

    private Object dateToDay() {
        Date today;
        String resultDate;
        SimpleDateFormat formatter;

        formatter = new SimpleDateFormat("EEE-d-MMM-yyyy H-mm");
        today = new Date();
        resultDate = formatter.format(today);
        return resultDate;
    }

    /** Get screenshot*/
    public void getScreenshot(ITestResult result) throws IOException, ParseException {
        takeScreenshot(result);
    }

    private void takeScreenshot(ITestResult result) throws IOException, ParseException {
       if (! result.isSuccess()) {
           File screenshot = new File("target\\surefire-reports\\" + result.getMethod().getMethodName() + "-"+ dateToDay() + ".png");
           screenshot.delete();
           File screenshotTempFile = ((TakesScreenshot) driver)
                   .getScreenshotAs(OutputType.FILE);
           FileUtils.copyFile(screenshotTempFile, screenshot);
           Reporter.log("<a href=\"../" + screenshot.getName() + "\">"
                   + result.getMethod().getMethodName() + " screenshot</a><br/>");
       }
    }

    /** Get information about current browser*/
    private String getBrowserInfo() {
        return ((JavascriptExecutor) driver).executeScript("var N= navigator.appName, ua= navigator.userAgent, tem;\n" +
                "    var M= ua.match(/(opera|chrome|safari|firefox|msie)\\/?\\s*(\\.?\\d+(\\.\\d+)*)/i);\n" +
                "    if(M && (tem= ua.match(/version\\/([\\.\\d]+)/i))!= null) M[2]= tem[1];\n" +
                "    M= M? [M[1], M[2]]: [N, navigator.appVersion, '-?'];\n" +
                "\n" +
                "    return M;").toString();
    }
}
