package ru.iteco.fmhandroid.ui.screen;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.Visibility;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import static ru.iteco.fmhandroid.ui.data.Datahelper.waitForView;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class ClaimCreateScreen {
    private ViewInteraction createHeader = onView(allOf(withId(R.id.custom_app_bar_title_text_view),
            withText("Создание")));
    private ViewInteraction claimHeader = onView(allOf(withId(R.id.
            custom_app_bar_sub_title_text_view), withText("Заявки")));
    private ViewInteraction topicField = onView(withHint("Тема"));
    private ViewInteraction executorField = onView(withHint("Исполнитель"));
    private ViewInteraction executorMenuButton = onView(allOf(withId(R.id.text_input_end_icon),
            withEffectiveVisibility(Visibility.VISIBLE)));
    private ViewInteraction dateField = onView(withHint("Дата"));
    private ViewInteraction timeField = onView(withHint("Время"));
    private ViewInteraction descriptionField = onView(withHint("Описание"));
    private ViewInteraction saveButton = onView(withId(R.id.save_button));
    private ViewInteraction cancelButton = onView(withId(R.id.cancel_button));

    public ViewInteraction getCreateHeader() {
        return createHeader;
    }

    public ViewInteraction getClaimHeader() {
        return claimHeader;
    }

    public ViewInteraction getTopicField() {
        return topicField;
    }

    public ViewInteraction getExecutorField() {
        return executorField;
    }

    public ViewInteraction getExecutorMenuButton() {
        return executorMenuButton;
    }

    public ViewInteraction getDateField() {
        return dateField;
    }

    public ViewInteraction getTimeField() {
        return timeField;
    }

    public ViewInteraction getDescriptionField() {
        return descriptionField;
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

    public void checkClaimCreateScreen() {
        getClaimHeader().check(matches(isDisplayed()));
        getCreateHeader().check(matches(isDisplayed()));
        getTopicField().check(matches(isDisplayed()));
        getExecutorField().check(matches(isDisplayed()));
        getExecutorMenuButton().check(matches(isDisplayed()));
        getDateField().check(matches(isDisplayed()));
        getTimeField().check(matches(isDisplayed()));
        getDescriptionField().check(matches(isDisplayed()));
        getSaveButton().check(matches(isDisplayed()));
        getCancelButton().check(matches(isDisplayed()));
    }
}
