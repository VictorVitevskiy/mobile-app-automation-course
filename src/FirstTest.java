import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

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
        capabilities.setCapability("orientation", "PORTRAIT");

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

    @Test
    public void testSwipeArticle() {

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                15
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Appium",
                "Cannot find search input",
                15
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Cannot find 'Appium' in search",
                30
        );

        WebElement title_element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                30
        );

        swipeUpToFindElement(
                By.xpath("//*[@text='View page in browser']"),
                "Cannot find the end of the article",
                20
        );

    }

    @Test
    public void saveFirstArticleToMyList() {

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
                "Cannot find 'Object-oriented programming language' input",
                30
        );

        waitForElementAndClick(
                By.id("More options"),
                "Cannot find button to open article options",
                10
        );

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Add to reading list')]"),
                "Cannot find option to add article to reading list",
                10
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                10
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of article folder",
                10
        );

        String folder_name = "Learning programming";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                folder_name,
                "Cannot put text into article folder input",
                10
        );

        waitForElementAndClick(
                By.id("android:id/button1"),
                "Cannot press OK button",
                10
        );

        waitForElementAndClick(
                By.id("Navigate up"),
                "Cannot close article, cannot find X button",
                10
        );

        waitForElementAndClick(
                By.id("My lists"),
                "Cannot find navigation button to My lists",
                10
        );

        waitForElementAndClick(
                By.id("My lists"),
                "Cannot find navigation button to My lists",
                10
        );

        waitForElementAndClick(
                By.xpath("//*[@text='" + folder_name + "']"),
                "Cannot find option to add article to reading list",
                10
        );

        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article"
        );

        waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article",
                10
        );
    }

    @Test
    public void testAmountOfNotEmptySearch() {

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                10
        );

        String search_line = "Linkin Park Discography";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_line,
                "Cannot find search input",
                10
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        waitForElementPresent(
                By.xpath(search_result_locator),
                "Cannot find anything by the request " + search_line,
                15
        );

        int amount_of_search_results = getAmountOfElements(
                By.xpath(search_result_locator)
        );

        Assert.assertTrue(
                "We found too few results",
                amount_of_search_results > 0
        );
    }
    @Test
    public void testAmountOfEmptySearch() {

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                10
        );

        String search_line = "ngdfgsdg";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_line,
                "Cannot find search input",
                10
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        String empty_result_label = "//*[@text='No results found']";

        waitForElementPresent(
                By.xpath(empty_result_label),
                "Cannot find empty result label by the request " + search_line,
                15
        );

        assertElementNotPresent(
                By.xpath(search_result_locator),
                "We've found some results by request " + search_line
        );
    }

    @Test
    public void testChangeScreenOrientationOnSearchResults() {

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                10
        );

        String search_line = "Java";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_line,
                "Cannot find search input",
                10
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic by searching " + search_line,
                30
        );

        String title_before_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String title_after_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );

        Assert.assertEquals(
                "article title have been change after screen rotation",
                title_before_rotation,
                title_after_rotation
        );

        driver.rotate(ScreenOrientation.PORTRAIT);

        String new_title_after_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );

        Assert.assertEquals(
                "article title have been change after screen rotation",
                title_before_rotation,
                new_title_after_rotation
        );
    }

    @Test
    public void testCheckSearchArticleInBackground() {

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                10
        );

        String search_line = "Java";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_line,
                "Cannot find search input",
                10
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic by searching " + search_line,
                10
        );

        driver.runAppInBackground(2);

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' article after returning from background",
                10
        );
    }

    @Test
    public void saveTwoArticlesToMyList() {

        String folder_name = "Learning programming";
        String first_title = "Java (programming language)";
        String second_title = "Python (programming language)";

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
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + first_title + "']"),
                "Cannot find '" + first_title + "' title",
                30
        );

        waitForElementAndClick(
                By.id("More options"),
                "Cannot find button to open article options",
                10
        );

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Add to reading list')]"),
                "Cannot find option to add article to reading list",
                10
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                10
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of article folder",
                10
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                folder_name,
                "Cannot put text into article folder input",
                10
        );

        waitForElementAndClick(
                By.id("android:id/button1"),
                "Cannot press OK button",
                10
        );

        waitForElementAndClick(
                By.id("Navigate up"),
                "Cannot close article, cannot find X button",
                10
        );

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                15
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Python",
                "Cannot find search input",
                15
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + second_title + "']"),
                "Cannot find '"+ second_title + "' title",
                30
        );

        waitForElementAndClick(
                By.id("More options"),
                "Cannot find button to open article options",
                10
        );

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Add to reading list')]"),
                "Cannot find option to add article to reading list",
                10
        );

        waitForElementAndClick(
                By.xpath("//*[@text='" + folder_name + "']"),
                "Cannot find option to add article to My lists",
                10
        );

        waitForElementAndClick(
                By.id("Navigate up"),
                "Cannot close article, cannot find X button",
                10
        );

        waitForElementAndClick(
                By.id("My lists"),
                "Cannot find navigation button to My lists",
                10
        );

        waitForElementAndClick(
                By.xpath("//*[@text='" + folder_name + "']"),
                "Cannot find option to open my list",
                10
        );

        swipeElementToLeft(
                By.xpath("//*[@text='" + first_title + "']"),
                "Cannot find saved article"
        );

        waitForElementNotPresent(
                By.xpath("//*[@text='" + first_title + "']"),
                "Cannot delete saved article",
                10
        );

        waitForElementPresent(
                By.xpath("//*[@text='" + second_title + "']"),
                "Cannot find saved article",
                10
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + second_title + "']"),
                "Cannot find '" + second_title + "' title",
                30
        );

        WebElement title_element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find saved article",
                10
        );

        Assert.assertEquals(
                "Article title do not match",
                second_title,
                title_element.getAttribute("text")
        );
    }

    @Test
    public void testOpenArticleAndCheckTitle() {

        String title = "Java (programming language)";

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
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + title + "']"),
                "Cannot find '" + title + "' title",
                30
        );

        assertElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title"
        );
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

    protected void swipeUp(int time_of_swipe){

        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();

        int x = size.width / 2;
        int start_y = (int)(size.height * 0.8);
        int end_y = (int)(size.height * 0.2);

        action
                .press(x, start_y)
                .waitAction(time_of_swipe)
                .moveTo(x, end_y)
                .release()
                .perform();
    }

    protected void swipeUpQuick() {
        swipeUp(200);
    }

    protected void swipeUpToFindElement(By by, String error_message, int max_swipes) {

        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {

            if (already_swiped > max_swipes) {
                waitForElementPresent(by, "cannot find element by swipe UP. \n" + error_message, 0);
                return;
            }
            swipeUpQuick();
            already_swiped++;
        }
    }

    protected void swipeElementToLeft(By by, String error_message) {

        WebElement element = waitForElementPresent(by, error_message);

        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(right_x, middle_y)
                .waitAction(300)
                .moveTo(left_x, middle_y)
                .release()
                .perform();
    }

    private int getAmountOfElements(By by) {

        List<?> elements = driver.findElements(by);
        return elements.size();
    }

    private void assertElementNotPresent(By by, String error_message) {

        int amount_of_elements = getAmountOfElements(by);

        if (amount_of_elements > 0) {
            String default_message = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeout_in_seconds) {

        WebElement element = waitForElementPresent(by, error_message, timeout_in_seconds);
        return element.getAttribute(attribute);
    }

    private void assertElementPresent(By by, String error_message) {

        if (driver.findElements(by).size() < 1) {
            String default_message = "An element '" + by.toString() + "' supposed to be present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }
}
