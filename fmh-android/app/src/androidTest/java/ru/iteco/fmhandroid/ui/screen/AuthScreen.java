package ru.iteco.fmhandroid.ui.screen;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.data.Datahelper.waitForView;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class AuthScreen {
    private ViewInteraction authHeader = onView(withText("Авторизация"));
    private ViewInteraction loginField = onView(withHint("Логин"));
    private ViewInteraction passwordField = onView(withHint("Пароль"));
    private ViewInteraction loginButton = onView(withId(R.id.enter_button));

    public ViewInteraction getLoginField() {
        return loginField;
    }

    public ViewInteraction getPasswordField() {
        return passwordField;
    }

    public ViewInteraction getLoginButton() {
        return loginButton;
    }

    public ViewInteraction getAuthHeader() {
        return authHeader;
    }

    public void waitLoginButton() {
        onView(isRoot()).perform(waitForView(R.id.enter_button, 10000));
    }

    public void checkAuthScreen() {
        getAuthHeader().check(matches(isDisplayed()));
        getLoginField().check(matches(isDisplayed()));
        getPasswordField().check(matches(isDisplayed()));
        getLoginButton().check(matches(isDisplayed()));
    }

    public void checkLoginButton() {
        getLoginButton().check(matches(isDisplayed()));
    }

    public void login(String login, String password) {
        getLoginField().perform(replaceText(login), closeSoftKeyboard());
        getPasswordField().perform(replaceText(password), closeSoftKeyboard());
        getLoginButton().perform(click());
    }
}
