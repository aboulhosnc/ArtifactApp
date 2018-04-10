package com.example.chady.artifactapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by michaelpoblacion1 on 4/9/18.
 */
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mActivity = null;
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(LoginActivity.class.getName(), null, false);
    Instrumentation.ActivityMonitor signupMonitor = getInstrumentation().addMonitor(Signup.class.getName(), null, false);


    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }
    @Test
    public void testLaunchOfLoginActivityOnLoginButtonClick(){
        assertNotNull(mActivity.findViewById(R.id.BLogin));

        onView(withId(R.id.BLogin)).perform(click());

        Activity loginActivity = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);

        assertNotNull(loginActivity);
    }
    @Test
    public void testLaunchOfSignupActivityOnSignupButtonClcik(){
        assertNotNull(mActivity.findViewById(R.id.BSignUp));

        onView(withId(R.id.BSignUp)).perform(click());

        Activity signupActivity = getInstrumentation().waitForMonitorWithTimeout(signupMonitor, 5000);

        assertNotNull(signupActivity);
    }
    @After
    public void tearDown() throws Exception {
        mActivity = null;

    }

}