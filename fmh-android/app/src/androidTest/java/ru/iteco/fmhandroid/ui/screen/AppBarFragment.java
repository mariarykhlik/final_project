package ru.iteco.fmhandroid.ui.screen;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import static ru.iteco.fmhandroid.ui.data.Datahelper.waitForView;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class AppBarFragment {
    private ViewInteraction mainMenuButton = onView(allOf(withId(R.id.main_menu_image_button),
            withEffectiveVisibility(VISIBLE)));
    private ViewInteraction trademark = onView(withId(R.id.trademark_image_view));
    private ViewInteraction missionButton = onView(withId(R.id.our_mission_image_button));
    private ViewInteraction authButton = onView(withId(R.id.authorization_image_button));
    private ViewInteraction main = onView(withText("Главная"));
    private ViewInteraction claims = onView(withText("Заявки"));
    private ViewInteraction news = onView(withText("Новости"));
    private ViewInteraction about = onView(withText("О приложении"));
    private ViewInteraction logout = onView(withText("Выйти"));

    public ViewInteraction getMainMenuButton() {
        return mainMenuButton;
    }

    public ViewInteraction getTrademark() {
        return trademark;
    }

    public ViewInteraction getMissionButton() {
        return missionButton;
    }

    public ViewInteraction getAuthButton() {
        return authButton;
    }

    public ViewInteraction getMain() {
        return main;
    }

    public ViewInteraction getClaims() {
        return claims;
    }

    public ViewInteraction getNews() {
        return news;
    }

    public ViewInteraction getAbout() {
        return about;
    }

    public ViewInteraction getLogout() {
        return logout;
    }

    public void waitMainMenuButton() {
        onView(isRoot()).perform(waitForView(R.id.main_menu_image_button, 10000));
    }

    public void checkAppBar() {
        getMainMenuButton().check(matches(isDisplayed()));
        getTrademark().check(matches(isDisplayed()));
        getMissionButton().check(matches(isDisplayed()));
        getAuthButton().check(matches(isDisplayed()));
    }

    public void navigateToMain() {
        getMainMenuButton().perform(click());
        getMain().check(matches(isDisplayed())).perform(click());
    }

    public void navigateToClaims() {
        getMainMenuButton().perform(click());
        getClaims().check(matches(isDisplayed())).perform(click());
    }

    public void navigateToNews() {
        getMainMenuButton().perform(click());
        getNews().check(matches(isDisplayed())).perform(click());
    }

    public void navigateToAbout() {
        getMainMenuButton().perform(click());
        getAbout().check(matches(isDisplayed())).perform(click());
    }

    public void navigateToMission() {
        getMissionButton().perform(click());
    }

    public void logout() {
        getAuthButton().perform(click());
        getLogout().check(matches(isDisplayed())).perform(click());
    }
}
