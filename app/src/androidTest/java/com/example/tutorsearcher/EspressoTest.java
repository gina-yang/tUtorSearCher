package com.example.tutorsearcher;

import android.util.Log;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.tutorsearcher.db.DBAccessor;
import com.example.tutorsearcher.db.searchCommandWrapper;
import com.example.tutorsearcher.ui.home.SearchFragment;
import com.example.tutorsearcher.ui.login.LoginActivity;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class EspressoTest {

    private String stringToBetyped;

    // Need this to define the activity we're going to pull elements from
    @Rule
    public ActivityTestRule<LoginActivity> activityRule
            = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
        stringToBetyped = "Espresso";
    }

    @Test
    public void testThatShouldPass() {
        onView(withId(R.id.username))
                .perform(typeText(stringToBetyped), closeSoftKeyboard());
        // onView(withId(R.id.changeTextBt)).perform(click());

        // Check that the text was changed.
        onView(withId(R.id.username))
                .check(matches(withText(stringToBetyped)));

        Log.d("test", "The Espresso Pass test has run!");
    }

    @Test
    public void testThatShouldFail() {
        // Type text.
        onView(withId(R.id.username))
                .perform(typeText(stringToBetyped), closeSoftKeyboard());

        // Check if the text happens to match string "Wahoo!".
        onView(withId(R.id.username))
                .check(matches(withText("Wahoo!")));

        Log.d("test", "The Espresso Fail test has run!");
    }


}