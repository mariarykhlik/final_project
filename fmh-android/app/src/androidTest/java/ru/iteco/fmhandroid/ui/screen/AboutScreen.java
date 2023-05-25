package ru.iteco.fmhandroid.ui.screen;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static ru.iteco.fmhandroid.ui.data.Datahelper.waitForView;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class AboutScreen {
    private ViewInteraction versionTitle = onView(withId(R.id.about_version_title_text_view));
    private ViewInteraction versionInfo = onView(withId(R.id.about_version_value_text_view));
    private ViewInteraction policyTitle = onView((withId(R.id.about_privacy_policy_label_text_view)));
    private ViewInteraction policyLink = onView((withId(R.id.about_privacy_policy_value_text_view)));
    private ViewInteraction termsTitle = onView((withId(R.id.about_terms_of_use_label_text_view)));
    private ViewInteraction termsLink = onView((withId(R.id.about_terms_of_use_value_text_view)));
    private ViewInteraction company = onView(withId(R.id.about_company_info_label_text_view));
    private ViewInteraction backButton = onView(withId(R.id.about_back_image_button));
    private ViewInteraction trademark = onView(withId(R.id.trademark_image_view));

    public ViewInteraction getVersionTitle() {
        return versionTitle;
    }

    public ViewInteraction getVersionInfo() {
        return versionInfo;
    }

    public ViewInteraction getPolicyTitle() {
        return policyTitle;
    }

    public ViewInteraction getPolicyLink() {
        return policyLink;
    }

    public ViewInteraction getTermsTitle() {
        return termsTitle;
    }

    public ViewInteraction getTermsLink() {
        return termsLink;
    }

    public ViewInteraction getCompany() {
        return company;
    }

    public ViewInteraction getBackButton() {
        return backButton;
    }

    public ViewInteraction getTrademark() {
        return trademark;
    }

    public void waitTrademark() {
        onView(isRoot()).perform(waitForView(R.id.trademark_image_view, 10000));
    }

    public void checkAboutScreen() {
        getVersionTitle().check(matches(isDisplayed()));
        getVersionInfo().check(matches(isDisplayed()));
        getPolicyTitle().check(matches(isDisplayed()));
        getPolicyLink().check(matches(isDisplayed()));
        getTermsTitle().check(matches(isDisplayed()));
        getTermsLink().check(matches(isDisplayed()));
        getCompany().check(matches(isDisplayed()));
        getBackButton().check(matches(isDisplayed()));
        getTrademark().check(matches(isDisplayed()));
    }
}
