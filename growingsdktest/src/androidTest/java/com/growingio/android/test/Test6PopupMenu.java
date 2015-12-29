package com.growingio.android.test;

/**
 * Created by lishaojie on 15/12/29.
 */
public class Test6PopupMenu extends ActivityTestBase {
    public Test6PopupMenu() {
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

    public void test1PopupMenu() throws Exception {
        assertPageEvent();
        solo.clickOnView(solo.getView(R.id.imageView2));
        testEventWith(R.raw.imageview_in_main_page_click, "ImageView In Main Page Click");
        testEventWith(R.raw.popupmenu_impression, "PopupMenu Impression");
        solo.clickOnText("Item 2");
        testEventWith(R.raw.popupmenu_second_item_click, "PopupMenu Second Item Click");
    }

}
