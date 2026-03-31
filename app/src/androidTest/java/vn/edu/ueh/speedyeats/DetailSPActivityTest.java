package vn.edu.ueh.speedyeats;
import android.content.Intent;
import android.os.IBinder;
import android.view.WindowManager;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Root;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;

import vn.edu.ueh.speedyeats.Model.Product;
import vn.edu.ueh.speedyeats.R;
import vn.edu.ueh.speedyeats.View.DetailSPActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static org.hamcrest.Matchers.containsString;

@RunWith(AndroidJUnit4.class)
public class DetailSPActivityTest {
    private Product fakeProduct() {
        return new Product(
                "1",
                "sp1",
                "Gà rán",
                76000L,
                "https://dummyimage.com/300.png",
                "food",
                "Mô tả test",
                10L,
                "12h",
                1L,
                "200g"
        );
    }

    @Test
    public void testDisplayProductDetail() {
        Intent intent = new Intent(
                ApplicationProvider.getApplicationContext(),
                DetailSPActivity.class
        );

        intent.putExtra("search", fakeProduct());

        try (ActivityScenario<DetailSPActivity> scenario =
                     ActivityScenario.launch(intent)) {

            // Check giá
            onView(withId(R.id.tv_gia_detail))
                    .check(matches(isDisplayed()));

            // Check mô tả
            onView(withId(R.id.tv_mota_detail))
                    .check(matches(withText(containsString("Mô tả"))));
        }
    }

    @Test
    public void testOpenBottomSheet() {
        Intent intent = new Intent(
                ApplicationProvider.getApplicationContext(),
                DetailSPActivity.class
        );

        intent.putExtra("search", fakeProduct());

        try (ActivityScenario<DetailSPActivity> scenario =
                     ActivityScenario.launch(intent)) {

            // Click nút add cart
            onView(withId(R.id.btn_addcart_detail))
                    .perform(click());

            // Check bottom sheet hiển thị
            onView(withId(R.id.tv_ten_bottom))
                    .inRoot(isDialog())
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void testIncreaseDecreaseQuantity() {
        Intent intent = new Intent(
                ApplicationProvider.getApplicationContext(),
                DetailSPActivity.class
        );

        intent.putExtra("search", fakeProduct());

        try (ActivityScenario<DetailSPActivity> scenario =
                     ActivityScenario.launch(intent)) {

            onView(withId(R.id.btn_addcart_detail))
                    .perform(click());

            // Click +
            onView(withId(R.id.btn_plus_bottom))
                    .inRoot(isDialog())
                    .perform(click());

            // Check số lượng = 2
            onView(withId(R.id.tv_soluong_bottom))
                    .inRoot(isDialog())
                    .check(matches(withText("2")));
        }
    }

    @Test
    public void testAddToCartClick() {
        Intent intent = new Intent(
                ApplicationProvider.getApplicationContext(),
                DetailSPActivity.class
        );

        intent.putExtra("search", fakeProduct());

        try (ActivityScenario<DetailSPActivity> scenario =
                     ActivityScenario.launch(intent)) {

            onView(withId(R.id.btn_addcart_detail))
                    .perform(click());

            onView(withId(R.id.btn_bottom))
                    .inRoot(isDialog())
                    .perform(click());
        }
    }

    @Test
    public void testToggleFavorite_NotLogin() {
        Intent intent = new Intent(
                ApplicationProvider.getApplicationContext(),
                DetailSPActivity.class
        );

        intent.putExtra("search", fakeProduct());

        try (ActivityScenario<DetailSPActivity> scenario =
                     ActivityScenario.launch(intent)) {

            onView(withId(R.id.toogle_btn_favorite))
                    .perform(click())
                    .check(matches(isNotChecked()));
        }
    }
}