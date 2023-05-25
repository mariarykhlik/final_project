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

public class NewsScreen {
    private ViewInteraction newsBlock = onView(withId(R.id.container_list_news_include));
    private ViewInteraction newsBlockHeader = onView(withText("Новости"));
    private ViewInteraction newsSortButton = onView(withId(R.id.sort_news_material_button));
    private ViewInteraction newsFilterButton = onView(withId(R.id.filter_news_material_button));
    private ViewInteraction newsEditButton = onView(withId(R.id.edit_news_material_button));
    private ViewInteraction newsList = onView(withId(R.id.news_list_recycler_view));

    private ViewInteraction categoryIcon = onView(getElementMatchAtPosition(
            withId(R.id.category_icon_image_view), 0));
    private ViewInteraction newsItemTitle = onView(getElementMatchAtPosition(
            withId(R.id.news_item_title_text_view), 0));
    private ViewInteraction expandNewsItemButton = onView(getElementMatchAtPosition(
            withId(R.id.view_news_item_image_view), 0));
    private ViewInteraction newsItemDate = onView(getElementMatchAtPosition(
            withId(R.id.news_item_date_text_view), 0));
    private ViewInteraction newsItemDescription = onView(getElementMatchAtPosition(
            withId(R.id.news_item_description_text_view), 0));

    public ViewInteraction getNewsBlock() {
        return newsBlock;
    }

    public ViewInteraction getNewsBlockHeader() {
        return newsBlockHeader;
    }

    public ViewInteraction getNewsSortButton() {
        return newsSortButton;
    }

    public ViewInteraction getNewsFilterButton() {
        return newsFilterButton;
    }

    public ViewInteraction getNewsEditButton() {
        return newsEditButton;
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

    public ViewInteraction getExpandNewsItemButton() {
        return expandNewsItemButton;
    }

    public ViewInteraction getNewsItemDate() {
        return newsItemDate;
    }

    public ViewInteraction getNewsItemDescription() {
        return newsItemDescription;
    }

    public void waitNewsEditButton() {
        onView(isRoot()).perform(waitForView(R.id.edit_news_material_button, 10000));
    }

    public void waitNews() {
        onView(isRoot()).perform(waitForView(R.id.news_item_date_text_view, 10000));
    }

    public void checkNewsItem() {
        getCategoryIcon().check(matches(isDisplayed()));
        getNewsItemTitle().check(matches(isDisplayed()));
        getExpandNewsItemButton().check(matches(isDisplayed()));
        getNewsItemDate().check(matches(isDisplayed()));
    }

    public void checkNewsScreen() {
        AppBarFragment appBarFragment = new AppBarFragment();

        appBarFragment.checkAppBar();
        getNewsBlock().check(matches(isDisplayed()));
        getNewsBlockHeader().check(matches(isDisplayed()));
        getNewsSortButton().check(matches(isDisplayed()));
        getNewsFilterButton().check(matches(isDisplayed()));
        getNewsEditButton().check(matches(isDisplayed()));
        getNewsList().check(matches(isDisplayed()));
        checkNewsItem();
    }
}
