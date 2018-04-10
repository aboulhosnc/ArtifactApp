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
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

/**
 * Created by michaelpoblacion1 on 4/9/18.
 */
public class SignupTest {
    @Rule
    public ActivityTestRule<Signup> suActivityTestRule = new ActivityTestRule<Signup>(Signup.class);

    private Signup suActivity = null;
    Instrumentation.ActivityMonitor suMonitor = getInstrumentation().addMonitor(Signup.class.getName(), null, false);
    Instrumentation.ActivityMonitor lMonitor = getInstrumentation().addMonitor(LoginActivity.class.getName(), null, false);
    Instrumentation.ActivityMonitor mainMonitor = getInstrumentation().addMonitor(MainActivity.class.getName(), null, false);


    @Before
    public void setUp() throws Exception {
        suActivity = suActivityTestRule.getActivity();
    }
    @Test
    public void enterNameTextFail(){
        String name = "Mike";
        onView(withId(R.id.TFname)).perform(typeText(name));
        onView(withText("Mike P")).check(matches(isDisplayed()));

    }
    @Test
    public void enterNameText(){
        String name = "Mike P";
        onView(withId(R.id.TFname)).perform(typeText(name));
        onView(withText("Mike P")).check(matches(isDisplayed()));

    }
    @Test
    public void enterEmailTextFail(){
        String email = "michael2@test.com";
        onView(withId(R.id.TFemail)).perform(typeText(email));
        onView(withText("Mike P")).check(matches(isDisplayed()));

    }
    @Test
    public void enterEmailText(){
        String email = "michael2@test.com";
        onView(withId(R.id.TFemail)).perform(typeText(email));
        onView(withText(email)).check(matches(isDisplayed()));

    }
    @Test
    public void enterPasswordTextFail(){
        String password = "123456";
        onView(withId(R.id.TFpassword)).perform(typeText(password));
        onView(withText("M2")).check(matches(isDisplayed()));
    }
    @Test
    public void enterPasswordText(){
        String password = "123456";
        onView(withId(R.id.TFpassword)).perform(typeText(password));
        onView(withText(password)).check(matches(isDisplayed()));
    }
    @Test
    public void enterPassword2TextFail(){
        String password = "123456";
        onView(withId(R.id.TFconfirmPassword)).perform(typeText(password));
        onView(withText("12345")).check(matches(isDisplayed()));
    }
    @Test
    public void enterPassword2Text(){
        String password = "123456";
        onView(withId(R.id.TFconfirmPassword)).perform(typeText(password));
        onView(withText(password)).check(matches(isDisplayed()));
    }
    @Test
    public void completeSignUpFailName(){
        String name = "Mike";
        String email = "Mike@test.com";
        String pass = "123456";
        String pass2 = "123456";

        //onView(withId(R.id.TFname)).perform(typeText(name));
        onView(withId(R.id.TFemail)).perform(typeText(email));
        onView(withId(R.id.TFpassword)).perform(typeText(pass));
        onView(withId(R.id.TFconfirmPassword)).perform(typeText(pass2));
        onView(withId(R.id.BuserSignUp)).perform(click());
        Activity signUpActivity = getInstrumentation().waitForMonitorWithTimeout(lMonitor,5000);

        assertNotNull(signUpActivity);
    }
    @Test
    public void completeSignUpFailEmail(){
        String name = "Mike";
        String email = "Mike@test.com";
        String pass = "123456";
        String pass2 = "123456";

        onView(withId(R.id.TFname)).perform(typeText(name));
        //onView(withId(R.id.TFemail)).perform(typeText(email));
        onView(withId(R.id.TFpassword)).perform(typeText(pass));
        onView(withId(R.id.TFconfirmPassword)).perform(typeText(pass2));

        onView(withId(R.id.BuserSignUp)).perform(click());
        Activity signUpActivity = getInstrumentation().waitForMonitorWithTimeout(lMonitor,5000);

        assertNotNull(signUpActivity);


    }
    @Test
    public void completeSignUpFailpass(){
        String name = "Mike";
        String email = "Mike@test.com";
        String pass = "123456";
        String pass2 = "12345";

        onView(withId(R.id.TFname)).perform(typeText(name));
        onView(withId(R.id.TFemail)).perform(typeText(email));
        onView(withId(R.id.TFpassword)).perform(typeText(pass));
        onView(withId(R.id.TFconfirmPassword)).perform(typeText(pass2));
        onView(withId(R.id.BuserSignUp)).perform(click());
        Activity signUpActivity = getInstrumentation().waitForMonitorWithTimeout(lMonitor,5000);

        assertNotNull(signUpActivity);
    }
    @Test
    public void completeSignUp(){
        String name = "Mike";
        String email = "Mike1@test.com";
        String pass = "123456";
        String pass2 = "123456";

        onView(withId(R.id.TFname)).perform(typeText(name));
        onView(withId(R.id.TFemail)).perform(typeText(email));
        onView(withId(R.id.TFpassword)).perform(typeText(pass));
        onView(withId(R.id.TFconfirmPassword)).perform(typeText(pass2));
        onView(withId(R.id.BuserSignUp)).perform(click());

        Activity signUpActivity = getInstrumentation().waitForMonitorWithTimeout(lMonitor,5000);

        assertNotNull(signUpActivity);
    }
    @Test
    public void cancelSignupFail(){
        String name = "Mike";
        String email = "Mike@test.com";
        String pass = "123456";
        String pass2 = "123456";

        //onView(withId(R.id.TFname)).perform(typeText(name));
        //onView(withId(R.id.TFemail)).perform(typeText(email));
        onView(withId(R.id.TFpassword)).perform(typeText(pass));
        onView(withId(R.id.TFconfirmPassword)).perform(typeText(pass2));
        onView(withId(R.id.BuserSignUp)).perform(click());

        Activity signUpActivity = getInstrumentation().waitForMonitorWithTimeout(lMonitor,5000);

        assertNotNull(signUpActivity);
    }
    @Test
    public void cancelSignup(){
        String name = "Mike";
        String email = "Mike@test.com";
        String pass = "123456";
        String pass2 = "123456";

        onView(withId(R.id.TFemail)).perform(typeText(email));
        onView(withId(R.id.cancelButton)).perform(click());

        Activity mainActivity = getInstrumentation().waitForMonitorWithTimeout(mainMonitor,5000);

        assertNotNull(mainActivity);
    }
    @After
    public void tearDown() throws Exception {
    }

}