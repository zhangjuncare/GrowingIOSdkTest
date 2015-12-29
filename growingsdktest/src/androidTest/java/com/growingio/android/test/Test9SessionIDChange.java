package com.growingio.android.test;

import android.text.TextUtils;
import android.util.Log;

import com.growingio.android.test.util.GrowingLogUtil;
import com.robotium.solo.Solo;

/**
 * Created by lishaojie on 15/12/29.
 */
public class Test9SessionIDChange extends ActivityTestBase {
    public Test9SessionIDChange() {
        super(SplashActivity.class);
    }

    public void testSessionIDChange() throws Exception {
        testPageEventWith(R.raw.splash_page_event, "Splash Page");
        testCommonEventWith(R.raw.splash_impression, "Splash Impression");
        assertNotNull(sessionID);
        solo.goBack();
        setActivity(null);
        solo.sleep(3500);
        solo = new Solo(getInstrumentation(), getActivity());
        mEvent = GrowingLogUtil.getInstance().pollEvent();
        assertNotNull(mEvent);
        String newSessionID = mEvent.getString("s");
        Log.i(TAG, "Old session id: "+sessionID+" new session id: "+newSessionID);
        assertTrue("New Session ID", !TextUtils.equals(sessionID, newSessionID));
        testPageEventWith(R.raw.splash_page_event, "Splash Page");
        testCommonEventWith(R.raw.splash_impression, "Splash Impression");
    }
}
