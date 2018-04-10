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
public class HomeScreenTest {
    @Rule
    public ActivityTestRule<HomeScreen> hsActivityTestRule = new ActivityTestRule<HomeScreen>(HomeScreen.class);

    private HomeScreen hsActivity = null;
    Instrumentation.ActivityMonitor hsMonitor = getInstrumentation().addMonitor(HomeScreen.class.getName(), null, false);
    Instrumentation.ActivityMonitor lMonitor = getInstrumentation().addMonitor(LoginActivity.class.getName(), null, false);
    Instrumentation.ActivityMonitor artifactMonitor = getInstrumentation().addMonitor(ArtifactsMainPage.class.getName(), null, false);
    Instrumentation.ActivityMonitor collectorMonitor = getInstrumentation().addMonitor(Collectors.class.getName(), null, false);

    /*
    Instrumentation.ActivityMonitor profileMonitor = getInstrumentation().addMonitor(Collectors.class.getName(), null, false);
    Instrumentation.ActivityMonitor mapsMonitor = getInstrumentation().addMonitor(Collectors.class.getName(), null, false);
    Instrumentation.ActivityMonitor chatMonitor = getInstrumentation().addMonitor(Collectors.class.getName(), null, false);
    Instrumentation.ActivityMonitor calendarMonitor = getInstrumentation().addMonitor(Collectors.class.getName(), null, false);
    Instrumentation.ActivityMonitor notificationsMonitor = getInstrumentation().addMonitor(Collectors.class.getName(), null, false);
    Instrumentation.ActivityMonitor settingsMonitor = getInstrumentation().addMonitor(Collectors.class.getName(), null, false);
    Instrumentation.ActivityMonitor Monitor = getInstrumentation().addMonitor(Collectors.class.getName(), null, false);
     */

    @Before
    public void setUp() throws Exception {
        hsActivity = hsActivityTestRule.getActivity();
    }
    @Test
    public void clickArtifactFail(){
        onView(withId(R.id.button3)).perform(click());
        Activity artifactActivity = getInstrumentation().waitForMonitorWithTimeout(artifactMonitor, 5000);

        assertNotNull(artifactActivity);

    }
    @Test
    public void clickArtifact(){
        onView(withId(R.id.button2)).perform(click());
        Activity artifactActivity = getInstrumentation().waitForMonitorWithTimeout(artifactMonitor, 5000);

        assertNotNull(artifactActivity);

    }
    @Test
    public void clickCollectorsFail(){
        onView(withId(R.id.button2)).perform(click());
        Activity collectorActivity = getInstrumentation().waitForMonitorWithTimeout(collectorMonitor, 5000);

        assertNotNull(collectorActivity);
    }
    @Test
    public void clickCollectors(){
        onView(withId(R.id.button3)).perform(click());
        Activity collectorActivity = getInstrumentation().waitForMonitorWithTimeout(collectorMonitor, 5000);

        assertNotNull(collectorActivity);
    }
    @After
    public void tearDown() throws Exception {
    }

}