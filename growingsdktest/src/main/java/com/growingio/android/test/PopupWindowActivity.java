package com.growingio.android.test;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

/**
 * Created by lishaojie on 15/12/29.
 *
 */
public class PopupWindowActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow_activity);
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupWindow window = new PopupWindow(PopupWindowActivity.this);
                ImageView imageView = new ImageView(PopupWindowActivity.this);
                imageView.setImageResource(R.drawable.splash);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setId(R.id.splash_image);
                window.setContentView(imageView);
                window.setWidth(getResources().getDimensionPixelSize(R.dimen.popup_window_size));
                window.setHeight(getResources().getDimensionPixelSize(R.dimen.popup_window_size));
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        window.dismiss();
                    }
                });
                window.showAsDropDown(v);
            }
        });
    }
}
