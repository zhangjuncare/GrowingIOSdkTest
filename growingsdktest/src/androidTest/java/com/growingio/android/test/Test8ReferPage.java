package com.growingio.android.test;

import android.content.Intent;

/**
 * Created by lishaojie on 15/12/29.
 */
public class Test8ReferPage extends ActivityTestBase {
    public Test8ReferPage() {
        super(SplashActivity.class);
    }

    public void testReferPage() throws Exception {
        testPageEventWith(R.raw.splash_page_event, "Splash Page");
        testCommonEventWith(R.raw.splash_impression, "Splash Impression");
        getActivity().startActivity(new Intent(getActivity(), PopupWindowActivity.class));
        testPageEventWith(R.raw.popupwindow_page_event, "PopupWindow Page");
        assertEquals(mEvent.getString("rp"), "SplashActivity");
        testCommonEventWith(R.raw.popupwindow_page_impression, "PopupWindow Page Impression");
    }
}
