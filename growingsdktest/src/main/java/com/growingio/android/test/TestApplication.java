package com.growingio.android.test;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.growingio.android.sdk.collection.GrowingIO;
import com.growingio.android.test.util.GrowingLogUtil;
import com.growingio.android.test.util.WindowHelper;

/**
 * Created by lishaojie on 15/12/24.
 */
public class TestApplication extends Application {

    private static final String TAG = "TestApp";
    private Handler mHandler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
        GrowingLogUtil.getInstance().init(this);
        GrowingLogUtil.getInstance().prepareLogQueue();
        Log.i(TAG, "created " + this);
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.i(TAG, "created " + activity);
            }

            @Override
            public void onActivityStarted(final Activity activity) {
                Log.i(TAG, "started " + activity);
                WindowHelper.init();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, activity + "'s decor: " + activity.getWindow().getDecorView());
                        View[] views = WindowHelper.getWindowViews();
                        for (View v : views) {
                            Log.i(TAG, v.toString());
                        }
                    }
                });

            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.i(TAG, "resumed " + activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.i(TAG, "paused " + activity);
            }

            @Override
            public void onActivityStopped(final Activity activity) {
                Log.i(TAG, "stoped " + activity);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, activity + "'s decor: " + activity.getWindow().getDecorView());
                        View[] views = WindowHelper.getWindowViews();
                        for (View v : views) {
                            Log.i(TAG, v.toString());
                        }
                    }
                });
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.i(TAG, "destroyed " + activity);
            }
        });
    }
}
