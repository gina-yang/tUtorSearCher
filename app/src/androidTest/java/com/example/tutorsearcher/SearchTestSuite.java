package com.example.tutorsearcher;

// Test Suite for Search functionality

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.tutorsearcher.activity.MainActivity;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class SearchTestSuite {

    // Need this to define the activity we're going to pull elements from
    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void initValidString() {
        // nothing here yet
    }

    @Test
    public void noResultTimeslotMismatchTest() {
//        int targetNumResults = 0;

//        // Set course to an existing course (e.g. CSCI310)
//        onView(withId(R.id.searchText))
//                .perform(typeText("CSCI310"), closeSoftKeyboard());
//        // Set timeslot to a non-existent timeslot (e.g. Sun 07)
//        // Select Day as "Sun"
//        onView(withId(R.id.daySpinner)).perform(click());
//        onData(allOf(is(instanceOf(String.class)), is("Sun"))).perform(click());
//        // Select Time as "07"
//        onView(withId(R.id.timeSpinner)).perform(click());
//        onData(allOf(is(instanceOf(String.class)), is("07"))).perform(click());
//
//        // Hit Search button
//        onView(withId(R.id.searchButton)).perform(click());
//
//        // Have to sleep to give results a chance to load
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        // Verify that there are no results
//        onView(withId(R.id.searchResultsContainer)).check(matches(
//                hasChildren(is(targetNumResults))
//        ));
    }

    @Test
    public void noResultCourseMismatchTest() {
//        int targetNumResults = 0;
//
//        // Set course to a non-existent course (e.g. ABCD000)
//        onView(withId(R.id.searchText))
//                .perform(typeText("ABCD000"), closeSoftKeyboard());
//        // Set timeslot to an existing timeslot (e.g. Mon 11)
//        // Select Day as "Mon"
//        onView(withId(R.id.daySpinner)).perform(click());
//        onData(allOf(is(instanceOf(String.class)), is("Mon"))).perform(click());
//        // Select Time as "11"
//        onView(withId(R.id.timeSpinner)).perform(click());
//        onData(allOf(is(instanceOf(String.class)), is("11"))).perform(click());
//
//        // Hit Search button
//        onView(withId(R.id.searchButton)).perform(click());
//
//        // Have to sleep to give results a chance to load
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        // Verify that there are no results
//        onView(withId(R.id.searchResultsContainer)).check(matches(
//                hasChildren(is(targetNumResults))
//        ));
    }

    @Test
    public void noResultBothMismatchTest() {
//        int targetNumResults = 0;
//
//        // Set course to a non-existent course (e.g. ABCD000)
//        onView(withId(R.id.searchText))
//                .perform(typeText("ABCD000"), closeSoftKeyboard());
//        // Set timeslot to a non-existent timeslot (e.g. Sun 07)
//        // Select Day as "Sun"
//        onView(withId(R.id.daySpinner)).perform(click());
//        onData(allOf(is(instanceOf(String.class)), is("Sun"))).perform(click());
//        // Select Time as "07"
//        onView(withId(R.id.timeSpinner)).perform(click());
//        onData(allOf(is(instanceOf(String.class)), is("07"))).perform(click());
//
//        // Hit Search button
//        onView(withId(R.id.searchButton)).perform(click());
//
//        // Have to sleep to give results a chance to load
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        // Verify that there are no results
//        onView(withId(R.id.searchResultsContainer)).check(matches(
//                hasChildren(is(targetNumResults))
//        ));
    }

    @Test
    public void oneResultTest() {
//        int targetNumResults = 1;
//
//        onView(withId(R.id.searchText))
//                .perform(typeText("CSCI310"), closeSoftKeyboard());
//        onView(withId(R.id.daySpinner)).perform(click());
//        onData(allOf(is(instanceOf(String.class)), is("Mon"))).perform(click());
//        onView(withId(R.id.timeSpinner)).perform(click());
//        onData(allOf(is(instanceOf(String.class)), is("11"))).perform(click());
//
//        // Hit Search button
//        onView(withId(R.id.searchButton)).perform(click());
//
//        // Have to sleep to give results a chance to load
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        // Verify that there is one result
//        onView(withId(R.id.searchResultsContainer)).check(matches(
//                hasChildren(is(targetNumResults))
//        ));
    }

    @Test
    public void twoResultsTest() {
//        int targetNumResults = 2;
//
//        onView(withId(R.id.searchText))
//                .perform(typeText("CSCI310"), closeSoftKeyboard());
//        onView(withId(R.id.daySpinner)).perform(click());
//        onData(allOf(is(instanceOf(String.class)), is("Mon"))).perform(click());
//        onView(withId(R.id.timeSpinner)).perform(click());
//        onData(allOf(is(instanceOf(String.class)), is("15"))).perform(click());
//
//        // Hit Search button
//        onView(withId(R.id.searchButton)).perform(click());
//
//        // Have to sleep to give results a chance to load
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        // Verify that there are two results
//        onView(withId(R.id.searchResultsContainer)).check(matches(
//                hasChildren(is(targetNumResults))
//        ));
    }

    // hasChildren matcher from: https://stackoverflow.com/questions/23797401/using-espresso-how-do-i-check-the-number-of-items-in-my-alert-dialog
    public static Matcher<View> hasChildren(final Matcher<Integer> numChildrenMatcher) {
        return new TypeSafeMatcher<View>() {

            /**
             * matching with viewgroup.getChildCount()
             */
            @Override
            public boolean matchesSafely(View view) {
                return view instanceof ViewGroup && numChildrenMatcher.matches(((ViewGroup)view).getChildCount());
            }

            /**
             * gets the description
             */
            @Override
            public void describeTo(Description description) {
                description.appendText(" a view with # children is ");
                numChildrenMatcher.describeTo(description);
            }
        };
    }
}