package ru.iteco.fmhandroid.ui.test;

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
import ru.iteco.fmhandroid.ui.screen.NewsControlScreen;
import ru.iteco.fmhandroid.ui.screen.NewsCreateScreen;
import ru.iteco.fmhandroid.ui.screen.NewsFilterScreen;
import ru.iteco.fmhandroid.ui.screen.NewsScreen;

@RunWith(AllureAndroidJUnit4.class)
public class NewsFilterScreenTest {
    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthScreen authScreen = new AuthScreen();
    MainScreen mainScreen = new MainScreen();
    AppBarFragment appBarFragment = new AppBarFragment();
    NewsScreen newsScreen = new NewsScreen();
    NewsFilterScreen newsFilterScreen = new NewsFilterScreen();
    NewsControlScreen newsControlScreen = new NewsControlScreen();
    NewsCreateScreen newsCreateScreen = new NewsCreateScreen();

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
    }

    @DisplayName("Проверка отображения элементов экрана")
    @Test
    public void screenElementsTest() {
        newsScreen.getNewsFilterButton().perform(click());
        newsFilterScreen.waitCancelButton();
        newsFilterScreen.checkNewsFilterScreen();
        newsFilterScreen.checkCheckboxesForFilterFromNewsScreen();
        newsFilterScreen.getFilterButton().perform(click());
        newsScreen.waitNewsEditButton();
        newsScreen.getNewsEditButton().perform(click());
        newsControlScreen.waitNewsCreateButton();
        newsControlScreen.getNewsFilterButton().perform(click());
        newsFilterScreen.waitCancelButton();
        newsFilterScreen.checkNewsFilterScreen();
        newsFilterScreen.checkCheckboxesForFilterFromNewsControlScreen();
    }

    @DisplayName("Проверка фильтрации новостей по категории")
    @Description("Создаем новость, фильтруем список новостей по данной категории на экранах " +
            "новостей и управления новостями. Проверяем присутствие созданной новости на " +
            "соответствующем экране.")
    @Story("Позитивный сценарий")
    @Test
    public void filterNewsByCategoryTest() throws InterruptedException {
        String category = getRandomNewsCategory();
        String title = cyrillic(10).getValue();
        newsScreen.getNewsEditButton().perform(click());
        newsControlScreen.waitNewsCreateButton();
        newsControlScreen.getNewsCreateButton().perform(click());
        newsCreateScreen.waitCancelButton();
        newsCreateScreen.createNewsForChecks(category, title,
                getDate(0,0,0));
        newsControlScreen.waitNewsCreateButton();
        Thread.sleep(1000);
        newsControlScreen.getNewsFilterButton().perform(click());
        newsFilterScreen.waitCancelButton();
        newsFilterScreen.getCategoryField().perform(replaceText(category));
        newsFilterScreen.getFilterButton().perform(click());
        newsControlScreen.waitNewsCreateButton();
        Thread.sleep(1000);
        newsControlScreen.getNewsList().perform(actionOnItemAtPosition(0, click()))
                .check(matches(hasDescendant(withText(title))));
        appBarFragment.navigateToNews();
        newsScreen.waitNewsEditButton();
        newsScreen.getNewsFilterButton().perform(click());
        newsFilterScreen.waitCancelButton();
        newsFilterScreen.getCategoryField().perform(replaceText(category));
        newsFilterScreen.getFilterButton().perform(click());
        newsScreen.waitNewsEditButton();
        Thread.sleep(1000);
        newsScreen.getNewsList().perform(actionOnItemAtPosition(0, click()))
                .check(matches(hasDescendant(withText(title))));
    }

    @DisplayName("Проверка фильтрации новостей по дате публикации")
    @Description("Создаем новость, фильтруем список новостей по периоду даты публикации на экранах " +
            "новостей и управления новостями. Проверяем присутствие созданной новости на " +
            "соответствующем экране.")
    @Story("Позитивный сценарий")
    @Test
    public void filterNewsByPeriodTest() throws InterruptedException {
        String date = getDate(0,0,0);
        newsScreen.getNewsEditButton().perform(click());
        newsControlScreen.waitNewsCreateButton();
        newsControlScreen.getNewsCreateButton().perform(click());
        newsCreateScreen.waitCancelButton();
        newsCreateScreen.createNewsForChecks(getRandomNewsCategory(),
                cyrillic(10).getValue(), date);
        newsControlScreen.waitNewsCreateButton();
        Thread.sleep(1000);
        newsControlScreen.getNewsFilterButton().perform(click());
        newsFilterScreen.waitCancelButton();
        newsFilterScreen.getPeriodStartField().perform(replaceText(date));
        newsFilterScreen.getPeriodEndField().perform(replaceText(date));
        newsFilterScreen.getFilterButton().perform(click());
        newsControlScreen.waitNewsItem();
        Thread.sleep(1000);
        newsControlScreen.getNewsItemPublicationDate().check(matches(withText(date)));
        appBarFragment.navigateToNews();
        newsScreen.waitNewsEditButton();
        newsScreen.getNewsFilterButton().perform(click());
        newsFilterScreen.waitCancelButton();
        newsFilterScreen.getPeriodStartField().perform(replaceText(date));
        newsFilterScreen.getPeriodEndField().perform(replaceText(date));
        newsFilterScreen.getFilterButton().perform(click());
        newsScreen.waitNews();
        Thread.sleep(1000);
        newsScreen.getNewsItemDate().check(matches(withText(date)));
    }

    @DisplayName("Проверка фильтрации новостей при задании одной границы периода")
    @Description("Создаем новость, фильтруем список новостей на экранах новостей и управления " +
            "новостями по периоду даты публикации, задавая только одну границу. Проверяем ошибку " +
            "задания периода.")
    @Story("Негативный сценарий")
    @Test
    public void filterNewsFailForPeriodErrorTest() throws InterruptedException {
        String date = getDate(0,0,0);
        newsScreen.getNewsEditButton().perform(click());
        newsControlScreen.waitNewsCreateButton();
        newsControlScreen.getNewsCreateButton().perform(click());
        newsCreateScreen.waitCancelButton();
        newsCreateScreen.createNewsForChecks(getRandomNewsCategory(),
                cyrillic(10).getValue(), date);
        newsControlScreen.waitNewsCreateButton();
        Thread.sleep(1000);
        newsControlScreen.getNewsFilterButton().perform(click());
        newsFilterScreen.waitCancelButton();
        newsFilterScreen.getPeriodStartField().perform(replaceText(date));
        newsFilterScreen.getFilterButton().perform(click());
        checkPopupWithText(activityScenarioRule, "Неверно указан период");
        checkPopupWithText(activityScenarioRule, "OK").perform(click());
        newsFilterScreen.getCancelButton().perform(click());
        appBarFragment.navigateToNews();
        newsScreen.waitNewsEditButton();
        newsScreen.getNewsFilterButton().perform(click());
        newsFilterScreen.waitCancelButton();
        newsFilterScreen.getPeriodEndField().perform(replaceText(date));
        newsFilterScreen.getFilterButton().perform(click());
        checkPopupWithText(activityScenarioRule, "Неверно указан период");
    }

    @DisplayName("Проверка фильтрации новостей при задании несуществующей категории")
    @Description("Фильтруем список новостей на экранах новостей и управления новостями по введенной" +
            " вручную несуществующей категории. Проверяем игнорирование фильтра.")
    @Story("Негативный сценарий")
    @Test
    public void filterNewsIgnoreCustomCategoryTest() {
        String category = cyrillic(10).getValue();
        newsScreen.getNewsFilterButton().perform(click());
        newsFilterScreen.waitCancelButton();
        newsFilterScreen.getCategoryField().perform(replaceText(category));
        newsFilterScreen.getFilterButton().perform(click());
        newsScreen.waitNewsEditButton();
        newsScreen.getNewsBlockHeader().check(matches(isDisplayed()));
        newsScreen.getNewsEditButton().perform(click());
        newsControlScreen.waitNewsCreateButton();
        newsControlScreen.getNewsFilterButton().perform(click());
        newsFilterScreen.waitCancelButton();
        newsFilterScreen.getCategoryField().perform(replaceText(category));
        newsFilterScreen.getFilterButton().perform(click());
        newsControlScreen.waitNewsCreateButton();
        newsControlScreen.getControlPanelTitle().check(matches(isDisplayed()));
    }

    @DisplayName("Проверка фильтрации новостей по активности")
    @Story("Позитивный сценарий")
    @Test
    public void filterNewsByStateTest() throws InterruptedException {
        newsScreen.getNewsEditButton().perform(click());
        newsControlScreen.waitNewsCreateButton();
        newsControlScreen.getNewsFilterButton().perform(click());
        newsFilterScreen.waitCancelButton();
        newsFilterScreen.getCheckBoxActive().perform(click());
        newsFilterScreen.getFilterButton().perform(click());
        newsControlScreen.waitNewsItem();
        Thread.sleep(1000);
        newsControlScreen.getNewsList().check(matches(hasDescendant(withText("Не активна"))))
                .check(matches(isDisplayed()));
        newsControlScreen.getNewsFilterButton().perform(click());
        newsFilterScreen.waitCancelButton();
        newsFilterScreen.getCheckBoxInactive().perform(click());
        newsFilterScreen.getFilterButton().perform(click());
        newsControlScreen.waitNewsItem();
        Thread.sleep(1000);
        newsControlScreen.getNewsList().check(matches(hasDescendant(withText("Активна"))))
                .check(matches(isDisplayed()));
    }

    @DisplayName("Проверка отмены фильтрации новостей")
    @Test
    public void filterNewsCancelTest() {
        newsScreen.getNewsFilterButton().perform(click());
        newsFilterScreen.waitCancelButton();
        newsFilterScreen.getCancelButton().perform(click());
        newsScreen.waitNewsEditButton();
        newsScreen.getNewsBlockHeader().check(matches(isDisplayed()));
        newsScreen.getNewsEditButton().perform(click());
        newsControlScreen.waitNewsCreateButton();
        newsControlScreen.getNewsFilterButton().perform(click());
        newsFilterScreen.waitCancelButton();
        newsFilterScreen.getCancelButton().perform(click());
        newsControlScreen.waitNewsCreateButton();
        newsControlScreen.getControlPanelTitle().check(matches(isDisplayed()));
    }
}