package ru.iteco.fmhandroid.ui.screen;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;
import static ru.iteco.fmhandroid.ui.data.Datahelper.getElementMatchAtPosition;
import static ru.iteco.fmhandroid.ui.data.Datahelper.waitForView;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class MissionScreen {
    private ViewInteraction missionHeader = onView(withId(R.id.our_mission_title_text_view));
    private ViewInteraction missionItemsList = onView(withId(R.id.our_mission_item_list_recycler_view));

    private ViewInteraction missionItemIcon = onView(getElementMatchAtPosition(
            withId(R.id.our_mission_item_image_view), 0));
    private ViewInteraction missionItemTitle = onView(getElementMatchAtPosition(
            withId(R.id.our_mission_item_title_text_view), 0));
    private ViewInteraction expandMissionItemButton = onView(getElementMatchAtPosition(
            withId(R.id.our_mission_item_open_card_image_button), 0));
    private ViewInteraction missionItemDescription = onView(getElementMatchAtPosition(withId(
            R.id.our_mission_item_description_text_view), 0));

    public ViewInteraction getMissionHeader() {
        return missionHeader;
    }

    public ViewInteraction getMissionItemsList() {
        return missionItemsList;
    }

    public ViewInteraction getMissionItemIcon() {
        return missionItemIcon;
    }

    public ViewInteraction getMissionItemTitle() {
        return missionItemTitle;
    }

    public ViewInteraction getExpandMissionItemButton() {
        return expandMissionItemButton;
    }

    public ViewInteraction getMissionItemDescription() {
        return missionItemDescription;
    }

    public void waitMissionItem() {
        onView(isRoot()).perform(waitForView(R.id.our_mission_item_title_text_view, 10000));
    }

    public void checkMissionItem() {
        getMissionItemIcon().check(matches(isDisplayed()));
        getMissionItemTitle().check(matches(isDisplayed()));
        getExpandMissionItemButton().check(matches(isDisplayed()));
    }

    public void checkMissionScreen() {
        AppBarFragment appBarFragment = new AppBarFragment();

        appBarFragment.checkAppBar();
        getMissionHeader().check(matches(isDisplayed()));
        getMissionItemsList().check(matches(isDisplayed()));
        checkMissionItem();
    }
}