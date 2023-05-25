package ru.iteco.fmhandroid.ui.screen;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.data.Datahelper.getElementMatchAtPosition;
import static ru.iteco.fmhandroid.ui.data.Datahelper.waitForView;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class ClaimsScreen {
    private ViewInteraction claimsBlock = onView(withId(R.id.container_list_claim_include));
    private ViewInteraction claimsBlockHeader = onView(withText("Заявки"));
    private ViewInteraction claimsFilterButton = onView(withId(R.id.filters_material_button));
    private ViewInteraction claimCreateButton = onView(withId(R.id.add_new_claim_material_button));
    private ViewInteraction claimsList = onView(withId(R.id.claim_list_recycler_view));
    private ViewInteraction claimsItem = onView(getElementMatchAtPosition(withParent(
            withId(R.id.claim_list_recycler_view)), 0));

    private ViewInteraction climeTopicTitle = onView(getElementMatchAtPosition(
            withId(R.id.title_material_text_view), 0));
    private ViewInteraction climeTopic = onView(getElementMatchAtPosition(
            withId(R.id.description_material_text_view), 0));
    private ViewInteraction executorTitle = onView(getElementMatchAtPosition(
            withId(R.id.executor_name_label_material_text_view), 0));
    private ViewInteraction executor = onView(getElementMatchAtPosition(
            withId(R.id.executor_name_material_text_view), 0));
    private ViewInteraction planDateTitle = onView(getElementMatchAtPosition(
            withId(R.id.plan_date_label_material_text_view), 0));
    private ViewInteraction planDate = onView(getElementMatchAtPosition(
            withId(R.id.plan_date_material_text_view), 0));
    private ViewInteraction planTime = onView(getElementMatchAtPosition(
            withId(R.id.plan_time_material_text_view), 0));

    public ViewInteraction getClaimsBlock() {
        return claimsBlock;
    }

    public ViewInteraction getClaimsBlockHeader() {
        return claimsBlockHeader;
    }

    public ViewInteraction getClaimsFilterButton() {
        return claimsFilterButton;
    }

    public ViewInteraction getClaimCreateButton() {
        return claimCreateButton;
    }

    public ViewInteraction getClaimsList() {
        return claimsList;
    }

    public ViewInteraction getClaimsItem() {
        return claimsItem;
    }

    public ViewInteraction getClimeTopicTitle() {
        return climeTopicTitle;
    }

    public ViewInteraction getClimeTopic() {
        return climeTopic;
    }

    public ViewInteraction getExecutorTitle() {
        return executorTitle;
    }

    public ViewInteraction getExecutor() {
        return executor;
    }

    public ViewInteraction getPlanDateTitle() {
        return planDateTitle;
    }

    public ViewInteraction getPlanDate() {
        return planDate;
    }

    public ViewInteraction getPlanTime() {
        return planTime;
    }

    public void waitClaimCreateButton() {
        onView(isRoot()).perform(waitForView(R.id.add_new_claim_material_button, 10000));
    }

    public void waitClaim() {
        onView(isRoot()).perform(waitForView(R.id.plan_time_material_text_view, 10000));
    }

    public void checkClaimsItem() {
        getClimeTopicTitle().check(matches(isDisplayed()));
        getClimeTopic().check(matches(isDisplayed()));
        getExecutorTitle().check(matches(isDisplayed()));
        getExecutor().check(matches(isDisplayed()));
        getPlanDateTitle().check(matches(isDisplayed()));
        getPlanDate().check(matches(isDisplayed()));
        getPlanTime().check(matches(isDisplayed()));
    }

    public void checkClaimsScreen() {
        AppBarFragment appBarFragment = new AppBarFragment();

        appBarFragment.checkAppBar();
        getClaimsBlock().check(matches(isDisplayed()));
        getClaimsBlockHeader().check(matches(isDisplayed()));
        getClaimsFilterButton().check(matches(isDisplayed()));
        getClaimCreateButton().check(matches(isDisplayed()));
        getClaimsList().check(matches(isDisplayed()));
        checkClaimsItem();
    }
}
