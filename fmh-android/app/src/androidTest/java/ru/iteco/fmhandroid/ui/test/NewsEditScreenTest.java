package ru.iteco.fmhandroid.ui.test;

import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.data.Datahelper.DateTime.getDate;
import static ru.iteco.fmhandroid.ui.data.Datahelper.checkPopupWithText;
import static ru.iteco.fmhandroid.ui.data.Datahelper.cyrillic;
import static ru.iteco.fmhandroid.ui.data.Datahelper.getRandomNewsCategory;
import static ru.iteco.fmhandroid.ui.data.Datahelper.validInfo;

import android.view.View;

import androidx.test.espresso.PerformException;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import junit.framework.AssertionFailedError;

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
import ru.iteco.fmhandroid.ui.screen.MainScreen;
import ru.iteco.fmhandroid.ui.screen.NewsControlScreen;
import ru.iteco.fmhandroid.ui.screen.NewsEditScreen;
import ru.iteco.fmhandroid.ui.screen.NewsScreen;

@RunWith(AllureAndroidJUnit4.class)
public class NewsEditScreenTest {
    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    View decorView;
    AuthScreen authScreen = new AuthScreen();
    MainScreen mainScreen = new MainScreen();
    NewsScreen newsScreen = new NewsScreen();
    NewsControlScreen newsControlScreen = new NewsControlScreen();
    NewsEditScreen newsEditScreen = new NewsEditScreen();

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
            mainScreen.waitAllNews();
        } catch (PerformException e) {
            mainScreen.waitAllNews();
        }
        mainScreen.getAllNewsButton().perform(click());
        newsScreen.waitNewsEditButton();
        newsScreen.getNewsEditButton().perform(click());
        newsControlScreen.waitNewsItem();
        newsControlScreen.getNewsItemEditButton().perform(click());
        newsEditScreen.waitCancelButton();
    }

    @DisplayName("Проверка отображения элементов экрана")
    @Test
    public void screenElementsTest() {
        newsEditScreen.checkNewsEditScreen();
    }

    @DisplayName("Проверка успешного редактирования новости")
    @Description("Заменяем значения полей на другие валидные. Проверяем по сохранению изменение " +
            "данных и активности.")
    @Story("Позитивный сценарий")
    @Test
    public void editNewsSuccessTest() throws InterruptedException {
        String title = cyrillic(10).getValue();
        String date = getDate(0, 0, 0);
        String description = cyrillic(50).getValue();
        String state;
        String active = "Активна";
        String inactive = "Не активна";
        newsEditScreen.getCategoryField().perform(replaceText(getRandomNewsCategory()));
        newsEditScreen.getTitleField().perform(replaceText(title));
        newsEditScreen.getPublicationDateField().perform(click());
        checkPopupWithText(activityScenarioRule, "ОК").perform(click());
        newsEditScreen.getPublicationTimeField().perform(click());
        checkPopupWithText(activityScenarioRule, "ОК").perform(click());
        newsEditScreen.getDescriptionField().perform(replaceText(description));
        try {
            newsEditScreen.getActivationSwitcher().check(matches(withText(active)));
            state = inactive;
        } catch (AssertionFailedError e) {
            state = active;
        }
        newsEditScreen.getActivationSwitcher().perform(click());
        newsEditScreen.getSaveButton().perform(click());
        newsControlScreen.waitNewsItem();
        Thread.sleep(1000);
        newsControlScreen.getNewsList().perform(actionOnItemAtPosition(0, click()))
                .check(matches(hasDescendant(withText(title))))
                .check(matches(hasDescendant(withText(date))))
                .check(matches(hasDescendant(withText(description))))
                .check(matches(hasDescendant(withText(state))));
    }

    @DisplayName("Проверка сохранения новости с несуществующей категорией")
    @Description("Вводим несуществующую категорию вручную. Проверяем ошибку сохранения.")
    @Story("Негативный сценарий")
    @Test
    public void EditNewsFailForCustomCategoryTest() {
        newsEditScreen.getCategoryField().perform(replaceText(cyrillic(10).getValue()));
        newsEditScreen.getSaveButton().perform(click());
        checkPopupWithText(activityScenarioRule, "Сохранение не удалось. Попробуйте позднее.");
    }

    @DisplayName("Проверка сохранения новости с очищенными полями")
    @Story("Негативный сценарий")
    @Test
    public void editNewsFailForEmptyFieldsTest() {
        newsEditScreen.getCategoryField().perform(clearText());
        newsEditScreen.getTitleField().perform(clearText());
        newsEditScreen.getPublicationDateField().perform(clearText());
        newsEditScreen.getPublicationTimeField().perform(clearText());
        newsEditScreen.getDescriptionField().perform(clearText());
        newsEditScreen.getSaveButton().perform(click());
        checkPopupWithText(activityScenarioRule, "Заполните пустые поля");
    }

    @DisplayName("Проверка отмены редактирования новости")
    @Test
    public void createNewsCancelTest() {
        newsEditScreen.getCancelButton().perform(click());
        checkPopupWithText(activityScenarioRule, "Изменения не будут сохранены." +
                " Вы действительно хотите выйти?");
        checkPopupWithText(activityScenarioRule, "OK").perform(click());
        newsControlScreen.waitNewsCreateButton();
        newsControlScreen.getControlPanelTitle().check(matches(isDisplayed()));
    }
}