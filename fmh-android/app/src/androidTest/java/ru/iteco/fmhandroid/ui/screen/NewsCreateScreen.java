package ru.iteco.fmhandroid.ui.screen;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import static ru.iteco.fmhandroid.ui.data.Datahelper.DateTime.getTime;
import static ru.iteco.fmhandroid.ui.data.Datahelper.cyrillic;
import static ru.iteco.fmhandroid.ui.data.Datahelper.waitForView;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;

import ru.iteco.fmhandroid.R;

public class NewsCreateScreen {
    private ViewInteraction createHeader = onView(allOf(withId(R.id.custom_app_bar_title_text_view),
        withText("Создание")));
    private ViewInteraction newsHeader = onView(allOf(withId(R.id.
            custom_app_bar_sub_title_text_view), withText("Новости")));
    private ViewInteraction categoryField = onView(withHint("Категория"));
    private ViewInteraction categoryMenuButton = onView(allOf(withId(R.id.text_input_end_icon),
            withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    private ViewInteraction titleField = onView(withHint("Заголовок"));
    private ViewInteraction publicationDateField = onView(withHint("Дата публикации"));
    private ViewInteraction publicationTimeField = onView(withHint("Время"));
    private ViewInteraction descriptionField = onView(withHint("Описание"));
    private ViewInteraction activationSwitcher = onView(withId(R.id.switcher));
    private ViewInteraction saveButton = onView(withId(R.id.save_button));
    private ViewInteraction cancelButton = onView(withId(R.id.cancel_button));

    public ViewInteraction getCreateHeader() {
        return createHeader;
    }

    public ViewInteraction getNewsHeader() {
        return newsHeader;
    }

    public ViewInteraction getCategoryField() {
        return categoryField;
    }

    public ViewInteraction getCategoryMenuButton() {
        return categoryMenuButton;
    }

    public ViewInteraction getTitleField() {
        return titleField;
    }

    public ViewInteraction getPublicationDateField() {
        return publicationDateField;
    }

    public ViewInteraction getPublicationTimeField() {
        return publicationTimeField;
    }

    public ViewInteraction getDescriptionField() {
        return descriptionField;
    }

    public ViewInteraction getActivationSwitcher() {
        return activationSwitcher;
    }

    public ViewInteraction getSaveButton() {
        return saveButton;
    }

    public ViewInteraction getCancelButton() {
        return cancelButton;
    }

    public void waitCancelButton() {
        onView(isRoot()).perform(waitForView(R.id.cancel_button, 10000));
    }

    public void checkNewsCreateScreen() {
        getCreateHeader().check(matches(isDisplayed()));
        getNewsHeader().check(matches(isDisplayed()));
        getCategoryField().check(matches(isDisplayed()));
        getCategoryMenuButton().check(matches(isDisplayed()));
        getTitleField().check(matches(isDisplayed()));
        getPublicationDateField().check(matches(isDisplayed()));
        getPublicationTimeField().check(matches(isDisplayed()));
        getDescriptionField().check(matches(isDisplayed()));
        getActivationSwitcher().check(matches(isDisplayed()));
        getActivationSwitcher().check(matches(isNotEnabled()));
        getSaveButton().check(matches(isDisplayed()));
        getCancelButton().check(matches(isDisplayed()));
    }

    public void createNewsForChecks(String category, String title, String date) {
        getCategoryField().perform(replaceText(category));
        getTitleField().perform(replaceText(title));
        getPublicationDateField().perform(replaceText(date));
        getPublicationTimeField().perform(replaceText(getTime(0, 0)));
        getDescriptionField().perform(replaceText(cyrillic(50).getValue()));
        getSaveButton().perform(click());
    }
}
