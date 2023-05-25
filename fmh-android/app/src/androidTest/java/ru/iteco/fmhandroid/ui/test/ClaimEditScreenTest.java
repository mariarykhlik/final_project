package ru.iteco.fmhandroid.ui.test;

import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static ru.iteco.fmhandroid.ui.data.Datahelper.DateTime.getDate;
import static ru.iteco.fmhandroid.ui.data.Datahelper.DateTime.getTime;
import static ru.iteco.fmhandroid.ui.data.Datahelper.checkPopupWithText;
import static ru.iteco.fmhandroid.ui.data.Datahelper.customScrollTo;
import static ru.iteco.fmhandroid.ui.data.Datahelper.cyrillic;
import static ru.iteco.fmhandroid.ui.data.Datahelper.validInfo;

import android.view.View;

import androidx.test.espresso.PerformException;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.Story;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.screen.AuthScreen;
import ru.iteco.fmhandroid.ui.screen.ClaimEditScreen;
import ru.iteco.fmhandroid.ui.screen.ClaimScreen;
import ru.iteco.fmhandroid.ui.screen.ClaimsFilterScreen;
import ru.iteco.fmhandroid.ui.screen.ClaimsScreen;
import ru.iteco.fmhandroid.ui.screen.MainScreen;

@RunWith(AllureAndroidJUnit4.class)
public class ClaimEditScreenTest {
    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    View decorView;
    ClaimEditScreen claimEditScreen = new ClaimEditScreen();
    AuthScreen authScreen = new AuthScreen();
    MainScreen mainScreen = new MainScreen();
    ClaimScreen claimScreen = new ClaimScreen();
    ClaimsScreen claimsScreen = new ClaimsScreen();
    ClaimsFilterScreen claimsFilterScreen = new ClaimsFilterScreen();

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
        claimsScreen.waitClaimCreateButton();
        claimsScreen.getClaimsFilterButton().perform(click());
        claimsFilterScreen.waitCancelButton();
        claimsFilterScreen.filterOpenedClaims();
        claimsScreen.waitClaim();
        Thread.sleep(600);
        claimsScreen.getClaimsList().perform(actionOnItemAtPosition(2, click()));
        claimScreen.waitClaimInfo();
        claimScreen.getClaimEditButton().perform(customScrollTo(), click());
        claimEditScreen.waitCancelButton();
    }

    @DisplayName("Проверка отображения элементов экрана")
    @Test
    public void screenElementsTest() {
        claimEditScreen.checkClaimEditScreen();
    }

    @DisplayName("Проверка успешного редактирования открытой заявки")
    @Description("Заменяем значения полей на другие валидные, назначаем исполнителя. Проверяем по " +
            "сохранению заявки изменение данных и статуса.")
    @Story("Позитивный сценарий")
    @Test
    public void editClaimSuccessTest() throws InterruptedException {
        String topic = cyrillic(10).getValue();
        String description = cyrillic(50).getValue();
        String executor = "Ivanov Ivan Ivanovich";
        String date = getDate(0, 0, 30);
        String time = getTime(0, 30);
        claimEditScreen.getTopicField().perform(replaceText(topic));
        claimEditScreen.getExecutorField().perform(click());
        checkPopupWithText(activityScenarioRule, executor).perform(click());
        claimEditScreen.getDateField().perform(replaceText(date));
        claimEditScreen.getTimeField().perform(replaceText(time));
        claimEditScreen.getDescriptionField().perform(replaceText(description));
        claimEditScreen.getSaveButton().perform(click());
        claimScreen.waitClaimInfo();
        Thread.sleep(600);
        claimScreen.getTopic().check(matches(withText(topic)));
        claimScreen.getExecutor().check(matches(withText(executor)));
        claimScreen.getPlanDate().check(matches(withText(date)));
        claimScreen.getPlanTime().check(matches(withText(time)));
        claimScreen.getClaimState().check(matches(withText("В работе")));
        claimScreen.getDescription().check(matches(withText(description)));
    }

    @DisplayName("Проверка сохранения статуса открытой заявки при назначении на несуществующего " +
            "исполнителя")
    @Description("Вводим вручную несуществующего исполнителя. Проверяем по сохранению заявки " +
            "игнорирование назначения и сохранение статуса.")
    @Story("Негативный сценарий")
    @Test
    public void executorIgnoreTest() throws InterruptedException {
        claimEditScreen.getExecutorField().perform(replaceText("notExisting"));
        claimEditScreen.getSaveButton().perform(click());
        claimScreen.waitClaimInfo();
        Thread.sleep(600);
        claimScreen.getExecutor().check(matches(withText("НЕ НАЗНАЧЕН")));
        claimScreen.getClaimState().check(matches(withText("Открыта")));
    }


    @DisplayName("Проверка сохранения заявки с очищенными полями")
    @Story("Негативный сценарий")
    @Test
    public void createClaimFailForEmptyFieldsTest() {
        claimEditScreen.getTopicField().perform(clearText());
        claimEditScreen.getDateField().perform(clearText());
        claimEditScreen.getTimeField().perform(clearText());
        claimEditScreen.getDescriptionField().perform(clearText());
        claimEditScreen.getSaveButton().perform(click());
        checkPopupWithText(activityScenarioRule, "Заполните пустые поля");
    }

    @DisplayName("Проверка отмены редактировании заявки")
    @Test
    public void createClaimCancelTest() {
        claimEditScreen.getTopicField().perform(replaceText("test"));
        claimEditScreen.getCancelButton().perform(click());
        checkPopupWithText(activityScenarioRule, "Изменения не будут сохранены." +
                " Вы действительно хотите выйти?");
        checkPopupWithText(activityScenarioRule, "OK").perform(click());
        claimScreen.waitClaimInfo();
        claimScreen.getTopic().check(matches(not(withText("test"))));
    }
}
