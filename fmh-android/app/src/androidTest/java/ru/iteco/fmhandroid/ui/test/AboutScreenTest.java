package ru.iteco.fmhandroid.ui.test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static ru.iteco.fmhandroid.ui.data.Datahelper.validInfo;

import android.content.Intent;

import androidx.test.espresso.PerformException;
import androidx.test.espresso.intent.Intents;
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
import ru.iteco.fmhandroid.ui.screen.MainScreen;

@RunWith(AllureAndroidJUnit4.class)
public class AboutScreenTest {
    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AboutScreen aboutScreen = new AboutScreen();
    AuthScreen authScreen = new AuthScreen();
    MainScreen mainScreen = new MainScreen();
    AppBarFragment appBarFragment = new AppBarFragment();

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
        appBarFragment.navigateToAbout();
        aboutScreen.waitTrademark();
    }

    @DisplayName("Проверка отображения элементов экрана")
    @Test
    public void screenElementsTest() {
        aboutScreen.checkAboutScreen();
    }

    @DisplayName("Проверка перехода к Политике конфиденциальности по ссылке с экрана")
    @Test
    public void PrivacyPolicyLinkTest() {
        Intents.init();
        aboutScreen.getPolicyLink().perform(click());
        intended(hasAction(Intent.ACTION_VIEW));
        intended(hasData("https://vhospice.org/#/privacy-policy"));
        Intents.release();
    }

    @DisplayName("Проверка перехода к Пользовалельскому соглашению по ссылке с экрана")
    @Test
    public void TermsOfUseLinkTest() {
        Intents.init();
        aboutScreen.getTermsLink().perform(click());
        intended(hasAction(Intent.ACTION_VIEW));
        intended(hasData("https://vhospice.org/#/terms-of-use"));
        Intents.release();
    }

    @DisplayName("Проверка возврата к предыдущему экрану по кнопке")
    @Test
    public void goBackTest() {
        aboutScreen.getBackButton().perform(click());
        mainScreen.waitAllClaims();
        mainScreen.getNewsBlockHeader().check(matches(isDisplayed()));
        mainScreen.getClaimsBlockHeader().check(matches(isDisplayed()));
    }
}
