package com.growingio.android.test;

import android.test.ActivityInstrumentationTestCase2;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.growingio.android.test.util.GrowingLogUtil;
import com.robotium.solo.Solo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Queue;

/**
 * Created by lishaojie on 15/12/26.
 */
abstract class ActivityTestBase extends ActivityInstrumentationTestCase2 {
    public static final String TAG = ActivityTestBase.class.getSimpleName();

    protected Solo solo;
    protected DisplayMetrics metrics;
    protected int listItemHeight;
    protected static String sessionID;
    protected JSONObject mEvent;
    protected JSONObject mExpect;
    protected Queue<JSONObject> mExpectList;

    public ActivityTestBase(Class activityClass) {
        super(activityClass);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
        solo.getConfig().commandLogging = true;
        metrics = getActivity().getResources().getDisplayMetrics();
        listItemHeight = getActivity().getResources().getDimensionPixelSize(R.dimen.list_item_height);
    }

    @Override
    protected void tearDown() throws Exception {
        getActivity().finish();
        super.tearDown();
        solo.sleep(1000);
    }

    protected void dragCenterInScreen(int dx, int dy) {
        float x1 = metrics.widthPixels / 2f - dx / 2f;
        float x2 = metrics.widthPixels / 2f + dx / 2f;
        float y1 = metrics.heightPixels / 2f - dy / 2f;
        float y2 = metrics.heightPixels / 2f + dy / 2f;
        int step = (int) (Math.sqrt((dx * dx) + (dy * dy)) / 10);
        solo.drag(x1, x2, y1, y2, step);
    }

    protected void dragListItemBy(float count) {
        dragCenterInScreen(0, (int) (count * listItemHeight));
    }

    protected void testCommonEventWith(int resID, String message) throws Exception {
        pollEventFromLog();
        setExpectEvent(resID);
        testCommonEvent(message);
    }

    protected void testPageEventWith(int resID, String message) throws Exception {
        pollEventFromLog();
        setExpectEvent(resID);
        testCommonPageEvent(message);
    }

    protected void setExpectEvent(int rawID) {
        mExpect = GrowingLogUtil.getInstance().parseLog(rawID);
    }

    protected void pollEventFromLog() throws Exception {
        solo.sleep(500);
        mEvent = GrowingLogUtil.getInstance().pollEvent();
        if (TextUtils.equals("vst", mEvent.optString("t"))) {
            JSONObject currentExpect = mExpect;
            setExpectEvent(R.raw.visit_event);
            testCommonVisitEvent("App Visit");
            mExpect = currentExpect;
            mEvent = GrowingLogUtil.getInstance().pollEvent();
        }
    }

    protected void testCommonVisitEvent(String message) throws Exception {
        sessionID = mEvent.getString("s");
        assertStringMemberEqual(message + " Platform", "b");
        assertStringMemberEqual(message + " Channel", "ch");
    }

    protected void testCommonPageEvent(String message) throws Exception {
        assertBaseInfo(message);
        assertStringMemberEqual(message + " Title", "tl");
    }


    protected void testCommonEvent(String message) throws Exception {
        assertBaseInfo(message);
        assertXPathEqual(message);
    }

    protected void assertXPathEqual(String message) throws Exception {
        JSONArray eventXPath = mEvent.getJSONArray("e");
        JSONArray expectXPath = mExpect.getJSONArray("e");
        assertEquals(message + " Node Count", expectXPath.length(), eventXPath.length());
        for (int i = eventXPath.length() - 1; i >= 0; i--) {
            JSONObject expectObject = expectXPath.getJSONObject(i);
            JSONObject eventObject = eventXPath.getJSONObject(i);
            assertStringMemberEqual(message + " XPATH", expectObject, eventObject, "x");
            assertStringMemberEqual(message + " Value", expectObject, eventObject, "v");
            assertStringMemberEqual(message + " Class", expectObject, eventObject, "n");
        }
    }

    protected void assertBaseInfo(String message) throws Exception {
        assertStringMemberEqual(message + " Page", "p");
        assertStringMemberEqual(message + " Type", "t");
        assertStringMemberEqual(message + " Package", "d");
    }

    protected void assertStringMemberEqual(String message, JSONObject o1, JSONObject o2, String member) throws Exception {
        if (o1.opt(member) == o2.opt(member)) return;
        assertEquals(message, o1.getString(member), o2.getString(member));
    }

    protected void assertStringMemberEqual(String message, String member) throws Exception {
        assertStringMemberEqual(message, mExpect, mEvent, member);
    }
}
