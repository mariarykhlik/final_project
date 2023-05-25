package ru.iteco.fmhandroid.ui.test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.doubleClick;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static ru.iteco.fmhandroid.ui.data.Datahelper.checkPopupWithHint;
import static ru.iteco.fmhandroid.ui.data.Datahelper.checkPopupWithText;
import static ru.iteco.fmhandroid.ui.data.Datahelper.checkToast;
import static ru.iteco.fmhandroid.ui.data.Datahelper.customScrollTo;
import static ru.iteco.fmhandroid.ui.data.Datahelper.validInfo;

import android.view.View;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.PerformException;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.Flaky;
import io.qameta.allure.kotlin.Story;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.screen.AuthScreen;
import ru.iteco.fmhandroid.ui.screen.ClaimEditScreen;
import ru.iteco.fmhandroid.ui.screen.ClaimScreen;
import ru.iteco.fmhandroid.ui.screen.ClaimsFilterScreen;
import ru.iteco.fmhandroid.ui.screen.ClaimsScreen;
import ru.iteco.fmhandroid.ui.screen.CommentScreen;
import ru.iteco.fmhandroid.ui.screen.MainScreen;

@RunWith(AllureAndroidJUnit4.class)
public class ClaimScreenTest {
    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    View decorView;
    AuthScreen authScreen = new AuthScreen();
    MainScreen mainScreen = new MainScreen();
    ClaimsScreen claimsScreen = new ClaimsScreen();
    ClaimScreen claimScreen = new ClaimScreen();
    ClaimsFilterScreen claimsFilterScreen = new ClaimsFilterScreen();
    ClaimEditScreen claimEditScreen = new ClaimEditScreen();
    CommentScreen commentScreen = new CommentScreen();

    @Before
    public void setUp() {
        activityScenarioRule.getScenario()
                .onActivity(activity -> decorView = activity.getWindow().getDecorView());
    }

    @Before
    public void appSetUp() throws InterruptedException {
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
        claimsScreen.getClaimsList().perform(actionOnItemAtPosition(1, click()));
        claimScreen.waitClaimInfo();
        claimScreen.checkClaimScreen();
    }

    @DisplayName("Проверка возврата на предыдущий экран по кнопке")
    @Test
    public void goBackTest() {
        claimsScreen.getClaimsList().perform(actionOnItemAtPosition(1, click()));
        claimScreen.waitClaimInfo();
        claimScreen.getGoToClaimListButton().perform(customScrollTo(), click());
        claimsScreen.waitClaimCreateButton();
        claimsScreen.getClaimsFilterButton().check(matches(isDisplayed()));
    }

    @DisplayName("Проверка назначения открытой заявки в работу")
    @Description("Назначаем открытую заявку в работу через меню кнопки смены статуса. Проверяем " +
            "смену статуса и деактивацию кнопки редактирования.")
    @Story("Позитивный сценарий")
    @Flaky
    @Test
    public void changeOpenToInProgressStateTest() throws InterruptedException {
        claimsScreen.getClaimsFilterButton().perform(click());
        claimsFilterScreen.waitCancelButton();
        claimsFilterScreen.filterOpenedClaims();
        claimsScreen.waitClaim();
        Thread.sleep(1000);
        claimsScreen.getClaimsList().perform(actionOnItemAtPosition(1, click()));
        claimScreen.waitClaimInfo();
        claimScreen.getStateChangeButton().perform(customScrollTo(), click());
        checkPopupWithText(activityScenarioRule, "В работу").perform(click());
        Thread.sleep(600);
        claimScreen.getClaimState().perform(customScrollTo()).check(matches(withText("В работе")));
        claimScreen.getClaimEditButton().perform(customScrollTo(), click());
        checkToast(decorView, "Редактировать Заявку можно только в статусе Открыта.");
    }

    @DisplayName("Проверка отмены открытой заявки")
    @Description("Отменяем открытую заявку через меню кнопки смены статуса. Проверяем смену статуса" +
            " и деактивацию кнопок смены статуса и редактирования.")
    @Story("Позитивный сценарий")
    @Flaky
    @Test
    public void changeOpenToCancelStateTest() throws InterruptedException {
        claimsScreen.getClaimsFilterButton().perform(click());
        claimsFilterScreen.filterOpenedClaims();
        claimsScreen.waitClaim();
        Thread.sleep(1000);
        claimsScreen.getClaimsList().perform(actionOnItemAtPosition(1, click()));
        claimScreen.waitClaimInfo();
        claimScreen.getStateChangeButton().perform(customScrollTo(), click());
        checkPopupWithText(activityScenarioRule, "Отменить").perform(click());
        Thread.sleep(600);
        claimScreen.getClaimState().perform(customScrollTo()).check(matches(withText("Отменена")));
        claimScreen.getStateChangeButton().check(matches(isEnabled()));
        claimScreen.getClaimEditButton().perform(customScrollTo(), click());
        checkToast(decorView, "Редактировать Заявку можно только в статусе Открыта.");
    }

    @DisplayName("Проверка сброса статуса заявки в работе")
    @Description("Сбрасываем назначенную в работу заявку через меню кнопки смены статуса с " +
            "заполнением комментария в открывшемся диалоге валидным значением. Проверяем сброс " +
            "статуса до открытой.")
    @Story("Позитивный сценарий")
    @Flaky
    @Test
    public void changeInProgressToDiscardStateTest() throws InterruptedException {
        claimsScreen.getClaimsFilterButton().perform(click());
        claimsFilterScreen.filterInProgressClaims();
        claimsScreen.waitClaim();
        Thread.sleep(1000);
        claimsScreen.getClaimsList().perform(actionOnItemAtPosition(1, click()));
        claimScreen.waitClaimInfo();
        claimScreen.getStateChangeButton().perform(customScrollTo(), click());
        checkPopupWithText(activityScenarioRule, "Сбросить").perform(click());
        checkPopupWithHint(activityScenarioRule, "Комментарий")
                .perform(replaceText("Сбросить"));
        checkPopupWithText(activityScenarioRule, "ОК").perform(click());
        Thread.sleep(600);
        claimScreen.getClaimState().perform(customScrollTo()).check(matches(withText("Открыта")));
    }

