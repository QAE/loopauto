package fw.basic.helpers;

import fw.basic.ApplicationManager;
import fw.basic.data.NavigationDataProviders;
import org.slf4j.LoggerFactory;
import pages.login.pages.LoginPage;

import java.net.MalformedURLException;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 19.01.13
 * Time: 21:45
 * To change this template use File | Settings | File Templates.
 */
/**
 * Basic navigation on LiveTell
 **/
public class NavigationHelper extends BaseHelper implements NavigationDataProviders {

    private static String serverSite;
    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(NavigationHelper.class);

    public NavigationHelper(ApplicationManager manager) throws MalformedURLException {
        super(manager);
    }

    protected String getBaseUrl() {
        serverSite = ApplicationManager.getServerSite();
        return serverSite;
    }

    public LoginPage goToMainPage() throws MalformedURLException {
        if (getCurrentUrl().equals(getBaseUrl())) {
            LOG.info("Main is a current page");
            reporterLog("Main is a current page");
            manager.getMainPageHelper().checkMainPage();
            return new LoginPage(ApplicationManager.getInstance());
        } else {
            openUrl(getBaseUrl());
            LOG.info("Go to Main page");
            reporterLog("Go to Main page");
            manager.getMainPageHelper().checkMainPage();
            return new LoginPage(ApplicationManager.getInstance());
        }
    }

    public LoginPage goToLoginPage() throws MalformedURLException {
        if (getCurrentUrl().equals(getBaseUrl() + LOGIN_PAGE)) {
            if (getBrowser().equals(BROWSER_SAFARI)) {
                openUrl(getBaseUrl() + LOGIN_PAGE);
            }
            LOG.info("Login is a current page");
            reporterLog("Login is a current page");
           // manager.getLoginHelper().checkLoginPage();
            return new LoginPage(ApplicationManager.getInstance());
        } else {
            openUrl(getBaseUrl() + LOGIN_PAGE);
            LOG.info("Go to Login page");
            reporterLog("Go to Login page");
            //manager.getLoginHelper().checkLoginPage();
            return new LoginPage(ApplicationManager.getInstance());
        }
    }

}
