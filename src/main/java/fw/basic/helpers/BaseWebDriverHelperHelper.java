package fw.basic.helpers;

import fw.basic.ApplicationManager;
import fw.basic.data.BaseDataProvider;
import fw.basic.data.WebDriverBaseHelperDataProviders;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;
import ru.yandex.qatools.htmlelements.element.*;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: alpa
 * Date: 31/05/13
 * Time: 17:57
 * To change this template use File | Settings | File Templates.
 */
/**
 * Parent class for general methods with WebDriver and HTML Elements.
 **/
public class BaseWebDriverHelperHelper implements WebDriverBaseHelperDataProviders, BaseDataProvider {

    protected final ApplicationManager manager;
    private WebDriver driver;
    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(BaseWebDriverHelperHelper.class);

    public BaseWebDriverHelperHelper(ApplicationManager manager) throws MalformedURLException {
        this.manager = manager;
        driver = manager.getWebDriverHelper().getDriver();
        PageFactory.initElements(new HtmlElementDecorator(driver), this);
    }

    /**
     * Check visibility of element
     * @param locator element locator
     **/
    protected boolean isElementVisible(By locator) {
        try {
            driver.findElement(locator);
            LOG.info("Element is visible");
            return true;
        } catch (NoSuchElementException e){
            LOG.info("Element is not visible" + e);
            return false;
        }
    }

    /**
     * Check visibility of element
     * @param textInput TextInput element
     **/
    protected boolean isElementVisible(TextInput textInput) {
        try {
            textInput.isDisplayed();
            LOG.info("Text field is visible");
            return true;
        } catch (NoSuchElementException e){
            LOG.info("Text field is not visible " + e);
            return false;
        }
    }

    /**
     * Check visibility of element
     * @param button Button element
     **/
    protected boolean isElementVisible(Button button) {
        try {
            button.isDisplayed();
            LOG.info("Button is visible");
            return true;
        } catch (NoSuchElementException e){
            LOG.info("Button is not visible" + e);
            return false;
        }
    }

    /**
     * Check visibility of element
     * @param link Link element
     **/
    protected boolean isElementVisible(Link link) {
        try {
            link.isDisplayed();
            LOG.info("Text field is visible");
            return true;
        } catch (NoSuchElementException e){
            LOG.info("Text field is not visible " + e);
            return false;
        }
    }

