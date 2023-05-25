package ru.iteco.fmhandroid.ui.screen;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.data.Datahelper.getElementMatchAtPosition;
import static ru.iteco.fmhandroid.ui.data.Datahelper.waitForView;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.Datahelper;

public class ClaimScreen {
    private ViewInteraction topicTitle = onView(withId(R.id.title_label_text_view));
    private ViewInteraction topic = onView(withId(R.id.title_text_view));
    private ViewInteraction executorTitle = onView(withId(R.id.executor_name_label_text_view));
    private ViewInteraction executor = onView(withId(R.id.executor_name_text_view));
    private ViewInteraction planDateTitle = onView(withId(R.id.plane_date_label_text_view));
    private ViewInteraction planDate = onView(withId(R.id.plane_date_text_view));
    private ViewInteraction planTime = onView(withId(R.id.plan_time_text_view));
    private ViewInteraction claimState = onView(withId(R.id.status_label_text_view));
    private ViewInteraction description = onView(withId(R.id.description_text_view));
    private ViewInteraction authorTitle = onView(withId(R.id.author_label_text_view));
    private ViewInteraction author = onView(withId(R.id.author_name_text_view));
    private ViewInteraction createDateTitle = onView(withId(R.id.create_data_label_text_view));
    private ViewInteraction createDate = onView(withId(R.id.create_data_text_view));
    private ViewInteraction createTime = onView(withId(R.id.create_time_text_view));
    private ViewInteraction commentsList = onView(withId(R.id.claim_comments_list_recycler_view));

    private ViewInteraction comment = onView(getElementMatchAtPosition(
            withId(R.id.comment_description_text_view), 0));
    private ViewInteraction commentEditButton = onView(getElementMatchAtPosition(
            withId(R.id.edit_comment_image_button), 0));
    private ViewInteraction commentator = onView(getElementMatchAtPosition(
            withId(R.id.commentator_name_text_view), 0));
    private ViewInteraction commentDate = onView(getElementMatchAtPosition(
            withId(R.id.comment_date_text_view), 0));
    private ViewInteraction commentTime = onView(getElementMatchAtPosition(
            withId(R.id.comment_time_text_view), 0));

    private ViewInteraction commentField = onView(withText("Добавить комментарий"));
    private ViewInteraction commentAddButton = onView(withId(R.id.add_comment_image_button));
    private ViewInteraction goToClaimListButton = onView(withId(R.id.close_image_button));
    private ViewInteraction stateChangeButton = onView(withId(R.id.status_processing_image_button));
    private ViewInteraction claimEditButton = onView(withId(R.id.edit_processing_image_button));

    public ViewInteraction getTopicTitle() {
        return topicTitle;
    }

    public ViewInteraction getTopic() {
        return topic;
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

    public ViewInteraction getClaimState() {
        return claimState;
    }

    public ViewInteraction getDescription() {
        return description;
    }

    public ViewInteraction getAuthorTitle() {
        return authorTitle;
    }

    public ViewInteraction getAuthor() {
        return author;
    }

    public ViewInteraction getCreateDateTitle() {
        return createDateTitle;
    }

    public ViewInteraction getCreateDate() {
        return createDate;
    }

    public ViewInteraction getCreateTime() {
        return createTime;
    }

    public ViewInteraction getCommentsList() {
        return commentsList;
    }

    public ViewInteraction getComment() {
        return comment;
    }

    public ViewInteraction getCommentEditButton() {
        return commentEditButton;
    }

    public ViewInteraction getCommentator() {
        return commentator;
    }

    public ViewInteraction getCommentDate() {
        return commentDate;
    }

    public ViewInteraction getCommentTime() {
        return commentTime;
    }

    public ViewInteraction getCommentField() {
        return commentField;
    }

    public ViewInteraction getCommentAddButton() {
        return commentAddButton;
    }

    public ViewInteraction getGoToClaimListButton() {
        return goToClaimListButton;
    }

    public ViewInteraction getStateChangeButton() {
        return stateChangeButton;
    }

    public ViewInteraction getClaimEditButton() {
        return claimEditButton;
    }

    public void waitClaimInfo() {
        onView(isRoot()).perform(waitForView(R.id.create_time_text_view, 10000));
    }

    public void checkComment() {
        getComment().check(matches(isDisplayed()));
        getCommentEditButton().check(matches(isDisplayed()));
        getCommentator().check(matches(isDisplayed()));
        getCommentDate().check(matches(isDisplayed()));
        getCommentTime().check(matches(isDisplayed()));
    }

    public void checkClaimScreen() {
        AppBarFragment appBarFragment = new AppBarFragment();

        appBarFragment.checkAppBar();
        getTopicTitle().check(matches(isDisplayed()));
        getTopic().check(matches(isDisplayed()));
        getExecutorTitle().check(matches(isDisplayed()));
        getExecutor().check(matches(isDisplayed()));
        getPlanDateTitle().check(matches(isDisplayed()));
        getPlanDate().check(matches(isDisplayed()));
        getPlanTime().check(matches(isDisplayed()));
        getClaimState().check(matches(isDisplayed()));
        getDescription().check(matches(isDisplayed()));
        getAuthorTitle().check(matches(isDisplayed()));
        getAuthor().check(matches(isDisplayed()));
        getCreateDateTitle().check(matches(isDisplayed()));
        getCreateDate().check(matches(isDisplayed()));
        getCreateTime().check(matches(isDisplayed()));

        getCommentField().perform(Datahelper.customScrollTo()).check(matches(isDisplayed()));
        getCommentAddButton().perform(Datahelper.customScrollTo()).check(matches(isDisplayed()));
        getGoToClaimListButton().perform(Datahelper.customScrollTo()).check(matches(isDisplayed()));
        getStateChangeButton().perform(Datahelper.customScrollTo()).check(matches(isDisplayed()));
        getClaimEditButton().perform(Datahelper.customScrollTo()).check(matches(isDisplayed()));
    }
}
