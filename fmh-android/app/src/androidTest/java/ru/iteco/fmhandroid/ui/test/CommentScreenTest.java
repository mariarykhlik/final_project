package ru.iteco.fmhandroid.ui.test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static ru.iteco.fmhandroid.ui.data.Datahelper.checkToast;
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
import io.qameta.allure.kotlin.Story;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.screen.AuthScreen;
import ru.iteco.fmhandroid.ui.screen.ClaimScreen;
import ru.iteco.fmhandroid.ui.screen.CommentScreen;
import ru.iteco.fmhandroid.ui.screen.MainScreen;

@RunWith(AllureAndroidJUnit4.class)
public class CommentScreenTest {
    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);


    View decorView;
    AuthScreen authScreen = new AuthScreen();
    MainScreen mainScreen = new MainScreen();
    ClaimScreen claimScreen = new ClaimScreen();
    CommentScreen commentScreen = new CommentScreen();

    @Before
    public void setUp() {
        activityScenarioRule.getScenario()
                .onActivity(activity -> decorView = activity.getWindow().getDecorView());
    }

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
        mainScreen.getClaimsList().perform(actionOnItemAtPosition(0, click()));
        claimScreen.waitClaimInfo();
    }

    @DisplayName("Проверка отображения элементов экрана")
    @Test
    public void screenElementsTest() {
        claimScreen.getCommentAddButton().perform(customScrollTo(), click());
        commentScreen.waitCancelButton();
        commentScreen.checkCommentScreen();
    }

    @DisplayName("Проверка успешного добавления комментария")
    @Story("Позитивный сценарий")
    @Test
    public void addCommentSuccessTest() throws InterruptedException {
        String comment = cyrillic(10).getValue();
        claimScreen.getCommentAddButton().perform(customScrollTo(), click());
        commentScreen.waitCancelButton();
        commentScreen.getCommentField().perform(replaceText(comment));
        commentScreen.getSaveButton().perform(click());
        claimScreen.waitClaimInfo();
        Thread.sleep(600);
        claimScreen.getCommentsList().check(matches(hasDescendant(withText(comment))));
    }

    @DisplayName("Проверка добавления пустого комментария")
    @Story("Негативный сценарий")
    @Test
    public void addEmptyCommentFailTest() {
        claimScreen.getCommentAddButton().perform(customScrollTo(), click());
        commentScreen.waitCancelButton();
        commentScreen.getSaveButton().perform(click());
        checkToast(decorView,"Поле не может быть пустым.");
    }

    @DisplayName("Проверка отмены добавления комментария")
    @Test
    public void commentAddCancelTest() throws InterruptedException {
        String comment = cyrillic(10).getValue();
        claimScreen.getCommentAddButton().perform(customScrollTo(), click());
        commentScreen.waitCancelButton();
        commentScreen.getCommentField().perform(replaceText(comment));
        commentScreen.getCancelButton().perform(click());
        claimScreen.waitClaimInfo();
        Thread.sleep(1000);
        claimScreen.getCommentsList().check(matches(not(hasDescendant(withText(comment)))));
    }

    @DisplayName("Проверка успешного редактирования комментария")
    @Story("Позитивный сценарий")
    @Test
    public void editCommentSuccessTest() throws InterruptedException {
        claimScreen.getCommentAddButton().perform(customScrollTo(), click());
        commentScreen.waitCancelButton();
        commentScreen.getCommentField().perform(replaceText(cyrillic(10).getValue()));
        commentScreen.getSaveButton().perform(click());
        claimScreen.waitClaimInfo();
        Thread.sleep(600);
        claimScreen.getCommentEditButton().perform(click());
        commentScreen.getCommentField().perform(replaceText("edited"));
        commentScreen.getSaveButton().perform(click());
        claimScreen.waitClaimInfo();
        claimScreen.getCommentsList().check(matches(hasDescendant(withText("edited"))));
    }
}
