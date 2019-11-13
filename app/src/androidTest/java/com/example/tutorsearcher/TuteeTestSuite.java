package com.example.tutorsearcher;

// Test Suite for Tutee functionality
// For now, just making sure that tutees can view profile info of accepted tutors
// Later functionality: tutees should be able to leave and view ratings for tutors

import android.util.Log;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.tutorsearcher.activity.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class TuteeTestSuite {
    private String stringToBetyped;

    // Need this to define the activity we're going to pull elements from
    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
        stringToBetyped = "Espresso";
    }

    @Test
    public void noRequestsView() {
    }

    @Test
    public void oneRequestView() {
    }

    @Test
    public void twoRequestsView() {
    }
}
