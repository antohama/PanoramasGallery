package com.training.anton.panoramasgallery.test;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.squareup.okhttp.mockwebserver.Dispatcher;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;
import com.training.anton.panoramasgallery.EspressoPanoramioApplication;
import com.training.anton.panoramasgallery.PanoramioActivity;
import com.training.anton.panoramasgallery.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class TestWithLocalServer {

    @Rule
    public final ActivityTestRule<PanoramioActivity> activityRule = new ActivityTestRule<PanoramioActivity>(
            PanoramioActivity.class,
            true,
            false);

    private MockWebServer server;
    private String mMockResponse;

    @Test
    public void testRecycleViewFilling() {
        String[] testText = {
                "The first minutes of the New Year 2013!",
                "St. George's Cathedral -Lviv",
                "Church at night",
                "\"Toys\" Mickiewicz Square. Lviv"
        };

        InputStream raw = getInstrumentation().getTargetContext().getResources().openRawResource(R.raw.mock);
        Scanner scanner = new Scanner(raw).useDelimiter("\\A");
        mMockResponse = scanner.hasNext() ? scanner.next() : "";
        try {
            raw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final Dispatcher dispatcher = new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                if (request.getPath().contains("/map/get_panoramas.php")) {
                    return new MockResponse().setResponseCode(200).setBody(mMockResponse);
                }
                return new MockResponse().setResponseCode(404);
            }
        };
        server = EspressoPanoramioApplication.get().getServer();
        server.setDispatcher(dispatcher);

        activityRule.launchActivity(null);

        for (String text : testText) {
            onView(withText(text)).check(matches(isDisplayed()));
            Log.d("Debug info: ", "found text: " + text);
        }
    }

    @Test
    public void testMainActivity_title() {
        activityRule.launchActivity(null);
        onView(withText(getInstrumentation().getTargetContext().getString(R.string.app_name))).check(matches(isDisplayed()));
    }
}
