package com.growingio.android.test;

import android.app.Activity;
import android.os.Bundle;

import com.growingio.android.sdk.collection.GrowingIO;

/**
 * Created by lishaojie on 15/12/26.
 */
public class BaseActivity extends Activity {
    protected String TAG;
    GrowingIO mGrowingIO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        mGrowingIO = GrowingIO.startTracing(this, "meaningless_application_id");
        if (mGrowingIO != null) {
            mGrowingIO.setChannel("growingio_sdk_test_only");
        }
    }
}
