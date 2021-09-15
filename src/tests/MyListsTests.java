package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class MyListsTests extends CoreTestCase {

    @Test
    public void testSaveFirstArticleToMyList() {

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waitForTitleElement();
        String article_title = articlePageObject.getArticleTitle();
        String folder_name = "Learning programming";
        articlePageObject.addArticleToMyList(folder_name);
        articlePageObject.closeArticle();

        NavigationUI navigationUI = new NavigationUI(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
        myListsPageObject.openFolderByName(folder_name);
        myListsPageObject.swipeByArticleToDelete(article_title);
    }

    @Test
    public void testSaveTwoArticlesToMyList() {

        String folder_name = "Learning programming";
        String first_title = "Java (programming language)";
        String second_title = "Python (programming language)";

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                15
        );

        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find search input",
                15
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + first_title + "']"),
                "Cannot find '" + first_title + "' title",
                30
        );

        mainPageObject.waitForElementAndClick(
                By.id("More options"),
                "Cannot find button to open article options",
                10
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Add to reading list')]"),
                "Cannot find option to add article to reading list",
                10
        );

        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                10
        );

        mainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of article folder",
                10
        );

        mainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                folder_name,
                "Cannot put text into article folder input",
                10
        );

        mainPageObject.waitForElementAndClick(
                By.id("android:id/button1"),
                "Cannot press OK button",
                10
        );

        mainPageObject.waitForElementAndClick(
                By.id("Navigate up"),
                "Cannot close article, cannot find X button",
                10
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                15
        );

        mainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Python",
                "Cannot find search input",
                15
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + second_title + "']"),
                "Cannot find '"+ second_title + "' title",
                30
        );

        mainPageObject.waitForElementAndClick(
                By.id("More options"),
                "Cannot find button to open article options",
                10
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Add to reading list')]"),
                "Cannot find option to add article to reading list",
                10
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + folder_name + "']"),
                "Cannot find option to add article to My lists",
                10
        );

        mainPageObject.waitForElementAndClick(
                By.id("Navigate up"),
                "Cannot close article, cannot find X button",
                10
        );

        mainPageObject.waitForElementAndClick(
                By.id("My lists"),
                "Cannot find navigation button to My lists",
                10
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + folder_name + "']"),
                "Cannot find option to open my list",
                10
        );

        mainPageObject.swipeElementToLeft(
                By.xpath("//*[@text='" + first_title + "']"),
                "Cannot find saved article"
        );

        mainPageObject.waitForElementNotPresent(
                By.xpath("//*[@text='" + first_title + "']"),
                "Cannot delete saved article",
                10
        );

        mainPageObject.waitForElementPresent(
                By.xpath("//*[@text='" + second_title + "']"),
                "Cannot find saved article",
                10
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + second_title + "']"),
                "Cannot find '" + second_title + "' title",
                30
        );

        WebElement title_element = mainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find saved article",
                10
        );

        assertEquals(
                "Article title do not match",
                second_title,
                title_element.getAttribute("text")
        );
    }
}
