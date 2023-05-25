package ru.iteco.fmhandroid.ui.test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static ru.iteco.fmhandroid.ui.data.Datahelper.checkPopupWithText;
import static ru.iteco.fmhandroid.ui.data.Datahelper.cyrillic;
import static ru.iteco.fmhandroid.ui.data.Datahelper.validInfo;

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
import ru.iteco.fmhandroid.ui.screen.ClaimCreateScreen;
import ru.iteco.fmhandroid.ui.screen.MainScreen;

@RunWith(AllureAndroidJUnit4.class)
public class ClaimCreateScreenTest {
    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthScreen authScreen = new AuthScreen();
    MainScreen mainScreen = new MainScreen();
    ClaimCreateScreen claimCreateScreen = new ClaimCreateScreen();

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
        mainScreen.getClaimCreateButton().perform(click());
        claimCreateScreen.waitCancelButton();
    }

    @DisplayName("Проверка отображения элементов экрана")
    @Test
    public void screenElementsTest() {
        claimCreateScreen.checkClaimCreateScreen();
    }

    @DisplayName("Проверка успешного создания заявки с исполнителем")
    @Description("Заполняем поля валидными значениями, дату и время выбираем в виджете, исполнителя" +
            " выбираем из списка. Проверяем по сохранению заявки переход на главный экран.")
    @Story("Позитивный сценарий")
    @Test
    public void createClaimSuccessTest() {
        claimCreateScreen.getTopicField().perform(replaceText(cyrillic(10).getValue()));
        claimCreateScreen.getExecutorField().perform(click());
        checkPopupWithText(activityScenarioRule, "Ivanov Ivan Ivanovich").perform(click());
        claimCreateScreen.getDateField().perform(click());
        checkPopupWithText(activityScenarioRule, "ОК").perform(click());
        claimCreateScreen.getTimeField().perform(click());
        checkPopupWithText(activityScenarioRule, "ОК").perform(click());
        claimCreateScreen.getDescriptionField().perform(replaceText(cyrillic(50).getValue()));
        claimCreateScreen.getSaveButton().perform(click());
        mainScreen.waitAllClaims();
        mainScreen.getNewsBlockHeader().check(matches(isDisplayed()));
        mainScreen.getClaimsBlockHeader().check(matches(isDisplayed()));
    }

    @DisplayName("Проверка успешного создания заявки с исполнителем не из списка")
    @Description("Заполняем поля валидными значениями, дату и время выбираем в виджете, вводим " +
            "вручную несуществующего исполнителя. Проверяем по сохранению заявки переход на главный " +
            "экран.")
    @Story("Негативный сценарий")
    @Test
    public void executorIgnoreTest() {
        claimCreateScreen.getTopicField().perform(replaceText(cyrillic(10).getValue()));
        claimCreateScreen.getExecutorField().perform(replaceText("notExisting"));
        claimCreateScreen.getDateField().perform(click());
        checkPopupWithText(activityScenarioRule, "ОК").perform(click());
        claimCreateScreen.getTimeField().perform(click());
        checkPopupWithText(activityScenarioRule, "ОК").perform(click());
        claimCreateScreen.getDescriptionField().perform(replaceText(cyrillic(50).getValue()));
        claimCreateScreen.getSaveButton().perform(click());
        mainScreen.waitAllClaims();
        mainScreen.getNewsBlockHeader().check(matches(isDisplayed()));
        mainScreen.getClaimsBlockHeader().check(matches(isDisplayed()));
    }

    @DisplayName("Проверка создания заявки с незаполненными полями")
    @Story("Негативный сценарий")
    @Test
    public void createClaimFailForEmptyFieldsTest() {
        claimCreateScreen.getSaveButton().perform(click());
        checkPopupWithText(activityScenarioRule, "Заполните пустые поля");
    }

    @DisplayName("Проверка отмены создания заявки")
    @Test
    public void createClaimCancelTest() {
        claimCreateScreen.getCancelButton().perform(click());
        checkPopupWithText(activityScenarioRule, "Изменения не будут сохранены." +
                " Вы действительно хотите выйти?");
        checkPopupWithText(activityScenarioRule, "OK").perform(click());
        mainScreen.waitAllClaims();
        mainScreen.getNewsBlockHeader().check(matches(isDisplayed()));
        mainScreen.getClaimsBlockHeader().check(matches(isDisplayed()));
    }
}
