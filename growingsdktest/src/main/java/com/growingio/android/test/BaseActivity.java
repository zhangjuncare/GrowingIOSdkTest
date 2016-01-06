package com.growingio.android.test;

import android.app.Activity;
import android.os.Bundle;

import com.growingio.android.sdk.collection.GrowingIO;

/**
 * Created by lishaojie on 15/12/26.
 */
public class BaseActivity extends Activity {
    protected String TAG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        GrowingIO growing = GrowingIO.startTracing(this, "92927f034cc5407dbdfb786f023bda58");
        if (growing != null) {
            growing.setChannel("growingio_sdk_test_only");
        }
    }
}
