package ru.iteco.fmhandroid.ui.screen;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static ru.iteco.fmhandroid.ui.data.Datahelper.getElementMatchAtPosition;
import static ru.iteco.fmhandroid.ui.data.Datahelper.waitForView;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class MainScreen {
    private ViewInteraction newsBlock = onView(withId(R.id
            .container_list_news_include_on_fragment_main));
    private ViewInteraction newsBlockHeader = onView(withText("Новости"));
    private ViewInteraction collapseNewsButton = onView(allOf(withId(R.id.expand_material_button),
            withParent(withParent(withId(R.id.container_list_news_include_on_fragment_main)))));
    private ViewInteraction allNewsButton = onView(withId(R.id.all_news_text_view));
    private ViewInteraction newsList = onView(withId(R.id.news_list_recycler_view));

    private ViewInteraction categoryIcon = onView(getElementMatchAtPosition(
            withId(R.id.category_icon_image_view), 0));
    private ViewInteraction newsItemTitle = onView(getElementMatchAtPosition(
            withId(R.id.news_item_title_text_view), 0));
    private ViewInteraction expandNewsItemButton = onView(getElementMatchAtPosition(
            withId(R.id.view_news_item_image_view), 0));
    private ViewInteraction newsItemDate = onView(getElementMatchAtPosition(
            withId(R.id.news_item_date_text_view), 0));
    private ViewInteraction newsItemDescription = onView(getElementMatchAtPosition(
            withId(R.id.news_item_description_text_view), 0));

    private ViewInteraction claimsBlock = onView(withId(R.id
            .container_list_claim_include_on_fragment_main));
    private ViewInteraction claimsBlockHeader = onView(withText("Заявки"));
    private ViewInteraction claimCreateButton = onView(withId(R.id.add_new_claim_material_button));
    private ViewInteraction collapseClaimsButton = onView(allOf(withId(R.id.expand_material_button),
            withParent(withParent(withId(R.id.container_list_claim_include_on_fragment_main)))));
    private ViewInteraction allClaimsButton = onView(withId(R.id.all_claims_text_view));
    private ViewInteraction claimsList = onView(withId(R.id.claim_list_recycler_view));

    private ViewInteraction climeTopicTitle = onView(getElementMatchAtPosition(
            withId(R.id.title_material_text_view), 0));
    private ViewInteraction climeTopic = onView(getElementMatchAtPosition(
            withId(R.id.description_material_text_view), 0));
    private ViewInteraction executorTitle = onView(getElementMatchAtPosition(
            withId(R.id.executor_name_label_material_text_view), 0));
    private ViewInteraction executor = onView(getElementMatchAtPosition(
            withId(R.id.executor_name_material_text_view), 0));
    private ViewInteraction planDateTitle = onView(getElementMatchAtPosition(
            withId(R.id.plan_date_label_material_text_view), 0));
    private ViewInteraction planDate = onView(getElementMatchAtPosition(
            withId(R.id.plan_date_material_text_view), 0));
    private ViewInteraction planTime = onView(getElementMatchAtPosition(
            withId(R.id.plan_time_material_text_view), 0));

    public ViewInteraction getCategoryIcon() {
        return categoryIcon;
    }

    public ViewInteraction getNewsItemTitle() {
        return newsItemTitle;
    }

    public ViewInteraction getExpandNewsItemButton() {
        return expandNewsItemButton;
    }

    public ViewInteraction getNewsItemDate() {
        return newsItemDate;
    }

    public ViewInteraction getNewsItemDescription() {
        return newsItemDescription;
    }

    public ViewInteraction getNewsBlock() {
        return newsBlock;
    }

    public ViewInteraction getNewsBlockHeader() {
        return newsBlockHeader;
    }

    public ViewInteraction getCollapseNewsButton() {
        return collapseNewsButton;
    }

    public ViewInteraction getAllNewsButton() {
        return allNewsButton;
    }

    public ViewInteraction getNewsList() {
        return newsList;
    }

    public ViewInteraction getClaimsBlock() {
        return claimsBlock;
    }

    public ViewInteraction getClaimsBlockHeader() {
        return claimsBlockHeader;
    }

    public ViewInteraction getClaimCreateButton() {
        return claimCreateButton;
    }

    public ViewInteraction getCollapseClaimsButton() {
        return collapseClaimsButton;
    }

    public ViewInteraction getAllClaimsButton() {
        return allClaimsButton;
    }

    public ViewInteraction getClaimsList() {
        return claimsList;
    }

    public ViewInteraction getClimeTopicTitle() {
        return climeTopicTitle;
    }

    public ViewInteraction getClimeTopic() {
        return climeTopic;
    }

    public ViewInteraction getExecutorTitle() {
        return executorTitle;
    }

    public ViewInteraction getExecutor() {
        return executor;
    }

    public ViewInteraction getPlanDateTitle() {
        return planDateTitle;
    }

    public ViewInteraction getPlanDate() {
        return planDate;
    }

    public ViewInteraction getPlanTime() {
        return planTime;
    }

    public void waitClaims() {
        onView(isRoot()).perform(waitForView(R.id.plan_time_material_text_view, 10000));
    }

    public void waitAllNews() {
        onView(isRoot()).perform(waitForView(R.id.all_news_text_view, 10000));
    }

    public void waitAllClaims() {
        onView(isRoot()).perform(waitForView(R.id.all_claims_text_view, 10000));
    }

    public void checkNewsItem() {
        getCategoryIcon().check(matches(isDisplayed()));
        getNewsItemTitle().check(matches(isDisplayed()));
        getExpandNewsItemButton().check(matches(isDisplayed()));
        getNewsItemDate().check(matches(isDisplayed()));
    }

    public void checkClaimsItem() {
        getClimeTopicTitle().check(matches(isDisplayed()));
        getClimeTopic().check(matches(isDisplayed()));
        getExecutorTitle().check(matches(isDisplayed()));
        getExecutor().check(matches(isDisplayed()));
        getPlanDateTitle().check(matches(isDisplayed()));
        getPlanDate().check(matches(isDisplayed()));
        getPlanTime().check(matches(isDisplayed()));
    }

    public void checkMainScreen() {
        AppBarFragment appBarFragment = new AppBarFragment();

        appBarFragment.checkAppBar();
        getNewsBlock().check(matches(isDisplayed()));
        getNewsBlockHeader().check(matches(isDisplayed()));
        getCollapseNewsButton().check(matches(isDisplayed()));
        getAllNewsButton().check(matches(isDisplayed()));
        getNewsList().check(matches(isDisplayed()));
        getNewsList().check(matches(hasChildCount(3)));
        checkNewsItem();
        getClaimsBlock().check(matches(isDisplayed()));
        getClaimsBlockHeader().check(matches(isDisplayed()));
        getClaimCreateButton().check(matches(isDisplayed()));
        getCollapseClaimsButton().check(matches(isDisplayed()));
        getAllClaimsButton().check(matches(isDisplayed()));
        getClaimsList().check(matches(isDisplayed()));
        getClaimsList().check(matches(hasChildCount(6)));
        checkClaimsItem();
    }
}
