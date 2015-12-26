package com.growingio.android.test;

/**
 * Created by lishaojie on 15/12/26.
 */
public class Test4MainActivity extends ActivityTestBase {
    public Test4MainActivity() {
        super(MainActivity.class);
    }

    private void assertPageEvent() throws Exception {
        pollEventFromLog();
        setExpectEvent(R.raw.main_page_event);
        testCommonPageEvent("Main Page");
        pollEventFromLog();
        setExpectEvent(R.raw.main_page_impression);
        testCommonEvent("Main Page Impression");
    }

    public void test1MainPage() throws Exception {
        assertPageEvent();
    }

    public void test2MainPageClick() throws Exception {
        assertPageEvent();
        solo.clickOnView(solo.getView(R.id.button));
        pollEventFromLog();
        setExpectEvent(R.raw.change_text_button_click);
        testCommonEvent("ChangeText Button Click");
        solo.clickOnView(solo.getView(R.id.button));
        pollEventFromLog();
        setExpectEvent(R.raw.test_textview_change_impression);
        testCommonEvent("Text Change Impression");
        pollEventFromLog();
        setExpectEvent(R.raw.change_text_button_click);
        testCommonEvent("ChangeText Button Click");
    }

}
