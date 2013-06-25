package pages.login.pages;

import fw.basic.ApplicationManager;
import fw.basic.helpers.BaseHelper;
import pages.login.data.LoginDataProviders;

import java.net.MalformedURLException;

/**
 * Created with IntelliJ IDEA.
 * User: alpa
 * Date: 16.01.13
 * Time: 16:10
 * To change this template use File | Settings | File Templates.
 */
public class LoginPage extends BaseHelper implements LoginDataProviders {

    public LoginPage(ApplicationManager manager) throws MalformedURLException {
        super(manager);
    }

}
