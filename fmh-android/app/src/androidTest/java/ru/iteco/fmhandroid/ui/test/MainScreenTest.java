package ru.iteco.fmhandroid.ui.test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static org.hamcrest.Matchers.not;
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
import ru.iteco.fmhandroid.ui.screen.AuthScreen;
import ru.iteco.fmhandroid.ui.screen.ClaimCreateScreen;
import ru.iteco.fmhandroid.ui.screen.ClaimScreen;
import ru.iteco.fmhandroid.ui.screen.ClaimsScreen;
import ru.iteco.fmhandroid.ui.screen.MainScreen;
import ru.iteco.fmhandroid.ui.screen.NewsScreen;

@RunWith(AllureAndroidJUnit4.class)
public class MainScreenTest {
    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthScreen authScreen = new AuthScreen();
    MainScreen mainScreen = new MainScreen();
    NewsScreen newsScreen = new NewsScreen();
    ClaimsScreen claimsScreen = new ClaimsScreen();
    ClaimCreateScreen claimCreateScreen = new ClaimCreateScreen();
    ClaimScreen claimScreen = new ClaimScreen();

    @Before
    public void appSetUp() {
        try {
            authScreen.waitLoginButton();
            authScreen.login(
                    validInfo().getLogin(),
                    validInfo().getPassword()
            );
            mainScreen.waitClaims();
        } catch (PerformException e) {
            mainScreen.waitClaims();
        }
    }

    @DisplayName("Проверка отображения элементов экрана")
    @Test
    public void screenElementsTest() {
        mainScreen.checkMainScreen();
    }

    @DisplayName("Проверка сворачивания и разворачивания блока новостей по кнопке")
    @Test
    public void collapseExpandNewsBlockTest() {
        mainScreen.getCollapseNewsButton().perform(click());
        mainScreen.getAllNewsButton().check(matches(not(isDisplayed())));
        mainScreen.getCollapseNewsButton().perform(click());
        mainScreen.getAllNewsButton().check(matches(isDisplayed()));
    }

    @DisplayName("Проверка сворачивания и разворачивания блока заявок по кнопке")
    @Test
    public void collapseExpandClaimsBlockTest() {
        mainScreen.getCollapseClaimsButton().perform(click());
        mainScreen.getAllClaimsButton().check(matches(not(isDisplayed())));
        mainScreen.getCollapseClaimsButton().perform(click());
        mainScreen.getAllClaimsButton().check(matches(isDisplayed()));
    }

    @DisplayName("Проверка перехода к экрану новостей по кнопке")
    @Test
    public void goToAllNewsTest() {
        mainScreen.getAllNewsButton().perform(click());
        newsScreen.waitNewsEditButton();
        newsScreen.getNewsBlockHeader().check(matches(isDisplayed()));
    }

    @DisplayName("Проверка перехода к экрану заявок по кнопке")
    @Test
    public void goToAllClaimsTest() {
        mainScreen.getAllClaimsButton().perform(click());
        claimsScreen.waitClaimCreateButton();
        claimsScreen.getClaimsBlockHeader().check(matches(isDisplayed()));
    }

    @DisplayName("Проверка перехода к экрану просмотра заявки по нажатию на заявку")
    @Test
    public void goToClaimScreenTest() {
        mainScreen.getClaimsList().perform(actionOnItemAtPosition(0, click()));
        claimScreen.waitClaimInfo();
        claimScreen.getDescription().check(matches(isDisplayed()));
    }

    @DisplayName("Проверка перехода к экрану создания заявки по кнопке")
    @Test
    public void goToClaimCreateScreenTest() {
        mainScreen.getClaimCreateButton().perform(click());
        claimCreateScreen.waitCancelButton();
        claimCreateScreen.getCreateHeader().check(matches(isDisplayed()));
        claimCreateScreen.getClaimHeader().check(matches(isDisplayed()));
    }

    @DisplayName("Проверка разворачивания новости по нажатию на новость")
    @Test
    public void expandNewsItemTest() throws InterruptedException {
        mainScreen.getNewsList().perform(actionOnItemAtPosition(0, click()));
        Thread.sleep(200);
        mainScreen.getNewsItemDescription().check(matches(withEffectiveVisibility(VISIBLE)));
    }
}