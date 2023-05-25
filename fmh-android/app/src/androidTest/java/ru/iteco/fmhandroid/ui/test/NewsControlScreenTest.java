package ru.iteco.fmhandroid.ui.test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static ru.iteco.fmhandroid.ui.data.Datahelper.DateTime.getDate;
import static ru.iteco.fmhandroid.ui.data.Datahelper.checkPopupWithText;
import static ru.iteco.fmhandroid.ui.data.Datahelper.cyrillic;
import static ru.iteco.fmhandroid.ui.data.Datahelper.getRandomNewsCategory;
import static ru.iteco.fmhandroid.ui.data.Datahelper.validInfo;

import androidx.test.espresso.PerformException;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Flaky;
import io.qameta.allure.kotlin.Story;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.screen.AuthScreen;
import ru.iteco.fmhandroid.ui.screen.MainScreen;
import ru.iteco.fmhandroid.ui.screen.NewsControlScreen;
import ru.iteco.fmhandroid.ui.screen.NewsCreateScreen;
import ru.iteco.fmhandroid.ui.screen.NewsEditScreen;
import ru.iteco.fmhandroid.ui.screen.NewsFilterScreen;
import ru.iteco.fmhandroid.ui.screen.NewsScreen;

@RunWith(AllureAndroidJUnit4.class)
public class NewsControlScreenTest {
    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthScreen authScreen = new AuthScreen();
    MainScreen mainScreen = new MainScreen();
    NewsScreen newsScreen = new NewsScreen();
    NewsFilterScreen newsFilterScreen = new NewsFilterScreen();
    NewsControlScreen newsControlScreen = new NewsControlScreen();
    NewsCreateScreen newsCreateScreen = new NewsCreateScreen();
    NewsEditScreen newsEditScreen = new NewsEditScreen();

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
    }

    @DisplayName("Проверка отображения элементов экрана")
    @Test
    public void screenElementsTest() {
        newsControlScreen.checkNewsControlScreen();
    }

    @DisplayName("Проверка сортировки новостей")
    @Test
    public void NewsSortTest() throws InterruptedException {
        String title = cyrillic(10).getValue();
        newsControlScreen.getNewsCreateButton().perform(click());
        newsCreateScreen.waitCancelButton();
        newsCreateScreen.createNewsForChecks(getRandomNewsCategory(), title,
                getDate(0, 0, 0));
        newsControlScreen.waitNewsItem();
        Thread.sleep(1000);
        newsControlScreen.getNewsList().perform(actionOnItemAtPosition(0, click()))
                .check(matches(hasDescendant(withText(title))));
        newsControlScreen.getNewsSortButton().perform(click());
        Thread.sleep(200);
        newsControlScreen.getNewsList().perform(actionOnItemAtPosition(0, click()))
                .check(matches(not(hasDescendant(withText(title)))));
    }

    @DisplayName("Проверка перехода к экрану фильтрации новостей по кнопке")
    @Test
    public void goToNewsFilterScreenTest() {
        newsControlScreen.getNewsFilterButton().perform(click());
        newsFilterScreen.waitCancelButton();
        newsFilterScreen.getNewsFilterTitle().check(matches(isDisplayed()));
    }

    @DisplayName("Проверка перехода к экрану создания новости по кнопке")
    @Test
    public void goToCreateNewsScreenTest() {
        newsControlScreen.getNewsCreateButton().perform(click());
        newsCreateScreen.waitCancelButton();
        newsCreateScreen.getCreateHeader().check(matches(isDisplayed()));
        newsCreateScreen.getNewsHeader().check(matches(isDisplayed()));
    }

    @DisplayName("Проверка удаления созданной новости")
    @Story("Позитивный сценарий")
    @Flaky
    @Test
    public void deleteNewsItemTest() throws InterruptedException {
        String title = cyrillic(10).getValue();
        newsControlScreen.getNewsCreateButton().perform(click());
        newsCreateScreen.waitCancelButton();
        newsCreateScreen.createNewsForChecks(getRandomNewsCategory(), title,
                getDate(0, 0, 0));
        newsControlScreen.waitNewsItem();
        Thread.sleep(1000);
        newsControlScreen.getNewsList().perform(actionOnItemAtPosition(0, click()))
                .check(matches(hasDescendant(withText(title))));
        newsControlScreen.getNewsItemDeleteButton().perform(click());
        checkPopupWithText(activityScenarioRule,
                "Вы уверены, что хотите безвозвратно удалить документ? " +
                        "Данные изменения нельзя будет отменить в будущем.");
        checkPopupWithText(activityScenarioRule, "OK").perform(click());
        newsControlScreen.waitNewsItem();
        Thread.sleep(1000);
        newsControlScreen.getNewsItemTitle().check(matches(not(withText(title))));
    }

    @DisplayName("Проверка перехода к экрану редактирования новости по кнопке")
    @Test
    public void goToEditNewsItemScreenTest() {
        newsControlScreen.getNewsItemEditButton().perform(click());
        newsEditScreen.waitCancelButton();
        newsEditScreen.getEditHeader().check(matches(isDisplayed()));
        newsEditScreen.getNewsHeader().check(matches(isDisplayed()));
    }

    @DisplayName("Проверка разворачивания новости по нажатию на новость")
    @Test
    public void expandNewsItemTest() throws InterruptedException {
        newsControlScreen.getNewsList().perform(actionOnItemAtPosition(0, click()));
        Thread.sleep(200);
        newsControlScreen.getNewsItemDescription().check(matches(withEffectiveVisibility(VISIBLE)));
    }
}