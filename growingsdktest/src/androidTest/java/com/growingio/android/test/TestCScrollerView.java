package com.growingio.android.test;

/**
 * Created by xyz on 16/1/17.
 */
public class TestCScrollerView extends ActivityTestBase {
    public TestCScrollerView() {
        super(ScrollerActivity.class);
    }

    private void assertPageEvent() throws Exception {
        pollEventFromLog();
        setExpectEvent(R.raw.scrollerview_page);
        testCommonPageEvent("ScrollerView Page Open");
        pollEventFromLog();
        setExpectEvent(R.raw.scrollerview_init_imp);
        testCommonEvent("ScrollerView Page Init Imp");
    }

    public void test1ScrollerViewScroll() throws Exception {
        assertPageEvent();
        solo.scrollToBottom();
        testCommonEventWith(R.raw.scrollerview_scroll_imp, "ScrollerView scroll");
    }


}
