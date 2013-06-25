package pages.login;

import fw.basic.ApplicationManager;
import fw.basic.helpers.BaseHelper;
import pages.login.data.LoginDataProviders;
import pages.login.pages.LoginPage;

import java.net.MalformedURLException;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 19.01.13
 * Time: 21:33
 * To change this template use File | Settings | File Templates.
 */
public class LoginHelper extends BaseHelper implements LoginDataProviders {

    private LoginPage loginPage;

    public LoginHelper(ApplicationManager manager) throws MalformedURLException {
        super(manager);
    }

    private LoginPage getLoginPage() throws MalformedURLException {
        if (loginPage == null) {
            return new LoginPage(ApplicationManager.getInstance());
        }
        return loginPage;
    }

}
