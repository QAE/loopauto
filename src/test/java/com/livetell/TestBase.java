package com.livetell;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import fw.basic.ApplicationManager;
import fw.basic.data.BaseDataProvider;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.*;
import fw.basic.data.TestData;
import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;

import java.net.MalformedURLException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 19.01.13
 * Time: 18:06
 * To change this template use File | Settings | File Templates.
 */
/**
 * Parent class for test classes
 **/
public class TestBase implements TestData, BaseDataProvider {

    /**
     * Getting handles and sent into Lilith
     **/
    static {
        System.setProperty(APACH_LOG, JDK_14_LOGGER);
        Logger.getLogger("").setLevel(Level.ALL);
        for (Handler h: Logger.getLogger("").getHandlers()) {
            Logger.getLogger("").removeHandler(h);
        }
        SLF4JBridgeHandler.install();
        SysOutOverSLF4J.sendSystemOutAndErrToSLF4J();
        StatusPrinter.print((LoggerContext) LoggerFactory.getILoggerFactory());
    }

    protected ApplicationManager app;
    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(TestBase.class);

    /**
     * Set up before test suite
     **/
    @BeforeSuite
    public void setUpBeforeSuite (ITestContext iTestContext) {
        ApplicationManager applicationManager = new ApplicationManager();
        applicationManager.setUpProperties();
        iTestContext.setAttribute("application", app);
        LOG.info("Read system properties before suite");
    }

    /**
     * Set up before test
     **/
    @BeforeTest
    public void deleteCookies() throws MalformedURLException {
        app = ApplicationManager.getInstance();
        app.getInstance().getWebDriverHelper().getDriver().manage().deleteAllCookies();
        app.getInstance().getWebDriverHelper().getDriver().navigate().refresh();
        LOG.info("Complete delete cookies before test");
        Reporter.log("<b style=\"color:#87CEFA\"> WebDriver version: " + System.getProperty("webdriver.driver") + "</b><br/>");
        Reporter.log("<b style=\"color:#1E90FF\"> Browser: "+ app.getBrowserInfo() + "</b><br/>");
        Reporter.log("<b style=\"color:#1E90FF\"> Site: " + app.getServerSite() + "</b><br/>");
        if (app.getSaucelabsLogin() != null) {
            Reporter.log("<b style=\"color:#1E90FF\"> SouceLabs Login: " + app.getSaucelabsLogin() + "</b><br/>");
        }
        LOG.info("Complete set up before test");
    }

    /**
     *Set up after test suite
     * */
    @AfterSuite
    public void tearDown() {
        ApplicationManager.getInstance().stop();
        LOG.info("Stopped tests");
    }
}
