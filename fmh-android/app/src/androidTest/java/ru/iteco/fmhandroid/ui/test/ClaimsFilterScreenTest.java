package ru.iteco.fmhandroid.ui.test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static ru.iteco.fmhandroid.ui.data.Datahelper.validInfo;

import androidx.test.espresso.PerformException;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.google.android.gms.tasks.OnFailureListener;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Attachment;
import io.qameta.allure.kotlin.Flaky;
import io.qameta.allure.kotlin.Story;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.screen.AuthScreen;
import ru.iteco.fmhandroid.ui.screen.ClaimScreen;
import ru.iteco.fmhandroid.ui.screen.ClaimsFilterScreen;
import ru.iteco.fmhandroid.ui.screen.ClaimsScreen;
import ru.iteco.fmhandroid.ui.screen.MainScreen;

@RunWith(AllureAndroidJUnit4.class)
public class ClaimsFilterScreenTest {
    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthScreen authScreen = new AuthScreen();
    MainScreen mainScreen = new MainScreen();
    ClaimsScreen claimsScreen = new ClaimsScreen();
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
        claimsScreen.waitClaimCreateButton();
        claimsScreen.getClaimsFilterButton().perform(click());
        claimsFilterScreen.waitCancelButton();
    }


    @DisplayName("Проверка отображения элементов экрана")
    @Test
    public void screenElementsTest() {
        claimsFilterScreen.checkClaimsFilterScreen();
    }

    @DisplayName("Проверка фильтрации открытых заявок")
    @Story("Позитивный сценарий")
    @Flaky
    @Test
    public void filterOpenedClaimsTest() throws InterruptedException {
        claimsFilterScreen.filterOpenedClaims();
        claimsScreen.waitClaim();
        Thread.sleep(1000);
        claimsScreen.getClaimsList().perform(actionOnItemAtPosition(2, click()));
        claimScreen.waitClaimInfo();
        claimScreen.getClaimState().check(matches(withText("Открыта")));
    }

    @DisplayName("Проверка фильтрации заявок в работе")
    @Story("Позитивный сценарий")
    @Flaky
    @Test
    public void filterInProgressClaimsTest() throws InterruptedException {
        claimsFilterScreen.filterInProgressClaims();
        claimsScreen.waitClaim();
        Thread.sleep(1000);
        claimsScreen.getClaimsList().perform(actionOnItemAtPosition(2, click()));
        claimScreen.waitClaimInfo();
        claimScreen.getClaimState().check(matches(withText("В работе")));
    }

    @DisplayName("Проверка фильтрации выполненных заявок")
    @Story("Позитивный сценарий")
    @Flaky
    @Test
    public void filterExecutedClaimsTest() throws InterruptedException {
        claimsFilterScreen.filterExecutedClaims();
        claimsScreen.waitClaim();
        Thread.sleep(1000);
        claimsScreen.getClaimsList().perform(actionOnItemAtPosition(2, click()));
        claimScreen.waitClaimInfo();
        claimScreen.getClaimState().check(matches(withText("Выполнена")));
    }


    @DisplayName("Проверка фильтрации отмененных заявок")
    @Story("Позитивный сценарий")
    @Flaky
    @Test
    public void filterCancelledClaimsTest() throws InterruptedException {
        claimsFilterScreen.filterCancelledClaims();
        claimsScreen.waitClaim();
        Thread.sleep(1000);
        claimsScreen.getClaimsList().perform(actionOnItemAtPosition(2, click()));
        claimScreen.waitClaimInfo();
        claimScreen.getClaimState().check(matches(withText("Отменена")));
    }

    @DisplayName("Проверка отмены применения фильтра")
    @Test
    public void filterCancelTest() throws InterruptedException {
        claimsFilterScreen.getCheckBoxOpened().perform(click());
        claimsFilterScreen.getCheckBoxInProgress().perform(click());
        claimsFilterScreen.getCheckBoxCancelled().perform(click());
        claimsFilterScreen.getCancelButton().perform(click());
        claimsScreen.waitClaim();
        Thread.sleep(1000);
        claimsScreen.getClaimsList().perform(actionOnItemAtPosition(2, click()));
        claimScreen.waitClaimInfo();
        claimScreen.getClaimState().check(matches(not(withText("Отменена"))));
    }
}
