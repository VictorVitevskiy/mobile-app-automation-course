package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearch() {

        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() {

        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testEntryFieldContainsText() {

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                10
        );

        mainPageObject.waitForElementPresent(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Cannot find entry field",
                10
        );

        mainPageObject.assertElementHasText(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Search…",
                "Cannot find expected text in entry field"
        );
    }

    @Test
    public void testVerifyAndCancelSearch() {

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                15
        );

        String search_text = "Java";
        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_text,
                "Cannot find 'Search…' input",
                15
        );

        mainPageObject.waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find search results",
                10
        );

        assertTrue(
                "Found one or zero articles",
                driver.findElements(By.id("org.wikipedia:id/page_list_item_container")).size() > 1
        );

        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                10
        );

        assertEquals(
                "Articles are still on the page ",
                0,
                driver.findElements(By.id("org.wikipedia:id/page_list_item_container")).size()
        );
    }

    @Test
    public void testCheckingWordsInSearch() {

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                10
        );

        String search_text = "Java";
        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_text,
                "Cannot find 'Search…' input",
                10
        );

        mainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/page_list_item_container"),
                "Cannot find search results",
                10
        );

        int articles_number = driver.findElements(By.id("org.wikipedia:id/page_list_item_container")).size();
        int number = 0;

        while (articles_number > number) {
            number++;
            WebElement element = mainPageObject.waitForElementPresent(
                    By.xpath("//android.widget.LinearLayout[" + number + "][@resource-id='org.wikipedia:id/page_list_item_container']//android.widget.TextView[1]"),
                    "Search element not found"
            );

            Assert.assertTrue(
                    "Search result does not contain the search word ",
                    element.getAttribute("text").contains(search_text));
        }
    }

    @Test
    public void testAmountOfNotEmptySearch() {

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        String search_line = "Linkin Park Discography";
        searchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = searchPageObject.getAmountOfFoundArticles();

        assertTrue(
                "We found too few results",
                amount_of_search_results > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch() {

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        String search_line = "ngdfgsdg";
        searchPageObject.typeSearchLine(search_line);
        searchPageObject.waitForEmptyResultsLabel();
        searchPageObject.assertThereIsNoResultOfSearch();
    }
}
