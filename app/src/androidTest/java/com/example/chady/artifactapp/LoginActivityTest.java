package com.example.chady.artifactapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.action.TypeTextAction;
import android.support.test.rule.ActivityTestRule;
import android.widget.EditText;
import android.widget.Toast;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.text.TextUtils.isEmpty;
import static org.junit.Assert.*;

/**
 * Created by michaelpoblacion1 on 4/9/18.
 */
public class LoginActivityTest {


    @Rule
    public ActivityTestRule<LoginActivity> lActivityTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);

    private LoginActivity lActivity = null;
    Instrumentation.ActivityMonitor hsMonitor = getInstrumentation().addMonitor(HomeScreen.class.getName(), null, false);
    Instrumentation.ActivityMonitor lMonitor = getInstrumentation().addMonitor(LoginActivity.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        lActivity = lActivityTestRule.getActivity();
    }
    @Test
    public void checkEmailTextBox(){
        String email = "michael@test.com";
        onView(withId(R.id.loginEmail)).perform(typeText(email));
        onView(withText("michael@text.com")).check(matches((isDisplayed())));
    }
    @Test
    public void checkPasswordTextBox(){
        String password = "123456";
        onView(withId(R.id.loginPassword)).perform(typeText(password));
        onView(withText("123456")).check(matches(isDisplayed()));
    }
    @Test
    public void enterWrongEmail(){
        onView(withId(R.id.loginEmail)).perform(typeText("michael@test.co"));
        onView(withId(R.id.loginPassword)).perform(typeText("123456"));
        onView(withId(R.id.BuserLogin)).perform(click());

        Activity loginActivity = getInstrumentation().waitForMonitorWithTimeout(lMonitor,5000);

        assertSame(loginActivity, lActivity);
    }
    @Test
    public void enterWrongEmailFail(){
        onView(withId(R.id.loginEmail)).perform(typeText("michael@test.co"));
        onView(withId(R.id.loginPassword)).perform(typeText("123456"));
        onView(withId(R.id.BuserLogin)).perform(click());

        Activity loginActivity = getInstrumentation().waitForMonitorWithTimeout(hsMonitor,5000);

        assertSame(loginActivity, lActivity);
    }
    @Test
    public void enterWrongPassword(){
        onView(withId(R.id.loginEmail)).perform(typeText("michael@test.com"));
        onView(withId(R.id.loginPassword)).perform(typeText("12345"));
        onView(withId(R.id.BuserLogin)).perform(click());

        Activity loginActivity = getInstrumentation().waitForMonitorWithTimeout(lMonitor,5000);

        assertSame(loginActivity, lActivity);
    }
    @Test
    public void enterWrongPasswordFail(){
        onView(withId(R.id.loginEmail)).perform(typeText("michael@test.com"));
        onView(withId(R.id.loginPassword)).perform(typeText("12345"));
        onView(withId(R.id.BuserLogin)).perform(click());

        Activity loginActivity = getInstrumentation().waitForMonitorWithTimeout(hsMonitor,5000);

        assertSame(loginActivity, lActivity);
    }
    @Test
    public void enterCorrectLoginInfoFail(){
        onView(withId(R.id.loginEmail)).perform(typeText("michael@test.co"));
        onView(withId(R.id.loginPassword)).perform(typeText("123456"));
        onView(withId(R.id.BuserLogin)).perform(click());

        Activity homeScreenActivity = getInstrumentation().waitForMonitorWithTimeout(hsMonitor,5000);

        assertNotNull(homeScreenActivity);
    }
    @Test
    public void enterCorrectLoginInfo(){
        onView(withId(R.id.loginEmail)).perform(typeText("michael@test.com"));
        onView(withId(R.id.loginPassword)).perform(typeText("123456"));
        onView(withId(R.id.BuserLogin)).perform(click());

        Activity homeScreenActivity = getInstrumentation().waitForMonitorWithTimeout(hsMonitor,5000);

        assertNotNull(homeScreenActivity);
    }

    @After
    public void tearDown() throws Exception {
        lActivity = null;
    }

}