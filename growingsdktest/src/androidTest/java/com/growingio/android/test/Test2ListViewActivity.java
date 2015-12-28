package com.growingio.android.test;

/**
 * Created by lishaojie on 15/12/26.
 */
public class Test2ListViewActivity extends ActivityTestBase {

    public Test2ListViewActivity() {
        super(ListViewActivity.class);
    }

    private void assertPageEvent() throws Exception {
        pollEventFromLog();
        setExpectEvent(R.raw.listview_page_event);
        testCommonPageEvent("ListView Page");
        pollEventFromLog();
        setExpectEvent(R.raw.listview_page_impression);
        testCommonEvent("ListView Page Impression");
    }

    public void test1ListViewPage() throws Exception {
        assertPageEvent();
    }

    public void test2ClickOnListView() throws Exception {
        assertPageEvent();
        solo.clickInList(3);
        pollEventFromLog();
        setExpectEvent(R.raw.listview_third_line_click);
        testCommonEvent("ListView ThirdLine Click");
        solo.clickInList(1);
        pollEventFromLog();
        setExpectEvent(R.raw.listview_first_line_click);
        testCommonEvent("ListView FirstLine Click");
    }

    public void test3SmoothScroll() throws Exception {
        assertPageEvent();
        dragListItemBy(-3.6f);
        pollEventFromLog();
        setExpectEvent(R.raw.listview_scroll_impression);
        testCommonEvent("ListView Scroll Down");
        solo.clickInList(3);
        pollEventFromLog();
        setExpectEvent(R.raw.listview_sixth_line_click);
        testCommonEvent("ListView SixthLine Click");
    }

}