package com.growingio.android.test;

/**
 * Created by xyz on 16/1/15.
 */
public class TestBBanner extends ActivityTestBase {

    public TestBBanner() {
        super(BannerActivity.class);
    }

    private void assertPageEvent() throws Exception {
        pollEventFromLog();
        setExpectEvent(R.raw.banner_page);
        testCommonPageEvent("Banner Page");
        pollEventFromLog();
        setExpectEvent(R.raw.banner_imp1);
        testCommonEvent("Banner Page Impression");
    }

    public void test1BannerImpressAndClick() throws Exception {
        assertPageEvent();
        solo.waitForText("描述2");
        testCommonEventWith(R.raw.banner_imp2, "Second Banner Show");
        solo.waitForText("描述3");
        solo.clickOnImage(1);
        testCommonEventWith(R.raw.banner_imp3, "Third Banner Show");
        testCommonEventWith(R.raw.banner_clck, "Third Banner Click");
    }

}
