package com.growingio.android.test.util;

import android.os.Build;
import android.view.View;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by xyz on 15/11/12.
 */
public class WindowHelper {

    private static Object sWindowManger;
    private static Field viewsField;
    private static Class sPhoneWindowClazz;
    private static Class sPopupWindowClazz;
    public static final String sMainWindowPrefix = "MainWindow";
    public static final String sDialogWindowPrefix = "DialogWindow";
    public static final String sPopupWindowPrefix = "PopupWindow";
    public static final String sCustomWindowPrefix = "CustomWindow";

    public static Comparator<View> sViewSizeComparator = new Comparator<View>() {
        @Override
        public int compare(View lhs, View rhs) {
            return rhs.getWidth() * rhs.getHeight() - lhs.getWidth() * lhs.getHeight();
        }
    };

    public static void init() {
        String windowManagerClassName;
        if (Build.VERSION.SDK_INT >= 17) {
            windowManagerClassName = "android.view.WindowManagerGlobal";
        } else {
            windowManagerClassName = "android.view.WindowManagerImpl";
        }

        Class<?> windowManager = null;
        try {
            windowManager = Class.forName(windowManagerClassName);

            String windowManagerString;
            if (Build.VERSION.SDK_INT >= 17) {
                windowManagerString = "sDefaultWindowManager";
            } else if (Build.VERSION.SDK_INT >= 13) {
                windowManagerString = "sWindowManager";
            } else {
                windowManagerString = "mWindowManager";
            }

            viewsField = windowManager.getDeclaredField("mViews");

            Field instanceField = windowManager.getDeclaredField(windowManagerString);
            viewsField.setAccessible(true);
            instanceField.setAccessible(true);
            sWindowManger = instanceField.get(null);

        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        } catch (ClassNotFoundException e) {
        }

        try {
            if (Build.VERSION.SDK_INT == 23) {
                sPhoneWindowClazz = Class.forName("com.android.internal.policy.PhoneWindow$DecorView");
            } else {
                sPhoneWindowClazz = Class.forName("com.android.internal.policy.impl.PhoneWindow$DecorView");
            }
        } catch (ClassNotFoundException ignored) {
        }
        try {
            if (Build.VERSION.SDK_INT == 23) {
                sPopupWindowClazz = Class.forName("android.widget.PopupWindow$PopupDecorView");
            } else {
                sPopupWindowClazz = Class.forName("android.widget.PopupWindow$PopupViewContainer");
            }
        } catch (ClassNotFoundException ignored) {
        }

    }

    public static View[] getWindowViews() {
        View[] result = new View[0];
        try {
            if (Build.VERSION.SDK_INT >= 19) {
                result = ((ArrayList<View>) viewsField.get(sWindowManger)).toArray(result);
            } else {
                result = (View[]) viewsField.get(sWindowManger);
            }
        } catch (IllegalAccessException ignored) {
        }
        return result;
    }

    public static View[] getSortedWindowViews() {
        View[] views = getWindowViews();
        if (views.length > 1) {
            views = Arrays.copyOf(views, views.length);
            Arrays.sort(views, sViewSizeComparator);
        }
        return views;
    }

    public static String getMainWindowPrefix() {
        return sMainWindowPrefix;
    }

    public static String getSubWindowPrefix(View root) {
        Class rootClazz = root.getClass();
        if (rootClazz == sPhoneWindowClazz) {
            return sDialogWindowPrefix;
        } else if (rootClazz == sPopupWindowClazz) {
            return sPopupWindowPrefix;
        } else {
            return sCustomWindowPrefix;
        }
    }

    /**
     * Returns the most recent view container
     *
     * @param views the views to check
     * @return the most recent view container
     */

    private View getRecentContainer(View[] views) {
        View container = null;
        long drawingTime = 0;
        View view;

        for (View view1 : views) {
            view = view1;
            if (view != null && view.isShown() && view.hasWindowFocus() && view.getDrawingTime() > drawingTime) {
                container = view;
                drawingTime = view.getDrawingTime();
            }
        }
        return container;
    }

    public static boolean isDecorView(View root) {
        return root.getClass() == sPhoneWindowClazz;
    }
}
