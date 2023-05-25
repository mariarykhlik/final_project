package ru.iteco.fmhandroid.ui.test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static ru.iteco.fmhandroid.ui.data.Datahelper.checkToast;
import static ru.iteco.fmhandroid.ui.data.Datahelper.invalidInfo;
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
import ru.iteco.fmhandroid.ui.screen.AppBarFragment;
import ru.iteco.fmhandroid.ui.screen.AuthScreen;
import ru.iteco.fmhandroid.ui.screen.MainScreen;

@RunWith(AllureAndroidJUnit4.class)
public class AuthScreenTest {
    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    View decorView;
    AuthScreen authScreen = new AuthScreen();
    MainScreen mainScreen = new MainScreen();
    AppBarFragment appBarFragment = new AppBarFragment();

    @Before
    public void setUp() {

        activityScenarioRule.getScenario()
                .onActivity(activity -> decorView = activity.getWindow().getDecorView());
    }


    @Before
    public void appSetUp() {
        try {
            authScreen.waitLoginButton();
        } catch (PerformException e) {
            mainScreen.waitAllNews();
            appBarFragment.logout();
            authScreen.waitLoginButton();
        }
    }

    @DisplayName("Проверка отображения элементов экрана")
    @Test
    public void screenElementsTest() {
        authScreen.checkAuthScreen();
    }

    @DisplayName("Проверка успешной авторизации")
    @Description("Вход под валидными данными пользователя с правами администратора")
    @Story("Позитивный сценарий")
    @Test
    public void authorizationSuccessTest() {
        authScreen.login(
                validInfo().getLogin(),
                validInfo().getPassword()
        );
        mainScreen.waitAllClaims();
        mainScreen.getNewsBlockHeader().check(matches(isDisplayed()));
        mainScreen.getClaimsBlockHeader().check(matches(isDisplayed()));
    }

    @DisplayName("Проверка авторизации без заполнения полей")
    @Story("Негативный сценарий")
    @Test
    public void authorizationFailForEmptyFieldsTest() {
        authScreen.getLoginButton().perform(click());
        checkToast(decorView, "Логин и пароль не могут быть пустыми");
    }

    @DisplayName("Проверка обязательности заполнения пароля")
    @Test
    public void authorizationFailForEmptyPasswordTest() {
        authScreen.login(
                validInfo().getLogin(),
                ""
        );
        checkToast(decorView, "Логин и пароль не могут быть пустыми");
    }

    @DisplayName("Проверка обязательности заполнения логина")
    @Test
    public void authorizationFailForEmptyLoginTest() {
        authScreen.login(
                "",
                validInfo().getPassword()
        );
        checkToast(decorView, "Логин и пароль не могут быть пустыми");
    }

    @DisplayName("Проверка авторизации с неправильными данными")
    @Story("Негативный сценарий")
    @Test
    public void authorizationErrorInfoTest() {
        authScreen.login(
                invalidInfo().getLogin(),
                invalidInfo().getPassword()
        );
        checkToast(decorView, "Неверный логин или пароль");
    }

    @DisplayName("Проверка авторизации с неправильным логином")
    @Story("Негативный сценарий")
    @Test
    public void authorizationErrorLoginTest() {
        authScreen.login(
                invalidInfo().getLogin(),
                validInfo().getPassword()
        );
        checkToast(decorView, "Неверный логин или пароль");
    }

    @DisplayName("Проверка авторизации с неправильным паролем")
    @Story("Негативный сценарий")
    @Test
    public void authorizationErrorPasswordTest() {
        authScreen.login(
                validInfo().getLogin(),
                invalidInfo().getPassword()
        );
        checkToast(decorView, "Неверный логин или пароль");
    }
}
