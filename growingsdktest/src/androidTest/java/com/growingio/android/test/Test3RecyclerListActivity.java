package com.growingio.android.test;

/**
 * Created by lishaojie on 15/12/26.
 */
public class Test3RecyclerListActivity extends ActivityTestBase {

    public Test3RecyclerListActivity() {
        super(RecyclerListActivity.class);
    }

    private void assertPageEvent() throws Exception {
        pollEventFromLog();
        setExpectEvent(R.raw.recycler_page_event);
        testCommonPageEvent("Recycler Page");
        pollEventFromLog();
        setExpectEvent(R.raw.recycler_page_impression);
        testCommonEvent("Recycler Page Impression");
    }

    public void test1RecyclerPage() throws Exception {
        assertPageEvent();
    }

    public void test2ClickOnListView() throws Exception {
        assertPageEvent();
        solo.clickInRecyclerView(2);
        pollEventFromLog();
        setExpectEvent(R.raw.recycler_third_line_click);
        testCommonEvent("Recycler ThirdLine Click");
        solo.clickInRecyclerView(0);
        pollEventFromLog();
        setExpectEvent(R.raw.recycler_first_line_click);
        testCommonEvent("Recycler FirstLine Click");
    }

    public void test3SmoothScroll() throws Exception {
        assertPageEvent();
        dragListItemBy(-3.5f);
        pollEventFromLog();
        setExpectEvent(R.raw.recycler_scroll_impression);
        testCommonEvent("Recycler Scroll Down");
        solo.clickInRecyclerView(2);
        pollEventFromLog();
        setExpectEvent(R.raw.recycler_sixth_line_click);
        testCommonEvent("Recycler SixthLine Click");
    }
}