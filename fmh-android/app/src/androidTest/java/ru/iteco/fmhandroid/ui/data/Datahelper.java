package ru.iteco.fmhandroid.ui.data;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import android.app.Activity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

import androidx.core.widget.NestedScrollView;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ScrollToAction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.espresso.util.TreeIterables;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.hamcrest.BaseMatcher;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

public class Datahelper {

    private Datahelper() {
    }

    public static class AuthInfo {
        private String login;
        private String password;

        public AuthInfo(String login, String password) {
            this.login = login;
            this.password = password;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }
    }

    public static AuthInfo validInfo() {
        return new AuthInfo("login2", "password2");
    }

    public static AuthInfo invalidInfo() {
        return new AuthInfo("log", "pass");
    }

    public static String getRandomString(String[] arr, int length) {
        Random random = new Random();
        String randomString = arr[random.nextInt(arr.length)];
        for (int i = 1; i < length; i++) {
            randomString = randomString.concat(arr[random.nextInt(arr.length)]);
        }
        return randomString;
    }

    public static String getRandomNewsCategory() {
        String[] category = {
                "Объявление",
                "День рождения",
                "Зарплата",
                "Профсоюз",
                "Праздник",
                "Массаж",
                "Благодарность",
                "Нужна помощь"
        };
        return getRandomString(category, 1);
    }

    public static class StringValue {
        private String value;

        public StringValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static StringValue cyrillic(int length) {
        String[] cyr = {
                "А", "Б", "В", "Г", "Д", "Е", "Ё", "Ж", "З", "И", "Й", "К", "Л", "М", "Н", "О", "П",
                "Р", "С", "Т", "У", "Ф", "Х", "Ц", "Ч", "Ш", "Щ", "Ъ", "Ы", "Ь", "Э", "Ю", "Я", "а",
                "б", "в", "г", "д", "е", "ё", "ж", "з", "и", "й", "к", "л", "м", "н", "о", "п", "р",
                "с", "т", "у", "ф", "х", "ц", "ч", "ш", "щ", "ъ", "ы", "ь", "э", "ю", "я"
        };
        String sample = getRandomString(cyr, length);
        return new StringValue(sample);
    }

    public static StringValue latin(int length) {
        String[] lat = {
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
                "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y",
                "z"
        };
        String sample = getRandomString(lat, length);
        return new StringValue(sample);
    }

    public static StringValue numeric(int length) {
        String[] num = {
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"
        };
        String sample = getRandomString(num, length);
        return new StringValue(sample);
    }

    public static StringValue special(int length) {
        String[] spec = {
                "@", "#", "$", "_", "&", "-", "+", "(", ")", "/", "*", "\"", "'", ":", ";", "!",
                "?", "~", "`", "|", "=", "{", "}", "\\", "[", "]", "№", "%", "^", "<", ">",
                ",", "."
        };
        String sample = getRandomString(spec, length);
        return new StringValue(sample);
    }

    public static StringValue whitespace(int length) {
        String sample = " ";
        for (int i = 1; i < length; i++) {
            sample = sample.concat(" ");
        }
        return new StringValue(sample);
    }

    public static StringValue emoji() {
        return new StringValue("\uD83D\uDE01\uD83D\uDE02\uD83E\uDD23\uD83D\uDE03\uD83D\uDE04");
    }

    public static StringValue hieroglyphic() {
        return new StringValue("中文範例");
    }

    public static StringValue arabic() {
        return new StringValue("عينة من النص باللغة العربية");
    }

    public static class DateTime {
        public static String getDate(int addYears, int addMonths, int addDays) {
            return LocalDate.now().plusYears(addYears).plusMonths(addMonths).plusDays(addDays)
                    .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        }

        public static String getTime(int addHours, int addMinutes) {
            return LocalTime.now().plusHours(addHours).plusMinutes(addMinutes)
                    .format(DateTimeFormatter.ofPattern("HH:mm"));
        }
    }

    public static Matcher<View> getElementMatchAtPosition(final Matcher<View> matcher,
                                                          final int position) {
        return new BaseMatcher<View>() {
            int counter = 0;
            @Override
            public boolean matches(final Object item) {
                if (matcher.matches(item)) {
                    if(counter == position) {
                        counter++;
                        return true;
                    }
                    counter++;
                }
                return false;
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("Element at hierarchy position "+position);
            }
        };
    }

    public static <T extends Activity> T getActivity(ActivityScenarioRule<T> activityScenarioRule) {
        AtomicReference<T> activityRef = new AtomicReference<>();
        activityScenarioRule.getScenario().onActivity(activityRef::set);
        return activityRef.get();
    }

    public static ViewInteraction checkToast(View decorView, String text) {
        return onView(withText(text)).inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    public static ViewInteraction checkPopupWithText(ActivityScenarioRule activityScenarioRule,
                                                     String text) {
        return onView(withText(text)).inRoot(withDecorView(not(is(getActivity(activityScenarioRule)
                .getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    public static ViewInteraction checkPopupWithHint(ActivityScenarioRule activityScenarioRule,
                                                    String hint) {
        return onView(withHint(hint)).inRoot(withDecorView(not(is(getActivity(activityScenarioRule)
                .getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    public static ViewAction customScrollTo() {

        ViewAction customScrollTo = new ViewAction() {

            @Override
            public Matcher<View> getConstraints() {
                return CoreMatchers.allOf(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                        isDescendantOfA(anyOf(
                                isAssignableFrom(ScrollView.class),
                                isAssignableFrom(HorizontalScrollView.class),
                                isAssignableFrom(NestedScrollView.class)))
                );
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public void perform(UiController uiController, View view) {
                new ScrollToAction().perform(uiController, view);
            }
        };
        return customScrollTo;
    }

    public static ViewAction waitForView(int viewId, long timeout) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "wait up to " + timeout + " milliseconds for the viewId" + viewId +
                        " view to become visible";
            }

            @Override
            public void perform(UiController uiController, View view) {
                long endTime = System.currentTimeMillis() + timeout;

                do {
                    for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                        if (withId(viewId).matches(child) && child.isShown()) {
                            return;
                        }
                    }

                    uiController.loopMainThreadForAtLeast(50);
                }
                while (System.currentTimeMillis() < endTime);

                throw new PerformException.Builder()
                        .withActionDescription(this.getDescription())
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(new TimeoutException("Waited " + timeout + " milliseconds"))
                        .build();
            }
        };
    }
}
