package vn.edu.ueh.speedyeats;
import androidx.test.espresso.contrib.RecyclerViewActions;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import vn.edu.ueh.speedyeats.View.SearchActivity;
import static androidx.test.espresso.intent.Intents.*;
import static androidx.test.espresso.intent.matcher.IntentMatchers.*;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class HomeFragmentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> rule =
            new ActivityScenarioRule<>(MainActivity.class);

//    @Test
//    public void testScrollAndClickRecyclerView() {
//
//        onView(withId(R.id.rcv_Garan))
//                .check(matches(isDisplayed()));
//
//        onView(withId(R.id.rcv_Garan))
//                .perform(RecyclerViewActions.scrollToPosition(0));
//
//        onView(withId(R.id.rcv_Garan))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
//    }

    @Test
    public void testUIComponentsDisplayed() {

        onView(withId(R.id.edt_search_home))
                .check(matches(isDisplayed()));

        onView(withId(R.id.img_home_cart))
                .check(matches(isDisplayed()));

        onView(withId(R.id.rcv_Garan))
                .check(matches(isDisplayed()));

        // đảm bảo recycler view load xong
        onView(withId(R.id.rcv_Garan))
                .perform(RecyclerViewActions.scrollToPosition(0));
    }

    @Test
    public void testClickSearch_openSearchActivity() {

        // Click search ở Home
        onView(withId(R.id.edt_search_home))
                .perform(click());

        // Kiểm tra SearchActivity mở ra
        onView(withId(R.id.search_view))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testClickCart_openCartActivity() {

        onView(withId(R.id.img_home_cart))
                .perform(click());

        onView(withId(R.id.tv_null_cart))
                .check(matches(isDisplayed()));
    }
}
