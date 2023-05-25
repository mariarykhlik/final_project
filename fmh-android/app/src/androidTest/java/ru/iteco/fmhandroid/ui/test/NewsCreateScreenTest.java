package ru.iteco.fmhandroid.ui.test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.data.Datahelper.checkPopupWithText;
import static ru.iteco.fmhandroid.ui.data.Datahelper.customScrollTo;
import static ru.iteco.fmhandroid.ui.data.Datahelper.cyrillic;
import static ru.iteco.fmhandroid.ui.data.Datahelper.getRandomNewsCategory;
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
import io.qameta.allure.kotlin.Flaky;
import io.qameta.allure.kotlin.Story;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.screen.AuthScreen;
import ru.iteco.fmhandroid.ui.screen.MainScreen;
import ru.iteco.fmhandroid.ui.screen.NewsControlScreen;
import ru.iteco.fmhandroid.ui.screen.NewsCreateScreen;
import ru.iteco.fmhandroid.ui.screen.NewsScreen;

@RunWith(AllureAndroidJUnit4.class)
public class NewsCreateScreenTest {
    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    View decorView;
    AuthScreen authScreen = new AuthScreen();
    MainScreen mainScreen = new MainScreen();
    NewsScreen newsScreen = new NewsScreen();
    NewsControlScreen newsControlScreen = new NewsControlScreen();
    NewsCreateScreen newsCreateScreen = new NewsCreateScreen();

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
        newsControlScreen.waitNewsCreateButton();
        newsControlScreen.getNewsCreateButton().perform(click());
        newsCreateScreen.waitCancelButton();
    }

    @DisplayName("Проверка отображения элементов экрана")
    @Test
    public void screenElementsTest() {
        newsCreateScreen.checkNewsCreateScreen();
    }

    @DisplayName("Проверка успешного создания новости")
    @Description("Заполняем поля валидными значениями, дату и время выбираем в виджете, категорию" +
            " выбираем из списка. Проверяем по сохранению новости переход на экран управления " +
            "новостями.")
    @Story("Позитивный сценарий")
    @Flaky
    @Test
    public void createNewsSuccessTest() throws InterruptedException {
        String title = cyrillic(10).getValue();
        newsCreateScreen.getCategoryField().perform(click());
        newsCreateScreen.getCategoryField().perform(closeSoftKeyboard());
        checkPopupWithText(activityScenarioRule, getRandomNewsCategory()).perform(click());
        newsCreateScreen.getTitleField().perform(replaceText(title));
        newsCreateScreen.getPublicationDateField().perform(click());
        checkPopupWithText(activityScenarioRule, "ОК").perform(click());
        newsCreateScreen.getPublicationTimeField().perform(click());
        checkPopupWithText(activityScenarioRule, "ОК").perform(click());
        newsCreateScreen.getDescriptionField().perform(replaceText(cyrillic(50).getValue()));
        newsCreateScreen.getSaveButton().perform(click());
        newsControlScreen.waitNewsItem();
        Thread.sleep(1000);
        newsControlScreen.getControlPanelTitle().check(matches(isDisplayed()));
        newsControlScreen.getNewsItemTitle().check(matches(withText(title)));
    }

    @DisplayName("Проверка создания новости с несуществующей категорией")
    @Description("Заполняем поля валидными значениями, дату и время выбираем в виджете, категорию" +
            "вводим несуществующую вручную. Проверяем ошибку сохранения.")
    @Story("Негативный сценарий")
    @Test
    public void createNewsFailForCustomCategoryTest() {
        String category = cyrillic(10).getValue();
        newsCreateScreen.getCategoryField().perform(replaceText(category));
        newsCreateScreen.getTitleField().perform(replaceText(category));
        newsCreateScreen.getPublicationDateField().perform(click());
        checkPopupWithText(activityScenarioRule, "ОК").perform(click());
        newsCreateScreen.getPublicationTimeField().perform(click());
        checkPopupWithText(activityScenarioRule, "ОК").perform(click());
        newsCreateScreen.getDescriptionField().perform(replaceText(cyrillic(50).getValue()));
        newsCreateScreen.getSaveButton().perform(click());
        checkPopupWithText(activityScenarioRule, "Сохранение не удалось. Попробуйте позднее.");
    }

    @DisplayName("Проверка создания новости с незаполненными полями")
    @Story("Негативный сценарий")
    @Test
    public void createNewsFailForEmptyFieldsTest() {
        newsCreateScreen.getSaveButton().perform(click());
        checkPopupWithText(activityScenarioRule, "Заполните пустые поля");
    }

    @DisplayName("Проверка отмены создания новости")
    @Test
    public void createNewsCancelTest() {
        newsCreateScreen.getCancelButton().perform(click());
        checkPopupWithText(activityScenarioRule, "Изменения не будут сохранены." +
                " Вы действительно хотите выйти?");
        checkPopupWithText(activityScenarioRule, "OK").perform(click());
        newsControlScreen.waitNewsCreateButton();
        newsControlScreen.getControlPanelTitle().check(matches(isDisplayed()));
    }
}
