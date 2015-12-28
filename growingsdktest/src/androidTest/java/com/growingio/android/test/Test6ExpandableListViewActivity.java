package com.growingio.android.test;

/**
 * Created by xyz on 15/12/27.
 */
public class Test6ExpandableListViewActivity extends ActivityTestBase {

    public Test6ExpandableListViewActivity() {
        super(ExpandableListViewActivity.class);
    }

    public void test1_ActivityShowShouldGenPageAndImp() throws Exception {
        pollEventFromLog();
        setExpectEvent(R.raw.expandablelistview_page_event);
        testCommonPageEvent("ExpandableListView Page");
        pollEventFromLog();
        setExpectEvent(R.raw.expandablelistview_impression_event);
        testCommonEvent("ExpandableListView Page Impression");
    }

    public void test2_ClickOnGroupItem1() throws Exception {
        pollEventFromLog();
        pollEventFromLog();
        solo.clickInList(2);
        testEvent("click on group item 1", R.raw.expandablelistview_click_group1);
        solo.sleep(300);
        testEvent("impression after click on group item 1", R.raw.expandablelistview_impress_after_click_group1);
    }

    public void test3_ClickOnChildItem1InGroup1() throws Exception {
        // skip page and imp
        pollEventFromLog();
        pollEventFromLog();
        solo.clickInList(2);
        // skip group click and new imp
        pollEventFromLog();
        pollEventFromLog();
        solo.clickInList(4);
        testEvent("click on child item 1 in group 1", R.raw.expandablelistview_click_child1_in_group1);
    }


}
