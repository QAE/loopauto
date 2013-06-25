package fw.basic.helpers;

import fw.basic.ApplicationManager;
import org.openqa.selenium.support.FindBy;
import org.slf4j.LoggerFactory;
import pages.staticdata.PageTitle;
import ru.yandex.qatools.htmlelements.element.TextInput;

import java.net.MalformedURLException;


/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 19.01.13
 * Time: 21:53
 * To change this template use File | Settings | File Templates.
 */
/**
 * Parent class for general methods in all project. All main pages should extends this class!
 **/
public class BaseHelper extends BaseWebDriverHelperHelper {

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(BaseHelper.class);

    public BaseHelper(ApplicationManager manager) throws MalformedURLException {
        super(manager);
    }

    /**
     * PageFactory HTML elements for creating WebElement
     **/
   @FindBy(id = "")
   private TextInput loginField;

    protected void assertPageTitle(PageTitle expectedPageTitle) {
        String titleValue = expectedPageTitle.getTitleValue();
        String currentTitle = getPageTitle();
        assertThatStringValueEquals(titleValue, currentTitle, "Should be on page " + titleValue +
                " but I am actually on page " + currentTitle);
        LOG.info("Assert page title: " + currentTitle);
    }
}
