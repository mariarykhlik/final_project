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
public class NewsScreenTest {
    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthScreen authScreen = new AuthScreen();
    MainScreen mainScreen = new MainScreen();
    AppBarFragment appBarFragment = new AppBarFragment();
    NewsScreen newsScreen = new NewsScreen();
    NewsCreateScreen newsCreateScreen = new NewsCreateScreen();
    NewsFilterScreen newsFilterScreen = new NewsFilterScreen();
    NewsControlScreen newsControlScreen = new NewsControlScreen();

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
        newsScreen.checkNewsScreen();
    }

    @DisplayName("Проверка разворачивания новости по нажатию на новость")
    @Test
    public void expandNewsItemTest() throws InterruptedException {
        newsScreen.getNewsList().perform(actionOnItemAtPosition(0, click()));
        Thread.sleep(200);
        newsScreen.getNewsItemDescription().check(matches(withEffectiveVisibility(VISIBLE)));
    }

    @DisplayName("Проверка перехода к экрану управления новостями по кнопке")
    @Test
    public void goToNewsControlScreenTest() {
        newsScreen.getNewsEditButton().perform(click());
        newsControlScreen.waitNewsCreateButton();
        newsControlScreen.getControlPanelTitle().check(matches(isDisplayed()));
    }

    @DisplayName("Проверка перехода к экрану фильтрации новостей по кнопке")
    @Test
    public void goToNewsFilterScreenTest() {
        newsScreen.getNewsFilterButton().perform(click());
        newsFilterScreen.waitCancelButton();
        newsFilterScreen.getNewsFilterTitle().check(matches(isDisplayed()));
    }

    @DisplayName("Проверка сортировки новостей")
    @Test
    public void NewsSortTest() throws InterruptedException {
        String title = cyrillic(10).getValue();
        newsScreen.getNewsEditButton().perform(click());
        newsControlScreen.waitNewsCreateButton();
        newsControlScreen.getNewsCreateButton().perform(click());
        newsCreateScreen.waitCancelButton();
        newsCreateScreen.createNewsForChecks(getRandomNewsCategory(), title,
                getDate(0, 0, 0));
        newsControlScreen.waitNewsCreateButton();
        appBarFragment.navigateToNews();
        newsScreen.waitNews();
        Thread.sleep(1000);
        newsScreen.getNewsList().perform(actionOnItemAtPosition(0, click()))
                .check(matches(hasDescendant(withText(title))));
        newsScreen.getNewsSortButton().perform(click());
        Thread.sleep(200);
        newsScreen.getNewsList().perform(actionOnItemAtPosition(0, click()))
                .check(matches(not(hasDescendant(withText(title)))));
    }
}
