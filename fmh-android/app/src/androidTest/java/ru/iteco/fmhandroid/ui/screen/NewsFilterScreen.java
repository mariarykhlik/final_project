package ru.iteco.fmhandroid.ui.screen;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.Visibility.GONE;
import static androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

import static ru.iteco.fmhandroid.ui.data.Datahelper.waitForView;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;

import ru.iteco.fmhandroid.R;

public class NewsFilterScreen {
    private ViewInteraction newsFilterTitle = onView(withId(R.id.filter_news_title_text_view));
    private ViewInteraction categoryField = onView(withHint("Категория"));
    private ViewInteraction categoryMenuButton = onView(allOf(withId(R.id.text_input_end_icon),
            withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    private ViewInteraction periodStartField = onView(withId(
            R.id.news_item_publish_date_start_text_input_edit_text));
    private ViewInteraction periodEndField = onView(withId(
            R.id.news_item_publish_date_end_text_input_edit_text));
    private ViewInteraction checkBoxActive = onView(withId(
            R.id.filter_news_active_material_check_box));
    private ViewInteraction checkBoxInactive = onView(withId(
            R.id.filter_news_inactive_material_check_box));
    private ViewInteraction filterButton = onView(withId(R.id.filter_button));
    private ViewInteraction cancelButton = onView(withId(R.id.cancel_button));

    public ViewInteraction getNewsFilterTitle() {
        return newsFilterTitle;
    }

    public ViewInteraction getCategoryField() {
        return categoryField;
    }

    public ViewInteraction getCategoryMenuButton() {
        return categoryMenuButton;
    }

    public ViewInteraction getPeriodStartField() {
        return periodStartField;
    }

    public ViewInteraction getPeriodEndField() {
        return periodEndField;
    }

    public ViewInteraction getCheckBoxActive() {
        return checkBoxActive;
    }

    public ViewInteraction getCheckBoxInactive() {
        return checkBoxInactive;
    }

    public ViewInteraction getFilterButton() {
        return filterButton;
    }

    public ViewInteraction getCancelButton() {
        return cancelButton;
    }

    public void waitCancelButton() {
        onView(isRoot()).perform(waitForView(R.id.cancel_button, 10000));
    }

    public void checkNewsFilterScreen() {
        getNewsFilterTitle().check(matches(isDisplayed()));
        getCategoryField().check(matches(isDisplayed()));
        getCategoryMenuButton().check(matches(isDisplayed()));
        getPeriodStartField().check(matches(isDisplayed()));
        getPeriodEndField().check(matches(isDisplayed()));
        getFilterButton().check(matches(isDisplayed()));
        getCancelButton().check(matches(isDisplayed()));
    }

    public void checkCheckboxesForFilterFromNewsScreen() {
        getCheckBoxActive().check(matches(withEffectiveVisibility(GONE)));
        getCheckBoxInactive().check(matches(withEffectiveVisibility(GONE)));
    }
    public void checkCheckboxesForFilterFromNewsControlScreen() {
        getCheckBoxActive().check(matches(withEffectiveVisibility(VISIBLE)))
                .check(matches(isChecked()));
        getCheckBoxInactive().check(matches(withEffectiveVisibility(VISIBLE)))
                .check(matches(isChecked()));
    }
}
