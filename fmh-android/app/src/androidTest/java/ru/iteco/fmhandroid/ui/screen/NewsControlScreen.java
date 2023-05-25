package ru.iteco.fmhandroid.ui.screen;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static ru.iteco.fmhandroid.ui.data.Datahelper.getElementMatchAtPosition;
import static ru.iteco.fmhandroid.ui.data.Datahelper.waitForView;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class NewsControlScreen {
    private ViewInteraction controlPanelTitle = onView(withText("Панель \n управления"));
    private ViewInteraction newsSortButton = onView(withId(R.id.sort_news_material_button));
    private ViewInteraction newsFilterButton = onView(withId(R.id.filter_news_material_button));
    private ViewInteraction newsCreateButton = onView(withId(R.id.add_news_image_view));
    private ViewInteraction newsList = onView(withId(R.id.news_list_recycler_view));

    private ViewInteraction categoryIcon = onView(getElementMatchAtPosition(
            withId(R.id.category_icon_image_view), 0));
    private ViewInteraction newsItemTitle = onView(getElementMatchAtPosition(
            withId(R.id.news_item_title_text_view), 0));
    private ViewInteraction newsItemPublicationDateTitle = onView(getElementMatchAtPosition(
            withId(R.id.news_item_publication_text_view), 0));
    private ViewInteraction newsItemCreationDateTitle = onView(getElementMatchAtPosition(
            withId(R.id.news_item_creation_text_view), 0));
    private ViewInteraction newsItemPublicationDate = onView(getElementMatchAtPosition(
            withId(R.id.news_item_publication_date_text_view), 0));
    private ViewInteraction newsItemCreationDate = onView(getElementMatchAtPosition(
            withId(R.id.news_item_create_date_text_view), 0));
    private ViewInteraction newsItemAuthorTitle = onView(getElementMatchAtPosition(
            withId(R.id.news_item_author_text_view), 0));
    private ViewInteraction newsItemAuthor = onView(getElementMatchAtPosition(
            withId(R.id.news_item_author_name_text_view), 0));
    private ViewInteraction newsItemPublicationStateIcon = onView(getElementMatchAtPosition(
            withId(R.id.news_item_published_icon_image_view), 0));
    private ViewInteraction newsItemPublicationState = onView(getElementMatchAtPosition(
            withId(R.id.news_item_published_text_view), 0));
    private ViewInteraction newsItemDescription = onView(getElementMatchAtPosition(
            withId(R.id.news_item_description_text_view), 0));
    private ViewInteraction newsItemDeleteButton = onView(getElementMatchAtPosition(
            withId(R.id.delete_news_item_image_view), 0));
    private ViewInteraction newsItemEditButton = onView(getElementMatchAtPosition(
            withId(R.id.edit_news_item_image_view), 0));
    private ViewInteraction newsItemExpandButton = onView(getElementMatchAtPosition(
            withId(R.id.view_news_item_image_view), 0));

    public ViewInteraction getControlPanelTitle() {
        return controlPanelTitle;
    }

    public ViewInteraction getNewsSortButton() {
        return newsSortButton;
    }

    public ViewInteraction getNewsFilterButton() {
        return newsFilterButton;
    }

    public ViewInteraction getNewsCreateButton() {
        return newsCreateButton;
    }

    public ViewInteraction getNewsList() {
        return newsList;
    }

    public ViewInteraction getCategoryIcon() {
        return categoryIcon;
    }

    public ViewInteraction getNewsItemTitle() {
        return newsItemTitle;
    }

    public ViewInteraction getNewsItemPublicationDateTitle() {
        return newsItemPublicationDateTitle;
    }

    public ViewInteraction getNewsItemCreationDateTitle() {
        return newsItemCreationDateTitle;
    }

    public ViewInteraction getNewsItemPublicationDate() {
        return newsItemPublicationDate;
    }

    public ViewInteraction getNewsItemCreationDate() {
        return newsItemCreationDate;
    }

    public ViewInteraction getNewsItemAuthorTitle() {
        return newsItemAuthorTitle;
    }

    public ViewInteraction getNewsItemAuthor() {
        return newsItemAuthor;
    }

    public ViewInteraction getNewsItemPublicationStateIcon() {
        return newsItemPublicationStateIcon;
    }

    public ViewInteraction getNewsItemPublicationState() {
        return newsItemPublicationState;
    }

    public ViewInteraction getNewsItemDescription() {
        return newsItemDescription;
    }

    public ViewInteraction getNewsItemDeleteButton() {
        return newsItemDeleteButton;
    }

    public ViewInteraction getNewsItemEditButton() {
        return newsItemEditButton;
    }

    public ViewInteraction getNewsItemExpandButton() {
        return newsItemExpandButton;
    }

    public void waitNewsCreateButton() {
        onView(isRoot()).perform(waitForView(R.id.add_news_image_view, 10000));
    }

    public void waitNewsItem() {
        onView(isRoot()).perform(waitForView(R.id.news_item_published_text_view, 10000));
    }

    public void checkNewsItem() {
        getCategoryIcon().check(matches(isDisplayed()));
        getNewsItemTitle().check(matches(isDisplayed()));
        getNewsItemPublicationDateTitle().check(matches(isDisplayed()));
        getNewsItemPublicationDate().check(matches(isDisplayed()));
        getNewsItemCreationDateTitle().check(matches(isDisplayed()));
        getNewsItemCreationDate().check(matches(isDisplayed()));
        getNewsItemAuthorTitle().check(matches(isDisplayed()));
        getNewsItemAuthor().check(matches(isDisplayed()));
        getNewsItemPublicationStateIcon().check(matches(isDisplayed()));
        getNewsItemPublicationState().check(matches(isDisplayed()));
        getNewsItemDeleteButton().check(matches(isDisplayed()));
        getNewsItemEditButton().check(matches(isDisplayed()));
        getNewsItemExpandButton().check(matches(isDisplayed()));
    }

    public void checkNewsControlScreen() {
        AppBarFragment appBarFragment = new AppBarFragment();

        appBarFragment.checkAppBar();
        getControlPanelTitle().check(matches(isDisplayed()));
        getNewsSortButton().check(matches(isDisplayed()));
        getNewsFilterButton().check(matches(isDisplayed()));
        getNewsCreateButton().check(matches(isDisplayed()));
        getNewsList().check(matches(isDisplayed()));
        checkNewsItem();
    }
}
