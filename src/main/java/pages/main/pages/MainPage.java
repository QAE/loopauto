package pages.main.pages;

import fw.basic.ApplicationManager;
import fw.basic.helpers.BaseHelper;
import org.openqa.selenium.support.FindBy;
import pages.main.data.MainPageData;
import ru.yandex.qatools.htmlelements.element.Link;

import java.net.MalformedURLException;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 6/25/13
 * Time: 9:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainPage extends BaseHelper implements MainPageData {

    public MainPage(ApplicationManager manager) throws MalformedURLException {
        super(manager);
    }

    @FindBy(xpath = "//a[@href='http://loopme.biz/wiki/index.php?title=Main_Page']")
    private Link wikiLink;

    @FindBy(xpath = "//a[@href='http://dashboard.loopme.me/login/']")
    private Link loginLink;

    @FindBy(xpath = "//a[@href='http://dashboard.loopme.me/signup/']")
    private Link registerLink;

    @FindBy(xpath = "//a[@href='http://loopme.biz/company/contact-us/']")
    private Link contactUsLink;

    public Link getWikiLink() {
        return wikiLink;
    }

    public Link getLoginLink() {
        return loginLink;
    }

    public Link getRegisterLink() {
        return registerLink;
    }

    public Link getContactUsLink() {
        return contactUsLink;
    }
}
