package com.growingio.android.test;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;

import com.growingio.android.sdk.collection.GrowingIO;
import com.growingio.android.test.util.GrowingLogUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        GrowingIO growing = GrowingIO.startTracing(this, "meaningless_application_id");
        if (growing != null) {
            growing.setChannel("growingio_sdk_test_only");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10000);
        } else {
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }, 1000);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 10000) {
            boolean granted = grantResults.length > 0;
            for (int g : grantResults) {
                if (g != PackageManager.PERMISSION_GRANTED) {
                    granted = false;
                }
            }
            if (granted) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }
    }
}
