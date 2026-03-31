package vn.edu.ueh.speedyeats;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;

import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static vn.edu.ueh.speedyeats.SignInActivityTest.waitFor;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ProfileFragmentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    private void login() {
        onView(withId(R.id.edt_email_user))
                .perform(typeText("nguyen@gmail.com"), closeSoftKeyboard());

        onView(withId(R.id.edt_matkhau_user))
                .perform(typeText("12345678"), closeSoftKeyboard());

        onView(withId(R.id.btn_dangnhap))
                .perform(click());
    }

    private void loginIfNeeded() {
        try {
            onView(withId(R.id.btn_dangnhap))
                    .check(matches(isDisplayed()));

            login();

        } catch (Exception ignored) {

        }
    }
    private void goToProfile() {

        // 1. Đợi Home load
        waitForView(R.id.bottomNavigationView);

        // 2. Click Profile
        onView(withId(R.id.canhan)).perform(click());

        // 3. Nếu bị redirect sang Login → login
        loginIfNeeded();

        // 4. Sau login → đảm bảo quay lại Home
        try {
            waitForView(R.id.canhan); //

            onView(withId(R.id.canhan)).perform(click());
        } catch (Exception ignored) {}

        // 5. Đợi Profile chắc chắn load
        onView(withId(R.id.edt_full_name))
                .check(matches(isDisplayed()));
    }

    public void waitForView(int viewId) {
        onView(isRoot()).perform(waitFor(viewId, 5000));
    }

    @Test
    public void testUI_Display() {
        goToProfile();

        onView(withId(R.id.img_avatar))
                .check(matches(isDisplayed()));

        onView(withId(R.id.edt_full_name))
                .check(matches(isDisplayed()));

        onView(withId(R.id.edt_address))
                .check(matches(isDisplayed()));

        onView(withId(R.id.edt_phone))
                .check(matches(isDisplayed()));

        onView(withId(R.id.edt_date))
                .check(matches(isDisplayed()));

        onView(withId(R.id.btn_update_profile))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testUpdateProfile() {
        goToProfile();

        // Đợi Profile Fragment load
        onView(withId(R.id.edt_full_name))
                .check(matches(isDisplayed()));


        onView(withId(R.id.edt_full_name))
                .perform(clearText(), typeText("Bao Tran"), closeSoftKeyboard());

        onView(withId(R.id.edt_address))
                .perform(clearText(), typeText("Ho Chi Minh"), closeSoftKeyboard());

        onView(withId(R.id.edt_phone))
                .perform(clearText(), typeText("0123456789"), closeSoftKeyboard());

        onView(withId(R.id.rdo_nam))
                .perform(click());

        onView(withId(R.id.btn_update_profile))
                .perform(click());

    }

    @Test
    public void testLogout() {
        goToProfile();

        // Đợi Fragment load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.layout_logout))
                .perform(click());

        onView(withText("Thông báo"))
                .check(matches(isDisplayed()));

        onView(withText("Đăng xuất"))
                .perform(click());

    }


}
