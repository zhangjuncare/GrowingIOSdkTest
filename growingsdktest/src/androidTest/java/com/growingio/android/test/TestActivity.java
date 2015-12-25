package com.growingio.android.test;

import android.os.Build;
import android.test.ActivityInstrumentationTestCase2;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.growingio.android.test.util.GrowingLogUtil;
import com.robotium.solo.Solo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by lishaojie on 15/12/24.
 */
public class TestActivity extends ActivityInstrumentationTestCase2<SplashActivity> {

    private static final String TAG = "SDK_TEST";
    private static final int EVENT_FIRST_VISIT = 0;
    private static final int EVENT_FIRST_PAGE = 1;
    private static final int EVENT_SPLASH_IMPRESSION = 2;
    private static final int EVENT_MAIN_PAGE_FROM_SPLASH = 3;
    private static final int EVENT_MAIN_PAGE_IMPRESSION = 4;
    private static final int EVENT_FLOAT_LAYOUT_CHANGE = 5;
    private static final int EVENT_CHANGE_TEXT_BUTTON_CLICK = 6;
    private static final int EVENT_TEXT_CHANGED_JOKER_IMPRESSION = 7;
    private static final int EVENT_CHANGE_TEXT_CLICK_AGAIN = 8;
    private static final int EVENT_OPEN_DRAWER_CLICK = 9;
    private static final int EVENT_DRAWER_IMPRESSION = 10;
    private static final int EVENT_BUTTON_IN_DRAWER_CLICK = 11;
    private static final int EVENT_MAX_COUNT = 12;
    private Solo solo;
    private DisplayMetrics metrics;
    private List<JSONObject> events;
    private List<JSONObject> expectEvents;
    private String sessionID;
    private JSONObject mEvent;
    private JSONObject mExpect;

    public TestActivity() {
        super(SplashActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
        solo.getConfig().commandLogging = true;
        metrics = getActivity().getResources().getDisplayMetrics();
        GrowingLogUtil.init(getActivity());
        assertTrue(solo.waitForActivity(SplashActivity.class));
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        solo = null;
    }

    public void testPrepareData() throws Exception {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            // com.robotium.solo.Solo.assertCurrentActivity doesn't work on Android M
            solo.sleep(1000);
        } else {
            solo.waitForActivity(MainActivity.class);
        }
        solo.sleep(500);
        solo.clickOnView(solo.getView(R.id.button));
        solo.sleep(500);
        solo.clickOnView(solo.getView(R.id.button));
        solo.sleep(500);
        clickOnNavigationButton();
        solo.sleep(1000);
        solo.clickOnText("Click button");
        solo.sleep(1000);
        Log.i(TAG, "simulate data prepared.");
        testDataCount();
        testCommonVisitEvent(EVENT_FIRST_VISIT, "First Visit");
        testCommonPageEvent(EVENT_FIRST_PAGE, "Splash Page");
        testCommonEvent(EVENT_SPLASH_IMPRESSION, "Splash Impression");
        testCommonPageEvent(EVENT_MAIN_PAGE_FROM_SPLASH, "Main Page");
        testCommonEvent(EVENT_MAIN_PAGE_IMPRESSION, "Main Impression");
        testCommonEvent(EVENT_FLOAT_LAYOUT_CHANGE, "ActionMenuView LayoutChanged");
        testCommonEvent(EVENT_CHANGE_TEXT_BUTTON_CLICK, "TextChange Click");
        testCommonEvent(EVENT_TEXT_CHANGED_JOKER_IMPRESSION, "TextChanged Impression");
        testCommonEvent(EVENT_CHANGE_TEXT_CLICK_AGAIN, "TextChange Click Again");
        testCommonEvent(EVENT_OPEN_DRAWER_CLICK, "Open Drawer Click");
        testCommonEvent(EVENT_DRAWER_IMPRESSION, "Drawer Impression");
        testCommonEvent(EVENT_BUTTON_IN_DRAWER_CLICK, "Drawer Menu Item Click");
    }

    private void testCommonEvent(int eventIndex, String message) throws Exception {
        prepareDataFor(eventIndex);
        assertInSession(message);
        assertBaseInfo(message);
        assertXPathEqual(message);
    }

    private void clickOnNavigationButton() {
        View toolbar = solo.getView(R.id.toolbar);
        int[] location = new int[2];
        toolbar.getLocationOnScreen(location);
        location[0] += toolbar.getHeight() / 2;
        location[1] += toolbar.getHeight() / 2;
        solo.clickOnScreen(location[0], location[1]);
    }

    private void testDataCount() throws Exception {
        expectEvents = GrowingLogUtil.parseLog(R.raw.app_event_flow);
        assertNotNull(expectEvents);
        assertEquals("Prepared data count must be " + EVENT_MAX_COUNT, expectEvents.size(), EVENT_MAX_COUNT);
        events = GrowingLogUtil.parseLogFile(TestApplication.sLogFilePath);
        assertNotNull(events);
        assertTrue("Data row "+events.size()+" less than "+EVENT_MAX_COUNT, events.size() >= EVENT_MAX_COUNT);
    }

    private void testCommonVisitEvent(int eventIndex, String message) throws Exception {
        prepareDataFor(eventIndex);
        sessionID = mEvent.getString("s");
        assertBaseInfo(message);
        assertStringMemberEqual(message, "b");
        assertStringMemberEqual(message, "ch");
    }

    private void testCommonPageEvent(int eventIndex, String message) throws Exception {
        prepareDataFor(eventIndex);
        assertInSession(message);
        assertBaseInfo(message);
        assertStringMemberEqual(message, "tl");
        assertStringMemberEqual(message, "rp");
    }

    private void assertXPathEqual(String message) throws Exception {
        JSONArray eventXPath = mEvent.getJSONArray("e");
        JSONArray expectXPath = mExpect.getJSONArray("e");
        assertEquals(eventXPath.length(), expectXPath.length());
        for (int i = eventXPath.length() - 1; i >= 0; i--) {
            JSONObject e = eventXPath.getJSONObject(i);
            JSONObject x = expectXPath.getJSONObject(i);
            assertStringMemberEqual(message, e, x, "x");
            assertStringMemberEqual(message, e, x, "v");
            assertStringMemberEqual(message, e, x, "n");
            assertIntMemberEqual(message, e, x, "gi");
        }
    }

    private void assertIntMemberEqual(String message, JSONObject e, JSONObject x, String member) throws Exception {
        assertEquals(message, e.getInt(member), x.getInt(member));
    }

    private static final String BASEINFO_PREFIX = "BaseInfo - ";
    private void assertBaseInfo(String message) throws Exception {
        assertStringMemberEqual(BASEINFO_PREFIX + message, "p");
        assertStringMemberEqual(BASEINFO_PREFIX + message, "t");
        assertStringMemberEqual(BASEINFO_PREFIX + message, "d");
    }

    private void assertInSession(String message) throws JSONException {
        assertEquals("Session - " + message, sessionID, mEvent.getString("s"));
    }

    private void prepareDataFor(int index) {
        mEvent = events.get(index);
        mExpect = expectEvents.get(index);
    }

    private void assertStringMemberEqual(String message, JSONObject o1, JSONObject o2, String member) throws Exception {
        if (o1.opt(member) == o2.opt(member)) return;
        assertEquals(message, o1.getString(member), o2.getString(member));
    }

    private void assertStringMemberEqual(String message, String member) throws Exception {
        assertStringMemberEqual(message, mExpect, mEvent, member);
    }
}
