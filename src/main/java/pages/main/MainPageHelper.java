package pages.main;

import fw.basic.ApplicationManager;
import fw.basic.helpers.BaseHelper;
import pages.main.pages.MainPage;
import pages.staticdata.PageTitle;

import java.net.MalformedURLException;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 6/25/13
 * Time: 9:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainPageHelper extends BaseHelper {

    private MainPage mainPage;

    public MainPageHelper(ApplicationManager manager) throws MalformedURLException {
        super(manager);
    }

    private MainPage getMainPage() throws MalformedURLException {
        if (mainPage==null) {
            return new MainPage(ApplicationManager.getInstance());
        }
        return mainPage;
    }

    public void checkMainPage() throws MalformedURLException {
        assertPageTitle(PageTitle.MAIN_PAGE);
        assertThatElementVisible(getMainPage().getWikiLink(), "Wiki link is not visible");
        assertThatElementVisible(getMainPage().getLoginLink(), "Login link is not visible");
        assertThatElementVisible(getMainPage().getRegisterLink(), "Register link is no visible");
        assertThatElementVisible(getMainPage().getContactUsLink(), "Contact Us link is not visible");
        reporterLog("Check main page");
    }
}
