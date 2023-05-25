package ru.iteco.fmhandroid.ui.screen;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static ru.iteco.fmhandroid.ui.data.Datahelper.waitForView;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class ClaimsFilterScreen {
    private ViewInteraction filterTitle = onView(withId(R.id.claim_filter_dialog_title));
    private ViewInteraction checkBoxOpened = onView(withId(R.id.item_filter_open));
    private ViewInteraction checkBoxInProgress = onView(withId(R.id.item_filter_in_progress));
    private ViewInteraction checkBoxExecuted = onView(withId(R.id.item_filter_executed));
    private ViewInteraction checkBoxCancelled = onView(withId(R.id.item_filter_cancelled));
    private ViewInteraction okButton = onView(withId(R.id.claim_list_filter_ok_material_button));
    private ViewInteraction cancelButton = onView(withId(R.id.claim_filter_cancel_material_button));

    public ViewInteraction getFilterTitle() {
        return filterTitle;
    }

    public ViewInteraction getCheckBoxOpened() {
        return checkBoxOpened;
    }

    public ViewInteraction getCheckBoxInProgress() {
        return checkBoxInProgress;
    }

    public ViewInteraction getCheckBoxExecuted() {
        return checkBoxExecuted;
    }

    public ViewInteraction getCheckBoxCancelled() {
        return checkBoxCancelled;
    }

    public ViewInteraction getOkButton() {
        return okButton;
    }

    public ViewInteraction getCancelButton() {
        return cancelButton;
    }

    public void waitCancelButton() {
        onView(isRoot()).perform(waitForView(R.id.claim_filter_cancel_material_button, 10000));
    }

    public void checkClaimsFilterScreen() {
        getFilterTitle().check(matches(isDisplayed()));
        getCheckBoxOpened().check(matches(isDisplayed())).check(matches(isChecked()));
        getCheckBoxInProgress().check(matches(isDisplayed())).check(matches(isChecked()));
        getCheckBoxExecuted().check(matches(isDisplayed())).check(matches(isNotChecked()));
        getCheckBoxCancelled().check(matches(isDisplayed())).check(matches(isNotChecked()));
        getOkButton().check(matches(isDisplayed()));
        getCancelButton().check(matches(isDisplayed()));
    }

    public void filterOpenedClaims() {
        getCheckBoxOpened().check(matches(isChecked()));
        getCheckBoxInProgress().perform(click()).check(matches(isNotChecked()));
        getOkButton().perform(click());
    }

    public void filterInProgressClaims() {
        getCheckBoxOpened().perform(click()).check(matches(isNotChecked()));
        getCheckBoxInProgress().check(matches(isChecked()));
        getOkButton().perform(click());
    }

    public void filterExecutedClaims() {
        getCheckBoxOpened().perform(click()).check(matches(isNotChecked()));
        getCheckBoxInProgress().perform(click()).check(matches(isNotChecked()));
        getCheckBoxExecuted().perform(click()).check(matches(isChecked()));
        getOkButton().perform(click());
    }

    public void filterCancelledClaims() {
        getCheckBoxOpened().perform(click()).check(matches(isNotChecked()));
        getCheckBoxInProgress().perform(click()).check(matches(isNotChecked()));
        getCheckBoxCancelled().perform(click()).check(matches(isChecked()));
        getOkButton().perform(click());
    }
}
