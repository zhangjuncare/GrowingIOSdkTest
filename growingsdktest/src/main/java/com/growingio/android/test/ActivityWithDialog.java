package com.growingio.android.test;

import android.app.AlertDialog;
import android.os.Bundle;

/**
 * Created by lishaojie on 15/12/26.
 */
public class ActivityWithDialog extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Dialog title")
                .setMessage("this is dialog message")
                .setPositiveButton("Positive", null)
                .setNegativeButton("Negative", null)
                .setNeutralButton("Neutral", null)
                .create();
        dialog.show();
    }
}
