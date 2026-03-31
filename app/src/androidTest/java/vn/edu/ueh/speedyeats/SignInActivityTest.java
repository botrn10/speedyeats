package vn.edu.ueh.speedyeats;
import vn.edu.ueh.speedyeats.View.SignInActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import android.content.pm.ActivityInfo;

import androidx.test.espresso.intent.Intents;

import androidx.test.espresso.util.TreeIterables;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import org.hamcrest.Matcher;
import android.view.View;

import vn.edu.ueh.speedyeats.View.SignInActivity;
import vn.edu.ueh.speedyeats.View.SignUpActivity;

@RunWith(AndroidJUnit4.class)

public class SignInActivityTest {

    @Rule
    public ActivityScenarioRule<SignInActivity> activityRule =
            new ActivityScenarioRule<>(SignInActivity.class);

    // Test UI hiển thị
    @Test
    public void testUIVisible() {

        onView(withId(R.id.edt_email_user))
                .check(matches(isDisplayed()));

        onView(withId(R.id.edt_matkhau_user))
                .check(matches(isDisplayed()));

        onView(withId(R.id.btn_dangnhap))
                .check(matches(isDisplayed()));

        onView(withId(R.id.btn_dangky))
                .check(matches(isDisplayed()));

        onView(withId(R.id.tv_forgot_password))
                .check(matches(isDisplayed()));
    }


    @Test
    public void testOpenRegisterActivity() {

        Intents.init();

        onView(withId(R.id.btn_dangky))
                .perform(click());

        intended(hasComponent(SignUpActivity.class.getName()));

        Intents.release();
    }

    @Test
    public void testTypingInput() {

        onView(withId(R.id.edt_email_user))
                .perform(typeText("test@gmail.com"), closeSoftKeyboard());

        onView(withId(R.id.edt_matkhau_user))
                .perform(typeText("123456"), closeSoftKeyboard());

        onView(withId(R.id.edt_email_user))
                .check(matches(withText("test@gmail.com")));

        onView(withId(R.id.edt_matkhau_user))
                .check(matches(withText("123456")));
    }

    @Test
    public void testClickForgotPassword() {

        onView(withId(R.id.tv_forgot_password))
                .perform(click());

    }

    @Test
    public void testClickLoginButton() {

        onView(withId(R.id.btn_dangnhap))
                .perform(click());
    }

    @Test
    public void testRotateScreen() throws InterruptedException {

        activityRule.getScenario().onActivity(activity -> {
            activity.setRequestedOrientation(
                    ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        });

        Thread.sleep(1000);

        onView(withId(R.id.edt_email_user))
                .check(matches(isDisplayed()));
    }


    public static ViewAction waitFor(final int viewId, final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Wait for view with id " + viewId;
            }

            @Override
            public void perform(UiController uiController, View view) {
                long startTime = System.currentTimeMillis();
                long endTime = startTime + millis;

                do {
                    for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                        if (child.getId() == viewId && child.isShown()) {
                            return;
                        }
                    }
                    uiController.loopMainThreadForAtLeast(100);
                } while (System.currentTimeMillis() < endTime);

                throw new AssertionError("View not found: " + viewId);
            }
        };
    }

//    @Test
//    public void testProgressDialogShow() {
//
//        onView(withId(R.id.btn_dangnhap))
//                .perform(click());
//
//        onView(withText("Loading"))
//                .check(matches(isDisplayed()));
//    }
}