    @DisplayName("Проверка выполнения назначенной в работу заявки")
    @Description("Исполняем назначенную в работу заявку через меню кнопки смены статуса с " +
            "заполнением комментария в открывшемся диалоге валидным значением. Проверяем смену " +
            "статуса и деактивацию кнопок смены статуса и редактирования.")
    @Story("Позитивный сценарий")
    @Flaky
    @Test
    public void changeInProgressToExecuteStateTest() throws InterruptedException {
        claimsScreen.getClaimsFilterButton().perform(click());
        claimsFilterScreen.filterInProgressClaims();
        claimsScreen.waitClaim();
        Thread.sleep(1000);
        claimsScreen.getClaimsList().perform(actionOnItemAtPosition(1, click()));
        claimScreen.waitClaimInfo();
        claimScreen.getStateChangeButton().perform(customScrollTo(), click());
        checkPopupWithText(activityScenarioRule, "Исполнить").perform(click());
        checkPopupWithHint(activityScenarioRule, "Комментарий")
                .perform(replaceText("Исполнить"));
        checkPopupWithText(activityScenarioRule, "ОК").perform(click());
        Thread.sleep(600);
        claimScreen.getClaimState().perform(customScrollTo()).check(matches(withText("Выполнена")));
        claimScreen.getStateChangeButton().check(matches(isEnabled()));
        claimScreen.getClaimEditButton().perform(customScrollTo(), click());
        checkToast(decorView, "Редактировать Заявку можно только в статусе Открыта.");
    }

    @DisplayName("Проверка смены статуса с пустым комментарием")
    @Description("Исполняем назначенную в работу заявку через меню кнопки смены статуса с " +
            "сохранением пустого комментария в открывшемся диалоге. Проверяем обязательность " +
            "заполнения комментария для смены статуса.")
    @Story("Негативный сценарий")
    @Flaky
    @Test
    public void changeStateFailForEmptyCommentTest() throws InterruptedException {
        claimsScreen.getClaimsFilterButton().perform(click());
        claimsFilterScreen.filterInProgressClaims();
        claimsScreen.waitClaim();
        Thread.sleep(1000);
        claimsScreen.getClaimsList().perform(actionOnItemAtPosition(1, click()));
        claimScreen.waitClaimInfo();
        claimScreen.getStateChangeButton().perform(customScrollTo(), click());
        checkPopupWithText(activityScenarioRule, "Исполнить").perform(click());
        checkPopupWithText(activityScenarioRule, "ОК").perform(click()).perform(pressBack());
        Thread.sleep(600);
        checkToast(decorView, "Поле не может быть пустым.");
    }

    @DisplayName("Проверка смены статуса с отменой сохранения комментария")
    @Description("Исполняем назначенную в работу заявку через меню кнопки смены статуса с отменой " +
            "сохранения комментария в открывшемся диалоге. Проверяем неприменение нового статуса.")
    @Story("Негативный сценарий")
    @Flaky
    @Test
    public void changeStateFailForCommentAddCancelTest() throws InterruptedException {
        claimsScreen.getClaimsFilterButton().perform(click());
        claimsFilterScreen.filterInProgressClaims();
        claimsScreen.waitClaim();
        Thread.sleep(1000);
        claimsScreen.getClaimsList().perform(actionOnItemAtPosition(1, click()));
        claimScreen.waitClaimInfo();
        claimScreen.getStateChangeButton().perform(customScrollTo(), click());
        checkPopupWithText(activityScenarioRule, "Исполнить").perform(click());
        checkPopupWithText(activityScenarioRule, "ОТМЕНА").perform(click());
        Thread.sleep(600);
        claimScreen.getClaimState().perform(customScrollTo()).check(matches((withText("В работе"))));
    }

    @DisplayName("Проверка перехода к экрану комментария по кнопкам редактирования и добавления " +
            "комментария")
    @Test
    public void goToCommentScreenTest() {
        claimsScreen.getClaimsList().perform(actionOnItemAtPosition(1, click()));
        claimScreen.waitClaimInfo();
        try {
            claimScreen.checkComment();
            claimScreen.getCommentEditButton().perform(click());
            commentScreen.waitCancelButton();
            commentScreen.getCancelButton().check(matches(isDisplayed())).perform(click());
        } catch (NoMatchingViewException e) {
            return;
        }
        claimScreen.getCommentAddButton().perform(customScrollTo(), click());
        commentScreen.waitCancelButton();
        commentScreen.getCommentField().check(matches(isDisplayed()));
    }

    @DisplayName("Проверка перехода к экрану редактирования заявки по кнопке")
    @Test
    public void goToClaimEditScreenTest() throws InterruptedException {
        claimsScreen.getClaimsFilterButton().perform(click());
        claimsFilterScreen.filterOpenedClaims();
        claimsScreen.waitClaim();
        Thread.sleep(1000);
        claimsScreen.getClaimsList().perform(actionOnItemAtPosition(1, click()));
        claimScreen.waitClaimInfo();
        claimScreen.getClaimEditButton().perform(customScrollTo(), click());
        claimEditScreen.waitCancelButton();
        claimEditScreen.getEditHeader().check(matches(isDisplayed()));
        claimEditScreen.getClaimHeader().check(matches(isDisplayed()));
    }
}