    /**
     * Check alert present
     **/
    protected boolean isAlertPresent() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 5);
            if (wait.until(ExpectedConditions.alertIsPresent()) == null) {
                LOG.info("Alert is not present");
                return false;
            } else {
                LOG.info("Alert is present");
                return true;
            }
        } catch (Exception e) {
            LOG.info("Alert is not present");
        }
        return false;
    }

    /**
     * Get WebDriver instance
     **/
    protected WebDriver getDriver() {
        return driver;
    }

    /**
     * Type data into element
     * @param locator element locator
     * @param text text
     **/
    protected void inputText(By locator, String text) {
        findElement(locator).clear();
        findElement(locator).sendKeys(text);
        LOG.info("Type info: " + text + " to element found " + locator);
    }

    /**
     * Find element
     * @param locator element locator
     **/
    protected WebElement findElement(By locator) {
        LOG.info("Find element: " + locator);
        return driver.findElement(locator);
    }

    /**
     * Find List of elements
     * @param locator element locator
     **/
    protected List<WebElement> findElements(By locator) {
        LOG.info("Find elements: " + locator);
        return driver.findElements(locator);
    }

    /**
     * Click on element
     * @param locator element locator
     **/
    protected void clickOnButton(By locator) {
        LOG.info("Click on element: " + locator);
        driver.findElement(locator).click();
    }

    /**
     * Click on element
     * @param element WebElement locator
     **/
    protected void clickOnButton(WebElement element) {
        LOG.info("Click on element: " + element);
        element.click();
    }

    /**
     * Open URL in browser
     * @param url URL
     **/
    protected void openUrl(String url) {
        driver.get(url);
        LOG.info("Open URL: " + url);
    }

    /**
     * Implicitly Wait
     * @param i number in second
     **/
    protected void implicitlyWait(int i) {
        driver.manage().timeouts().implicitlyWait(i, TimeUnit.SECONDS);
    }

    /**
     * Wait for complete ajax request
     **/
    protected ExpectedCondition<Boolean> ajaxComplete() {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                long counter = (Long) ((JavascriptExecutor) d).executeScript(
                        "return window.jQuery.active", new Object[]{});
                return counter == 0;
            }
        };
    }

    /**
     * Wait for element present
     * @param locator element locator
     **/
    protected void waitElementPresent(By locator) {
        new WebDriverWait(driver, 5).until(
                ExpectedConditions.presenceOfElementLocated((locator)));
        LOG.info("Wait for element present");
    }

    /**
     * Wait for element present
     * @param locator element locator
     **/
    protected void waitElementPresent(By locator, int seconds) {
        new WebDriverWait(driver, seconds).until(
                ExpectedConditions.presenceOfElementLocated((locator)));
        LOG.info("Wait for element present");
    }

    /**
     * Wait for element visibility
     * @param locator element locator
     **/
    protected void waitElementVisibility(By locator) {
        new WebDriverWait(driver, 5).until(
                ExpectedConditions.visibilityOfElementLocated((locator)));
        LOG.info("Wait for element present");
    }

    /**
     * Wait for element invisibility
     * @param locator element locator
     **/
    protected void waitElementInvisibility(By locator) {
        new WebDriverWait(driver, 5).until(
                ExpectedConditions.invisibilityOfElementLocated((locator)));
        LOG.info("Wait for element invisibility");
    }

    /**
     * Get current URL from browser
     **/
    protected String getCurrentUrl() {
        LOG.info("Get current url");
        return driver.getCurrentUrl();
    }

    /**
     * Refresh current pages
     **/
    public void refreshPage() {
        driver.navigate().refresh();
        LOG.info("Refresh page");
        reporterLog("Refresh pages");
    }

    /**
     * Get pages title
     **/
    protected String getPageTitle() {
        String title = driver.getTitle();
        LOG.info("Get title: " + title);
        return title;
    }

    /**
     * Set value for attribute
     * @param locator element locator
     * @param attribute name attribute
     * @param value value attribute
     **/
    public void setAttribute(By locator, String attribute, String value) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('" + attribute
                + "',arguments[1]);",
                findElement(locator),
                value);
        LOG.info("Set attribute: " + attribute + " Value: " + value);
    }

    /**
     * Wait for alert present
     **/
    private Alert waitForAlert(WebDriver driver, int seconds) {
        Wait<WebDriver> wait = new WebDriverWait(driver, seconds).ignoring(NullPointerException.class);
        Alert alert = wait.until(new ExpectedCondition<Alert>() {
            @Override
            public Alert apply(WebDriver driver) {
                Alert alert = driver.switchTo().alert();
                alert.getText();
                return alert;
            }
        });
        LOG.info("Wait for alert");
        return alert;
    }

    /**
     * Get alert for action
     **/
    protected Alert getAlert(){
        return waitForAlert(driver, 10);
    }

    /**
     * Get browser type for fix errors concerning cross-browsers
     **/
    public String getBrowser() {
        String browser = ApplicationManager.getBrowserType();
        LOG.info("Browser type: " + browser);
        return browser;
    }

    /**
     * Added log message in report
     * @param message text message
     **/
    public void reporterLog(String message) {
       Reporter.log(message + " </br>");
    }

    /**
     * Input data character by character
     **/
    public void waitElementDuringSendKeys(String el, WebElement element){
        String elLowerCase = el.toLowerCase().trim();
        for(int i = 0; i < el.length(); i++){
            element.sendKeys(Character.toString(elLowerCase.charAt(i)));
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        element.sendKeys(" ".trim());
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOG.info("Wait element during send keys");
    }

    /**
     * Get element text
     * @param locator element locator
     **/
    protected String getElementText(By locator) {
        String text = findElement(locator).getText().trim();
        LOG.info("Get element text: " + text);
        return text;
    }

    public void waitElementDuringSendKeys(String el, TextInput element){
        String elLowerCase = el.toLowerCase().trim();
        for(int i = 0; i < el.length(); i++){
            element.sendKeys(Character.toString(elLowerCase.charAt(i)));
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        element.sendKeys(" ".trim());
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOG.info("Wait element during send keys");
    }

    /**
     * Get element text
     * @param element element locator
     **/
    protected String getElementText(TextInput element) {
        String text = element.getText().trim();
        LOG.info("Get element text: " + text);
        return text;
    }

    /**
     * Get element text
     * @param locator element locator
     * @param attribute element attribute
     **/
    protected String getElementAttribute(By locator, String attribute) {
        LOG.info("Get element attribute: " + attribute);
        return findElement(locator).getAttribute(attribute).trim();
    }

    /**
     * Get element text
     * @param button element locator
     **/
    protected String getElementText(Button button) {
        String text = button.getWrappedElement().getText().trim();
        LOG.info("Get element text: " + text);
        return text;
    }

    /**
     * Get element text
     * @param checkBox element locator
     **/
    protected String getElementText(CheckBox checkBox) {
        String text = checkBox.getText().trim();
        LOG.info("Get element text: " + text);
        return text;
    }

    /**
     * Click on button
     * @param btn Button element
     **/
    protected void clickOnButton(Button btn) {
        btn.click();
        LOG.info("Click on button: " + btn.getName());
    }

    /**
     * Get TextInput Properties
     * @param element TextInput element
     **/
    protected void inputText(TextInput element, String text) {
        element.clear();
        element.sendKeys(text);
        LOG.info("Input text: " + text);
    }

    /**
     * Get TextInput Properties
     * @param element TextInput element
     **/
    protected TextInput getElementOptions(TextInput element) {
        LOG.info("Get element options");
        return element;
    }

    /**
     * Get Button properties
     * @param element Button element
     **/
    protected Button getElementOptions(Button element) {
        LOG.info("Get element options");
        return element;
    }

    /**
     * Get CheckBox properties
     * @param checkBox CheckBox element
     **/
    protected CheckBox getElementOptions(CheckBox checkBox) {
        LOG.info("Get element options");
        return checkBox;
    }

    /**
     * Get Select properties
     * @param select CheckBox element
     **/
    protected Select getElementOptions(Select select) {
        LOG.info("Get element options");
        return select;
    }

    /**
     * Get table options
     * @param table Table element
     **/
    protected Table getElementOptions(Table table) {
        LOG.info("Get element options");
        return table;
    }

    /**
     * Get link options
     * @param link Select element
     **/
    protected Link getElementOptions(Link link) {
        LOG.info("Get element options");
        return link;
    }

    /**
     * Get Form options
     * @param form Form element
     **/
    protected Form getElementOptions(Form form) {
        LOG.info("Get element options");
        return form;
    }

    /**
     * Check checkBox
     * @param checkBox CheckBox element
     **/
    protected void checkCheckbox(CheckBox checkBox) {
        checkBox.select();
        LOG.info("Mark checkbox");
    }

    /**
     * Uncheck checkBox
     * @param checkBox CheckBox element
     **/
    protected void unCheckbox(CheckBox checkBox) {
        checkBox.deselect();
        LOG.info("Unmark checkbox");
    }

    /**
     * Select by visible text options
     * @param select Select element
     **/
    protected void selectByVisibleText(Select select, String text) {
        select.selectByVisibleText(text);
        LOG.info("Select by visible text: " + text);
    }

    /**
     * Select by index options
     * @param select Select element
     **/
    protected void selectByIndex(Select select, int text) {
        select.selectByIndex(text);
        LOG.info("Select by index: " + text);
    }

    /**
     * Select by index options
     * @param select Select element
     **/
    protected void selectByValue(Select select, String text) {
        select.selectByValue(text);
        LOG.info("Select by value: " + text);
    }

    /**
     * Get link
     * @param link Select element
     **/
    protected void getLink(Link link) {
        link.getReference();
    }

    /**
     * Get link
     * @param link Select element
     **/
    protected String getLinkText(Link link) {
        String text = link.getText();
        LOG.info("Link text: " + text);
        return text;
    }

    /**
     * Fill form
     * @param form Form element
     * @param data data and object for input
     **/
    protected void fillForm(Form form, Map<String, Object> data) {
        form.fill(data);
        LOG.info("Fill form");
    }

    /**
     * Return selenium web element
     * @param element TextInput element
     **/
    protected WebElement getWrappedElement(TextInput element) {
        return element.getWrappedElement();
    }

    /**
     * Return selenium web element
     * @param element Button element
     **/
    protected WebElement getWrappedElement(Button element) {
        return element.getWrappedElement();
    }

    /**
     * Return selenium web element
     * @param element CheckBox element
     **/
    protected WebElement getWrappedElement(CheckBox element) {
        return element.getWrappedElement();
    }

    /**
     * Return selenium web element
     * @param element Select element
     **/
    protected WebElement getWrappedElement(Select element) {
        return element.getWrappedElement();
    }

    /**
     * Return selenium web element
     * @param table Table element
     **/
    protected WebElement getWrappedElement(Table table) {
        return table.getWrappedElement();
    }

    /**
     * Return selenium web element
     * @param link Table element
     **/
    protected WebElement getWrappedElement(Link link) {
        return link.getWrappedElement();
    }

    /**
     * Return selenium web element
     * @param form Form element
     **/
    protected WebElement getWrappedElement(Form form) {
        return form.getWrappedElement();
    }

    /**
     * Assert visibility of web element
     * @param textInput TextInput element
     * @param message String text message
     **/
    protected void assertThatElementVisible(TextInput textInput, String message) {
        assertTrue(isElementVisible(textInput), message);
        LOG.info("Assert that element visible");
    }

    /**
     * Assert visibility of web element
     * @param button Button element
     * @param message String text message
     **/
    protected void assertThatElementVisible(Button button, String message) {
        assertTrue(isElementVisible(button), message);
        LOG.info("Assert that element visible");
    }

    /**
     * Assert visibility of web element
     * @param locator Web element locator
     * @param message String text message
     **/
    protected void assertThatElementVisible(By locator, String message) {
        assertTrue(isElementVisible(locator), message);
        LOG.info("Assert that element visible");
    }

    /**
     * Assert visibility of web element
     * @param link TextInput element
     * @param message String text message
     **/
    protected void assertThatElementVisible(Link link, String message) {
        assertTrue(isElementVisible(link), message);
        LOG.info("Assert that element visible");
    }

    /**
     * Assert String value is equals
     * @param actualValue actual String value
     * @param expectedValue expected String value
     *  @param message String text message
     **/
    protected void assertThatStringValueEquals(String expectedValue, String actualValue, String message) {
        assertEquals(expectedValue, actualValue, message);
        LOG.info("Assert that string values equals: " + expectedValue + " = " + actualValue);
    }

    /**
     * Assert that String contains value
     * @param actualValue actual String value
     * @param expectedValue expected String value
     *  @param message String text message
     **/
    protected void assertThatStringContainsValue(String expectedValue, String actualValue, String message) {
        assertTrue(actualValue.contains(expectedValue), message);
        LOG.info("Assert that string contains value: " + expectedValue +" contains " + actualValue);
    }

    /**
     * Assert that String value not empty
     * @param expectedValue expected String value
     *  @param message String text message
     **/
    protected void assertThatStringValueNotEmpty(String expectedValue, String message) {
        assertFalse(expectedValue.isEmpty(), message);
        LOG.info("Assert that string values is not empty");

    }

    /**
     * Assert that int value equals
     * @param actualValue actual int value
     * @param expectedValue int String value
     *  @param message String text message
     **/
    protected void assertThatIntValueEquals(int expectedValue, int actualValue, String message) {
        assertTrue(expectedValue == actualValue, message);
        LOG.info("Assert that int values equals: " + expectedValue + " = " + actualValue);
    }

    /**
     * Assert that int value equals not equals 0
     * @param expectedValue int String value
     *  @param message String text message
     **/
    protected void assertThatIntValueNotEqualsNull(int expectedValue, String message) {
        assertTrue(expectedValue != 0, message);
        LOG.info("Assert that int value not equal null: " + expectedValue);
    }

    /** Return size of web element
     * @param xpathExpression xpath expression
     **/
    protected int getSize(String xpathExpression) {
        int size = driver.findElements(By.xpath(xpathExpression)).size();
        LOG.info("Get size: " + size);
        return size;
    }

    /**  Thread sleep
     * @param seconds input seconds
     **/
    protected void waitABit(int seconds) {
        int i = seconds * 1000;
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOG.info("Wait a bit: " + seconds);
    }

    /**Return current window handle*/
    public String getCurrentWindow() {
        String windowHandles = driver.getWindowHandle();
        LOG.info("Get current window handles: " + windowHandles);
        return windowHandles;
    }

    public void switchWindow() {
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }
        LOG.info("Switch window");
    }

    public void switchWindow(String winHandleBefore) {
        driver.switchTo().window(winHandleBefore);
        LOG.info("Switch window: " + winHandleBefore);
    }

    public void closeCurrentWindow() {
        driver.close();
        LOG.info("Close current window");
    }
}
