package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.By;

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

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.verifyTextInSearchLine("Search…");
    }

    @Test
    public void testVerifyAndCancelSearch() {

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");

        assertTrue(
                "Found one or zero articles",
                driver.findElements(By.id("org.wikipedia:id/page_list_item_container")).size() > 1
        );

        searchPageObject.clickCancelSearch();

        assertEquals(
                "Articles are still on the page ",
                0,
                driver.findElements(By.id("org.wikipedia:id/page_list_item_container")).size()
        );
    }

    @Test
    public void testCheckingWordsInSearch() {

        String search_text = "Java";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_text);
        searchPageObject.checkCorrectnessOfSearchResults(search_text);
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

    @Test
    public void testVerifyFirstThreeSearchResults() {

        String search_text = "Java";

        String first_result_title = "Java";
        String first_result_description = "Island of Indonesia";
        String second_result_title = "JavaScript";
        String second_result_description = "Programming language";
        String third_result_title = "Java (programming language)";
        String third_result_description = "Object-oriented programming language";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_text);
        searchPageObject.waitForElementByTitleAndDescription(first_result_title, first_result_description);
        searchPageObject.waitForElementByTitleAndDescription(second_result_title, second_result_description);
        searchPageObject.waitForElementByTitleAndDescription(third_result_title, third_result_description);
    }
}
