package com.growingio.android.test;

/**
 * Created by lishaojie on 15/12/24.
 */
public class Test1SplashActivity extends ActivityTestBase {

    public Test1SplashActivity() {
        super(SplashActivity.class);
    }

    public void test_SplashActivity_Show_GenPageEvent() throws Exception {
        pollEventFromLog();
        setExpectEvent(R.raw.splash_page_event);
        testCommonPageEvent("Splash Page");
        pollEventFromLog();
        setExpectEvent(R.raw.splash_impression);
        testCommonEvent("Splash Impression");
    }




}
