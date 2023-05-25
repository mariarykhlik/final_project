package ru.iteco.fmhandroid.ui.screen;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static ru.iteco.fmhandroid.ui.data.Datahelper.waitForView;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class CommentScreen {
    private ViewInteraction commentField = onView(withHint("Комментарий"));
    private ViewInteraction saveButton = onView(withId(R.id.save_button));
    private ViewInteraction cancelButton = onView(withId(R.id.cancel_button));

    public ViewInteraction getCommentField() {
        return commentField;
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

    public void checkCommentScreen() {
        getCommentField().check(matches(isDisplayed()));
        getSaveButton().check(matches(isDisplayed()));
        getCancelButton().check(matches(isDisplayed()));
    }
}