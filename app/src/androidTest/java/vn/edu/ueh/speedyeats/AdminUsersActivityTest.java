package vn.edu.ueh.speedyeats;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static java.util.regex.Pattern.matches;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import vn.edu.ueh.speedyeats.View.Admin.AdminUsersActivity;

@RunWith(AndroidJUnit4.class)
public class AdminUsersActivityTest {

    @Rule
    public ActivityScenarioRule<AdminUsersActivity> rule =
            new ActivityScenarioRule<>(AdminUsersActivity.class);

    @Test
    public void testUIComponentsDisplayed() {
        onView(withId(R.id.img_back_users)).check(matches(isDisplayed()));
        onView(withId(R.id.rcv_users)).check(matches(isDisplayed()));
        onView(withId(R.id.image_add_users)).check(matches(isDisplayed()));
    }

    @Test
    public void testBackButton() {
        onView(withId(R.id.img_back_users)).perform(click());
    }

    @Test
    public void testOpenAddUserDialog() {
        onView(withId(R.id.image_add_users)).perform(click());

        onView(withId(R.id.edt_add_email_users))
                .check(matches(isDisplayed()));

        onView(withId(R.id.btn_add_users))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testClickUserItem() {

        rule.getScenario().onActivity(activity -> {
            activity.getDataUser(
                    "1",
                    "test@gmail.com",
                    "Test User",
                    "HCM",
                    "0123",
                    "01/01/2000",
                    "Nam",
                    ""
            );
        });

        onView(withId(R.id.rcv_users))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.tv_email_users))
                .check(matches(isDisplayed()));
    }
}
