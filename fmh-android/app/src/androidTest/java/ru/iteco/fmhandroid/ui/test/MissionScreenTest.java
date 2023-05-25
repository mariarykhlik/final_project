package ru.iteco.fmhandroid.ui.test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static ru.iteco.fmhandroid.ui.data.Datahelper.validInfo;

import androidx.test.espresso.PerformException;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.screen.AppBarFragment;
import ru.iteco.fmhandroid.ui.screen.AuthScreen;
import ru.iteco.fmhandroid.ui.screen.MainScreen;
import ru.iteco.fmhandroid.ui.screen.MissionScreen;

@RunWith(AllureAndroidJUnit4.class)
public class MissionScreenTest {
    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthScreen authScreen = new AuthScreen();
    MainScreen mainScreen = new MainScreen();
    AppBarFragment appBarFragment = new AppBarFragment();
    MissionScreen missionScreen = new MissionScreen();

    @Before
    public void appSetUp() {
        try {
            authScreen.waitLoginButton();
            authScreen.login(
                    validInfo().getLogin(),
                    validInfo().getPassword()
            );
            mainScreen.waitAllNews();
        } catch (PerformException e) {
            mainScreen.waitAllNews();
        }
        appBarFragment.navigateToMission();
        missionScreen.waitMissionItem();
    }

    @DisplayName("Проверка отображения элементов экрана")
    @Test
    public void screenElementsTest() {
        missionScreen.checkMissionScreen();
    }

    @DisplayName("Проверка разворачивания тезиса по нажатию")
    @Test
    public void expandMissionItemTest() throws InterruptedException {
        missionScreen.getMissionItemsList().perform(actionOnItemAtPosition(0, click()));
        Thread.sleep(200);
        missionScreen.getMissionItemDescription().check(matches(withEffectiveVisibility(VISIBLE)));
    }
}