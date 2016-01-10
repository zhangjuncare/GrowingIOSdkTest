package com.growingio.android.test;

import android.test.ActivityInstrumentationTestCase2;

import com.growingio.android.test.util.GrowingLogUtil;

/**
 * Created by lishaojie on 16/1/10.
 */
public class TestZLastCheckTest extends ActivityInstrumentationTestCase2 {
    public TestZLastCheckTest() {
        super(SplashActivity.class);
    }

    public void testCheckNoMoreData() throws Exception {
        assertNull("Event Data Leaked!", GrowingLogUtil.getInstance().pollEvent());
    }
}
