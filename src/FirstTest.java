import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class FirstTest {

    private AppiumDriver<?> driver;

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "D:\\JavaProjects\\mobile-app-automation-course\\apps\\org.wikipedia.apk");

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void firstTest() {

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                15
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find search input",
                30
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by Java",
                30
        );
    }

    @Test
    public void testCancelSearch() {

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                15
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find search input",
                15
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                15
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                15
        );

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "X is still present on the page",
                15
        );
    }

    @Test
    public void testCompareArticleTitle() {

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                15
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find search input",
                15
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Search Wikipedia' input",
                30
        );

        WebElement title_element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                30
        );

        String article_title = title_element.getAttribute("text");

        Assert.assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                article_title
        );
    }

    @Test
    public void testEntryFieldContainsText() {

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                10
        );

        waitForElementPresent(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Cannot find entry field",
                10
        );

        assertElementHasText(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Search…",
                "Cannot find expected text in entry field"
        );
    }

    @Test
    public void testVerifyAndCancelSearch() {

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                15
        );

        String search_text = "Java";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_text,
                "Cannot find 'Search…' input",
                15
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find search results",
                10
        );

        Assert.assertTrue(
                "Found one or zero articles",
                driver.findElements(By.id("org.wikipedia:id/page_list_item_container")).size() > 1
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                10
        );

        Assert.assertEquals(
                "Articles are still on the page ",
                0,
                driver.findElements(By.id("org.wikipedia:id/page_list_item_container")).size()
        );
    }

    @Test
    public void testCheckingWordsInSearch() {

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                10
        );

        String search_text = "Java";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_text,
                "Cannot find 'Search…' input",
                10
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/page_list_item_container"),
                "Cannot find search results",
                10
        );

        int articles_number = driver.findElements(By.id("org.wikipedia:id/page_list_item_container")).size();
        int number = 0;

        while (articles_number > number) {
            number++;
            WebElement element = waitForElementPresent(
                    By.xpath("//android.widget.LinearLayout[" + number + "][@resource-id='org.wikipedia:id/page_list_item_container']//android.widget.TextView[1]"),
                    "Search element not found"
            );

            Assert.assertTrue(
                    "Search result does not contain the search word ",
                    element.getAttribute("text").contains(search_text));
        }
    }

    private WebElement waitForElementPresent(By by, String error_message, long timeout_in_seconds) {

        WebDriverWait wait = new WebDriverWait(driver, timeout_in_seconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String error_message) {

        return waitForElementPresent(by, error_message, 15);
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeout_in_seconds) {

        WebElement element = waitForElementPresent(by, error_message, timeout_in_seconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeout_in_seconds) {

        WebElement element = waitForElementPresent(by, error_message, timeout_in_seconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeout_in_seconds) {

        WebDriverWait wait = new WebDriverWait(driver, timeout_in_seconds);
        wait.withMessage(error_message);
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeout_in_seconds) {

        WebElement element = waitForElementPresent(by, error_message, timeout_in_seconds);
        element.clear();
        return element;
    }

    private void assertElementHasText(By by, String expected_text, String error_message) {

        WebElement element = waitForElementPresent(by, error_message);
        Assert.assertTrue(
                error_message,
                element.getAttribute("text").contains(expected_text)
        );
    }
}
