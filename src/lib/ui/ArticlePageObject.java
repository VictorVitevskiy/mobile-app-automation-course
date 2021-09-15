package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject{

    public static final String
            TITLE = "org.wikipedia:id/view_page_title_text",
            FOOTER_ELEMENT = "//*[@text='View page in browser']",
            OPTIONS_BUTTON = "More options",
            OPTIONS_ADD_TO_MY_LIST_BUTTON = "//*[contains(@text,'Add to reading list')]",
            ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
            MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
            MY_LIST_OK_BUTTON = "android:id/button1",
            CLOSE_ARTICLE_BUTTON = "Navigate up",
            FOLDER_BY_NAME_TPL = "//*[@text='{FOLDER_NAME}']";

    public ArticlePageObject(AppiumDriver<?> driver) {
        super(driver);
    }

    public WebElement waitForTitleElement() {

        return this.waitForElementPresent(
                By.id(TITLE),
                "Cannot find article title on page",
                15
        );
    }

    public String getArticleTitle() {

        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("text");
    }

    public void checkArticleTitlePresent() {

        this.assertElementPresent(
                By.id(TITLE),
                "Cannot find article title"
        );
    }

    public void swipeToFooter() {

        this.swipeUpToFindElement(
                By.xpath(FOOTER_ELEMENT),
                "cannot find the end of article",
                20
        );
    }

    public void addArticleToMyListForTheFirstTime(String name_of_folder) {

        this.waitForElementAndClick(
                By.id(OPTIONS_BUTTON),
                "Cannot find button to open article options",
                10
        );

        this.waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "Cannot find option to add article to reading list",
                10
        );

        this.waitForElementAndClick(
                By.id(ADD_TO_MY_LIST_OVERLAY),
                "Cannot find 'Got it' tip overlay",
                10
        );

        this.waitForElementAndClear(
                By.id(MY_LIST_NAME_INPUT),
                "Cannot find input to set name of article folder",
                10
        );

        this.waitForElementAndSendKeys(
                By.id(MY_LIST_NAME_INPUT),
                name_of_folder,
                "Cannot put text into article folder input",
                10
        );

        this.waitForElementAndClick(
                By.id(MY_LIST_OK_BUTTON),
                "Cannot press OK button",
                10
        );
    }
    public void addArticleIntoExistingMyList(String name_of_folder) {

        this.waitForElementAndClick(
                By.id(OPTIONS_BUTTON),
                "Cannot find button to open article options",
                10
        );

        this.waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "Cannot find option to add article to reading list",
                10
        );

        String folder_xpath = getFolderByName(name_of_folder);
        this.waitForElementAndClick(
                By.xpath(folder_xpath),
                "Cannot find option to add article to My lists",
                10
        );
    }

    public void closeArticle() {

        this.waitForElementAndClick(
                By.id(CLOSE_ARTICLE_BUTTON),
                "Cannot close article, cannot find X button",
                10
        );
    }

    private static String getFolderByName(String name_of_folder) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }
}
