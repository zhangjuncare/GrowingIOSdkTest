package com.growingio.android.test.util;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by lishaojie on 15/12/24.
 */
public class GrowingLogUtil {
    private static Context sContext;

    public static void init(Context context) {
        sContext = context.getApplicationContext();
    }

    public static ArrayList<JSONObject> parseLogFile(String path) {
        try {
            return parseLog(new FileInputStream(path));
        } catch (FileNotFoundException e) {
        }
        return null;
    }

    public static ArrayList<JSONObject> parseLog(InputStream stream) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        ArrayList<JSONObject> list = new ArrayList<JSONObject>();
        try {
            String line = bufferedReader.readLine();
            while (line != null) {
                JSONObject object = new JSONObject(line.substring(13));
                list.add(object);
                line = bufferedReader.readLine();
            }
            stream.close();
        } catch (IOException | JSONException ignored) {
        }
        return list;
    }

    public static ArrayList<JSONObject> parseLog(int rawId) {
        return parseLog(sContext.getResources().openRawResource(rawId));
    }

}
