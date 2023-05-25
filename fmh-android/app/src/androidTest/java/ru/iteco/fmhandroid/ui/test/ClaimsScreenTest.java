package ru.iteco.fmhandroid.ui.test;

import static androidx.test.espresso.action.ViewActions.click;
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
import ru.iteco.fmhandroid.ui.screen.AuthScreen;
import ru.iteco.fmhandroid.ui.screen.ClaimCreateScreen;
import ru.iteco.fmhandroid.ui.screen.ClaimScreen;
import ru.iteco.fmhandroid.ui.screen.ClaimsFilterScreen;
import ru.iteco.fmhandroid.ui.screen.ClaimsScreen;
import ru.iteco.fmhandroid.ui.screen.MainScreen;

@RunWith(AllureAndroidJUnit4.class)
public class ClaimsScreenTest {
    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthScreen authScreen = new AuthScreen();
    MainScreen mainScreen = new MainScreen();
    ClaimsScreen claimsScreen = new ClaimsScreen();
    ClaimCreateScreen claimCreateScreen = new ClaimCreateScreen();
    ClaimScreen claimScreen = new ClaimScreen();
    ClaimsFilterScreen claimsFilterScreen = new ClaimsFilterScreen();

    @Before
    public void appSetUp() {
        try {
            authScreen.waitLoginButton();
            authScreen.login(
                    validInfo().getLogin(),
                    validInfo().getPassword()
            );
            mainScreen.waitAllClaims();
        } catch (PerformException e) {
            mainScreen.waitAllClaims();
        }
        mainScreen.getAllClaimsButton().perform(click());
        claimsScreen.waitClaim();
    }

    @DisplayName("Проверка отображения элементов экрана")
    @Test
    public void screenElementsTest() {
        claimsScreen.checkClaimsScreen();
    }

    @Test
    public void goToClaimCreateScreenTest() {
        claimsScreen.getClaimCreateButton().perform(click());
        claimCreateScreen.waitCancelButton();
        claimCreateScreen.getCreateHeader().check(matches(isDisplayed()));
        claimCreateScreen.getClaimHeader().check(matches(isDisplayed()));
    }

    @DisplayName("Проверка перехода к экрану просмотра заявки по нажатию на заявку")
    @Test
    public void goToClaimScreenTest() {
        claimsScreen.getClaimsItem().perform(click());
        claimScreen.waitClaimInfo();
        claimScreen.getClaimState().check(matches(isDisplayed()));
    }

    @DisplayName("Проверка перехода к экрану фильтрации заявок по кнопке")
    @Test
    public void goToClaimsFilterScreenTest() {
        claimsScreen.getClaimsFilterButton().perform(click());
        claimsFilterScreen.waitCancelButton();
        claimsFilterScreen.getFilterTitle().check(matches(isDisplayed()));
    }
}