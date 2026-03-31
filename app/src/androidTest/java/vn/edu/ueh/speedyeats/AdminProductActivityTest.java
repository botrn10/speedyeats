package vn.edu.ueh.speedyeats;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.idling.CountingIdlingResource;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import androidx.test.espresso.IdlingRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import vn.edu.ueh.speedyeats.View.Admin.AdminAddSPActivity;
import vn.edu.ueh.speedyeats.View.Admin.AdminProductActivity;

@RunWith(AndroidJUnit4.class)
public class AdminProductActivityTest {

    @Rule
    public ActivityScenarioRule<AdminProductActivity> rule =
            new ActivityScenarioRule<>(AdminProductActivity.class);

    @Test
    public void testUIComponentsDisplayed() {
        onView(withId(R.id.rcv_admin_product))
                .check(matches(isDisplayed()));

        onView(withId(R.id.spinner_loai_sp))
                .check(matches(isDisplayed()));

        onView(withId(R.id.image_add_product))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testClickAddProduct_openAddScreen() {
        Intents.init();

        onView(withId(R.id.image_add_product))
                .perform(click());

        intended(hasComponent(AdminAddSPActivity.class.getName()));

        Intents.release();
    }

    @Test
    public void testSelectSpinnerItem() {
        onView(withId(R.id.spinner_loai_sp))
                .perform(click());

        onData(allOf(is(instanceOf(String.class)), is("Tất cả")))
                .perform(click());

        // Có thể check RecyclerView không crash hoặc có item
        onView(withId(R.id.rcv_admin_product))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testClickItem_openEditScreen() {
        Intents.init();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.rcv_admin_product))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        intended(hasComponent(AdminAddSPActivity.class.getName()));

        Intents.release();
    }
}
