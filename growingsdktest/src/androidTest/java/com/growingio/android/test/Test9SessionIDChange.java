package com.growingio.android.test;

import android.text.TextUtils;
import android.util.Log;

import com.growingio.android.test.util.GrowingLogUtil;

/**
 * Created by lishaojie on 15/12/31.
 */
public class Test9SessionIDChange extends ActivityTestBase {

    public Test9SessionIDChange() {
        super(SplashActivity.class);
    }

    public void test1SaveChangedSessionID() throws Exception {
        testPageEventWith(R.raw.splash_page_event, "Splash Page");
        testCommonEventWith(R.raw.splash_impression, "Splash Impression");
        assertTrue("No Valid Session ID", !TextUtils.isEmpty(sessionID));
    }

    public void test2VerifySessionIDChange() throws Exception {
        mEvent = GrowingLogUtil.getInstance().pollEvent();
        assertNotNull("New Visit Event", mEvent);
        assertEquals("vst", mEvent.getString("t"));
        String newSessionID = mEvent.getString("s");
        Log.i(TAG, "Old session id: " + sessionID + " new session id: " + newSessionID);
        assertTrue("New Session ID Identical With Old ID", !TextUtils.equals(sessionID, newSessionID));
        testPageEventWith(R.raw.splash_page_event, "Splash Page");
        testCommonEventWith(R.raw.splash_impression, "Splash Impression");
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        if (this.toString().contains("test1SaveChangedSessionID")) {
            solo.sleep(3000);
        }
    }
}
