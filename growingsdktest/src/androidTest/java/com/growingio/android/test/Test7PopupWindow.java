package com.growingio.android.test;

/**
 * Created by lishaojie on 15/12/29.
 */
public class Test7PopupWindow extends ActivityTestBase {
    public Test7PopupWindow() {
        super(PopupWindowActivity.class);
    }

    public void testPopupWindow() throws Exception {
        testPageEventWith(R.raw.popupwindow_page_event, "PopupWindow Page");
        testCommonEventWith(R.raw.popupwindow_page_impression, "PopupWindow Page Impression");
        solo.clickOnText("show popup window");
        solo.sleep(500);
        solo.clickOnView(solo.getView(R.id.splash_image));
        testCommonEventWith(R.raw.show_popupwindow_click, "Show PopupWindow Click");
        testCommonEventWith(R.raw.popupwindow_impression, "PopupWindow Impression");
        testCommonEventWith(R.raw.imageview_in_popupwindow_click, "ImageView In PopupWindow Click");
    }

    public void testClickOnViewGroup() throws Exception {
        testPageEventWith(R.raw.popupwindow_page_event, "PopupWindow Page");
        testCommonEventWith(R.raw.popupwindow_page_impression, "PopupWindow Page Impression");
        solo.clickOnScreen(metrics.widthPixels / 4, metrics.heightPixels / 4);
        testCommonEventWith(R.raw.popupwindow_page_top_left_layout_click, "Top Left Layout Click");
        solo.clickOnScreen(metrics.widthPixels / 4 * 3, metrics.heightPixels / 4);
        testCommonEventWith(R.raw.popupwindow_page_top_layout_click, "Top Layout Click");
    }
}
