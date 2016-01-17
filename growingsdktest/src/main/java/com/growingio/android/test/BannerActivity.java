package com.growingio.android.test;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.LinearLayout.LayoutParams;


public class BannerActivity extends BaseActivity implements OnPageChangeListener {

    private List<ImageView> mImageViews;
    private TextView tv;
    private LinearLayout ll;
    private int preEnablePosition = 0;
    private String[] imageDescription = {"描述1", "描述2", "描述3", "描述4", "描述5"};
    private ViewPager viewPager;
    private boolean isStop = false;
    private static int[] sLocationOnScreenBuffer = new int[2];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);


        init();
        mGrowingIO.trackBanner(viewPager, Arrays.asList(imageDescription));
//        mGrowingIO.ignoredView(viewPager);

        Thread myThread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (!isStop) {
                    SystemClock.sleep(3000);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            int newIndex = viewPager.getCurrentItem() + 1;
                            viewPager.setCurrentItem(newIndex);
                        }
                    });
                }
            }
        });

        viewPager.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < viewPager.getChildCount(); i++) {
                    View view = viewPager.getChildAt(i);
                    view.getLocationOnScreen(sLocationOnScreenBuffer);
                    Log.i(TAG, String.format("Location %d %d", sLocationOnScreenBuffer[0], sLocationOnScreenBuffer[1]));
                    Log.i(TAG, String.format("Is visible to user: %b", isVisible(view)));
                }
            }
        });

        myThread.start();

    }

    @Override
    protected void onDestroy() {
        isStop = true;
        super.onDestroy();
    }

    private void init() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ll = (LinearLayout) findViewById(R.id.ll_point_group);
        tv = (TextView) findViewById(R.id.tv_image_miaoshu);

        mImageViews = new ArrayList<ImageView>();
        int[] imageIDs = {R.drawable.banner_1, R.drawable.banner_2, R.drawable.banner_3,
                R.drawable.banner_4, R.drawable.banner_5,};


        ImageView iv;
        View view;
        LayoutParams params;
        for (int id : imageIDs) {
            iv = new ImageView(this);
            iv.setBackgroundResource(id);
            mImageViews.add(iv);

            view = new View(this);
            view.setBackgroundResource(R.drawable.point_background);
            params = new LayoutParams(5, 5);
            params.leftMargin = 5;
            view.setEnabled(false);
            view.setLayoutParams(params);
            ll.addView(view);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(BannerActivity.this, "image click", Toast.LENGTH_SHORT).show();
                }
            });
        }

        viewPager.setAdapter(new MyAdapter());
        viewPager.setOnPageChangeListener(this);

        tv.setText(imageDescription[0]);
        ll.getChildAt(0).setEnabled(true);

        int index = (Integer.MAX_VALUE / 2)
                - ((Integer.MAX_VALUE / 2) % mImageViews.size());
        viewPager.setCurrentItem(index);


    }

    class MyAdapter extends PagerAdapter {


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mImageViews.get(position % mImageViews.size()));
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViews.get(position % mImageViews.size()));
            return mImageViews.get(position % mImageViews.size());
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return Integer.MAX_VALUE;
        }


        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub
        for (int i = 0; i < viewPager.getChildCount(); i++) {
            View view = viewPager.getChildAt(i);
            view.getLocationOnScreen(sLocationOnScreenBuffer);
            Log.i(TAG, String.format("Location %d %d", sLocationOnScreenBuffer[0], sLocationOnScreenBuffer[1]));
            Log.i(TAG, String.format("Is visible to user: %b", isVisible(view)));
            Log.i(TAG, String.format("Current item: %d", viewPager.getCurrentItem()));
        }
    }

    @Override
    public void onPageSelected(int position) {
        int newPosition = position % mImageViews.size();
        tv.setText(imageDescription[newPosition]);
        ll.getChildAt(preEnablePosition).setEnabled(false);
        ll.getChildAt(newPosition).setEnabled(true);
        preEnablePosition = newPosition;
    }

    public static boolean isVisible(View view) {
        if (view.getVisibility() == View.VISIBLE
                && view.getWidth() > 0
                && view.getHeight() > 0
                && view.getAlpha() > 0) {
            Rect visibleRect = new Rect();
            return view.getLocalVisibleRect(visibleRect);
        }
        return false;
    }

}
