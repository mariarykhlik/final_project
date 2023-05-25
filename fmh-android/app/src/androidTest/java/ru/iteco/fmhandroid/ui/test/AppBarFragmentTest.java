package ru.iteco.fmhandroid.ui.test;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
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
import ru.iteco.fmhandroid.ui.screen.AboutScreen;
import ru.iteco.fmhandroid.ui.screen.AppBarFragment;
import ru.iteco.fmhandroid.ui.screen.AuthScreen;
import ru.iteco.fmhandroid.ui.screen.ClaimsScreen;
import ru.iteco.fmhandroid.ui.screen.MainScreen;
import ru.iteco.fmhandroid.ui.screen.MissionScreen;
import ru.iteco.fmhandroid.ui.screen.NewsScreen;

@RunWith(AllureAndroidJUnit4.class)
public class AppBarFragmentTest {
    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AppBarFragment appBarFragment = new AppBarFragment();
    AuthScreen authScreen = new AuthScreen();
    ClaimsScreen claimsScreen = new ClaimsScreen();
    NewsScreen newsScreen = new NewsScreen();
    MainScreen mainScreen = new MainScreen();
    MissionScreen missionScreen = new MissionScreen();
    AboutScreen aboutScreen = new AboutScreen();

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
    }

    @DisplayName("Проверка отображения элементов главной панели приложения")
    @Test
    public void fragmentElementsTest() {
        appBarFragment.checkAppBar();
    }

    @DisplayName("Проверка навигации по экранам через меню главной панели приложения")
    @Test
    public void navigationByMenuTest() {
        appBarFragment.navigateToClaims();
        claimsScreen.waitClaimCreateButton();
        claimsScreen.getClaimsBlockHeader().check(matches(isDisplayed()));
        appBarFragment.navigateToNews();
        newsScreen.waitNewsEditButton();
        newsScreen.getNewsBlockHeader().check(matches(isDisplayed()));
        appBarFragment.navigateToMain();
        mainScreen.waitAllClaims();
        mainScreen.getNewsBlockHeader().check(matches(isDisplayed()));
        mainScreen.getClaimsBlockHeader().check(matches(isDisplayed()));
        appBarFragment.navigateToAbout();
        aboutScreen.waitTrademark();
        aboutScreen.getVersionTitle().check(matches(isDisplayed()));
    }

    @DisplayName("Проверка перехода на экран цитат по кнопке главной панели")
    @Test
    public void goToMissionScreenTest() {
        appBarFragment.navigateToMission();
        missionScreen.waitMissionItem();
        missionScreen.getMissionHeader().check(matches(isDisplayed()));
    }

    @DisplayName("Проверка завершения сеанса пользователя по кнопке главной панели")
    @Test
    public void logoutTest() {
        appBarFragment.logout();
        authScreen.waitLoginButton();
        authScreen.getAuthHeader().check(matches(isDisplayed()));
    }
}
