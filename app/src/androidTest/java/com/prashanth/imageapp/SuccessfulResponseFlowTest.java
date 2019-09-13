package com.prashanth.imageapp;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertFalse;

import android.content.Intent;
import android.widget.EditText;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import com.prashanth.imageapp.ui.MainActivity;
import com.robotium.solo.Solo;
import java.io.IOException;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import timber.log.Timber;

@RunWith(AndroidJUnit4.class)
public class SuccessfulResponseFlowTest {

    private MockWebServer server;

    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, false, false);

    @Before
    public void setUp() throws Exception {
        setupServer();
        solo = new Solo(getInstrumentation(), rule.getActivity());
    }

    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        server.shutdown();
    }

    @Test()
    public void launchAppWithSuccessfulResponse() {

        //Launch app with the MockServer running
        rule.launchActivity(new Intent());

        EditText searchEditText = (EditText) solo.getView(R.id.search_edittext);

        //search for music
        solo.enterText(searchEditText, "music");

        solo.pressSoftKeyboardSearchButton();

        assertFalse(solo.getCurrentActivity().isFinishing());

        introduceDelay(3000L);

    }

    private void setupServer() throws Exception {

        server = new MockWebServer();
        Dispatcher dispatcher = new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                if (request.getMethod().equals("GET") && request.getPath().contains("/v2/images/search/")) {

                    String imageResponse;
                    try {
                        imageResponse = IOUtils.toString(getInstrumentation().getContext().getResources().getAssets().open("json/image_response.json"));
                        return new MockResponse().setResponseCode(200).setBody(imageResponse);
                    } catch (IOException e) {
                        Timber.e(e);
                    }
                }
                return null;
            }
        };

        server.setDispatcher(dispatcher);
        server.start(8080);

    }

    private void introduceDelay(long timeout) {
        synchronized (this) {
            try {
                this.wait(timeout);
            } catch (InterruptedException e) {
                Timber.e(e);
            }
        }
    }

}