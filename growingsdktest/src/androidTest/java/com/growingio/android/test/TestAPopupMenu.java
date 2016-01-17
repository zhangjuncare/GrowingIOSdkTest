package com.growingio.android.test;

/**
 * Created by lishaojie on 15/12/29.
 */
public class TestAPopupMenu extends ActivityTestBase {
    public TestAPopupMenu() {
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
        testCommonEventWith(R.raw.imageview_in_main_page_click, "ImageView In Main Page Click");
        testCommonEventWith(R.raw.popupmenu_impression, "PopupMenu Impression");
        solo.clickOnText("Item 2");
        testCommonEventWith(R.raw.popupmenu_second_item_click, "PopupMenu Second Item Click");
    }

}
