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
        articlePageObject.addArticleToMyListForTheFirstTime(folder_name);
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

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring(first_title);

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.addArticleToMyListForTheFirstTime(folder_name);
        articlePageObject.closeArticle();

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Python");
        searchPageObject.clickByArticleWithSubstring(second_title);

        articlePageObject.addArticleIntoExistingMyList(folder_name);
        articlePageObject.closeArticle();

        NavigationUI navigationUI = new NavigationUI(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
        myListsPageObject.openFolderByName(folder_name);
        myListsPageObject.swipeByArticleToDelete(first_title);
        myListsPageObject.waitForArticleToAppearByTitle(second_title);
        myListsPageObject.openArticleByTitle(second_title);

        WebElement title_element = articlePageObject.waitForTitleElement();

        assertEquals(
                "Article title do not match",
                second_title,
                title_element.getAttribute("text")
        );
    }
}
