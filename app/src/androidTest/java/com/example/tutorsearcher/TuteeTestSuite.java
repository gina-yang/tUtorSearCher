package com.example.tutorsearcher;

// Test Suite for Tutee functionality
// For now, just making sure that tutees can view profile info of accepted tutors
// Later functionality: tutees should be able to leave and view ratings for tutors

import android.app.Instrumentation;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.fragment.app.FragmentManager;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.tutorsearcher.activity.MainActivity;
import com.example.tutorsearcher.db.DBAccessor;
import com.example.tutorsearcher.db.getRequestsCommandWrapper;
import com.example.tutorsearcher.ui.login.LoginActivity;
import com.example.tutorsearcher.ui.notifications.RequestsFragment;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.example.tutorsearcher.SearchTestSuite.hasChildren;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

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
        int targetNumResults = 0;

        // Log in as tutee with no pending or accepted requests
        User u = new Tutee("tuteetest@usc.edu");
        LoginActivity.loggedInUser = u;

        // Navigate to Requests tab to load requests
        onView(withId(R.id.navigation_notifications)).perform(click());

        // Wait for requests to load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify that there are no results
        onView(withId(R.id.requestsContainer)).check(matches(
                hasChildren(is(targetNumResults))
        ));
    }

    @Test
    public void oneRequestViewPending() {
        int targetNumResults = 1;

        // Log in as tutee with one pending request
        User u = new Tutee("carl@usc.edu");
        LoginActivity.loggedInUser = u;

        // Navigate to Requests tab to load requests
        onView(withId(R.id.navigation_notifications)).perform(click());
//        // get Requests Fragment by ID: navigation_notifications
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        RequestsFragment requestsFragment = (RequestsFragment)fragmentManager.findFragmentById(R.id.navigation_notifications);
//        getRequestsCommandWrapper tuteeRequestsWrapper = requestsFragment.new tuteeRequestsWrapper();
//        new DBAccessor().getAllRequests(LoginActivity.loggedInUser.getEmail(), LoginActivity.loggedInUser.getType(), tuteeRequestsWrapper);

        // Wait for requests to load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify that there is one result
        onView(withId(R.id.requestsContainer)).check(matches(
                hasChildren(is(targetNumResults))
        ));

        // Verify that the tutor's name is correct
        onView(nthChildOf(nthChildOf(withId(R.id.requestsContainer),
                0), 0)).check(matches(withText(containsString("Kara Mota"))));
    }

    @Test
    public void oneRequestViewAccepted() {
        int targetNumResults = 1;

        // Log in as tutee with one accepted request
        User u = new Tutee("bentest1@usc.edu");
        LoginActivity.loggedInUser = u;

        // Navigate to Requests tab to load requests
        onView(withId(R.id.navigation_notifications)).perform(click());

        // Wait for requests to load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify that there is one result
        onView(withId(R.id.requestsContainer)).check(matches(
                hasChildren(is(targetNumResults))
        ));

        // Verify that the tutor's name is correct
        onView(nthChildOf(nthChildOf(withId(R.id.requestsContainer),
                0), 0)).check(matches(withText(containsString("Keetu Nhanna"))));
    }

    @Test
    public void twoRequestsView() {
        int targetNumResults = 2;

        // Log in as tutee with two accepted requests
        User u = new Tutee("bentest2@usc.edu");
        LoginActivity.loggedInUser = u;

        // Navigate to Requests tab to load requests
        onView(withId(R.id.navigation_notifications)).perform(click());

        // Wait for requests to load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify that there are two results
        onView(withId(R.id.requestsContainer)).check(matches(
                hasChildren(is(targetNumResults))
        ));

        // Verify that the tutor's name is correct in both cases
        onView(nthChildOf(nthChildOf(withId(R.id.requestsContainer),
                0), 0)).check(matches(withText(containsString("Keetu Nhanna"))));
        onView(nthChildOf(nthChildOf(withId(R.id.requestsContainer),
                1), 0)).check(matches(withText(containsString("Keetu Nhanna"))));
    }

    // Adapted from: https://stackoverflow.com/questions/20860832/why-does-getactivity-block-during-junit-test-when-custom-imageview-calls-start
    public MainActivity getActivity() {
        Intent intent = new Intent(getInstrumentation().getTargetContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // register activity that need to be monitored.
        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(MainActivity.class.getName(), null, false);
        getInstrumentation().getTargetContext().startActivity(intent);
        MainActivity mActivity = (MainActivity) getInstrumentation().waitForMonitor(monitor);
        return mActivity;
    }

    // adapted from: https://stackoverflow.com/questions/24748303/selecting-child-view-at-index-using-espresso
    public static Matcher<View> nthChildOf(final Matcher<View> parentMatcher, final int childPosition) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("with "+childPosition+" child view of type parentMatcher");
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view.getParent() instanceof ViewGroup)) {
                    return parentMatcher.matches(view.getParent());
                }

                ViewGroup group = (ViewGroup) view.getParent();
                return parentMatcher.matches(view.getParent()) && group.getChildAt(childPosition).equals(view);
            }
        };
    }
}
