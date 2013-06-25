package com.loopme;

import org.testng.annotations.Test;
import java.net.MalformedURLException;

/**
 * Created with IntelliJ IDEA.
 * User: alpa
 * Date: 11/02/13
 * Time: 14:01
 * To change this template use File | Settings | File Templates.
 */
public class LoginTests extends TestBase {

    @Test (enabled = true)
    public void checkLinksOnMainPage() throws MalformedURLException {
        app.getNavigationHelper().goToMainPage();
    }

}