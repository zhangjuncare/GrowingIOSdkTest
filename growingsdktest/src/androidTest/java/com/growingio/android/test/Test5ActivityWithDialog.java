package com.growingio.android.test;

/**
 * Created by lishaojie on 15/12/26.
 */
public class Test5ActivityWithDialog extends ActivityTestBase {
    public Test5ActivityWithDialog() {
        super(ActivityWithDialog.class);
    }

    public void testPageWithDialog() throws Exception {
        pollEventFromLog();
        setExpectEvent(R.raw.activity_dialog_page_event);
        testCommonPageEvent("Activity With Dialog Page");
        pollEventFromLog();
        setExpectEvent(R.raw.activity_with_dialog_impression);
        testCommonEvent("Activity OnCreate Dialog Impression");
        pollEventFromLog();
        setExpectEvent(R.raw.alertdialog_impression);
        testCommonEvent("AlertDialog Simple Impression");
        solo.clickOnText("Negative");
        pollEventFromLog();
        setExpectEvent(R.raw.alertdialog_negative_button_click);
        testCommonEvent("AlertDialog Negative Button Click");
    }
}
