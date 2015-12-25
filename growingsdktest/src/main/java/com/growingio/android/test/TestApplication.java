package com.growingio.android.test;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.growingio.android.test.util.GrowingLogUtil;
import com.growingio.android.test.util.WindowHelper;

import org.json.JSONObject;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by lishaojie on 15/12/24.
 */
public class TestApplication extends Application {

    private static final String TAG = "TestApp";
    private DateFormat mTimeFormat = new SimpleDateFormat("HH:mm:ss.SSS");
    private DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final File ROOT_DIR = new File(Environment.getExternalStorageDirectory(), "GrowingIO" + File.separator);
    private File sDir = new File(ROOT_DIR, "AutoTestLog" + File.separator);
    public static String sLogFilePath;

    @Override
    public void onCreate() {
        super.onCreate();

        sLogFilePath = String.format("%s%s%s_%s.log", sDir.getAbsolutePath(), File.separator, "GrowingSdkTest",
                mDateFormat.format(Calendar.getInstance().getTimeInMillis()));
        File file = new File(sLogFilePath);
        if (file.exists()) {
            GrowingLogUtil.init(this);
            ArrayList<JSONObject> array = GrowingLogUtil.parseLogFile(sLogFilePath);
            file.delete();
        }
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(final Activity activity) {
                new Handler().post(new Runnable() {
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

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }
}
