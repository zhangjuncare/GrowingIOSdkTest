package com.growingio.android.test.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by lishaojie on 15/12/24.
 */
public class GrowingLogUtil {
    private static final int EVENT_LOG_LINE_PREFIX_LENGTH = 13;
    private static final DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final File ROOT_DIR = new File(Environment.getExternalStorageDirectory(), "GrowingIO" + File.separator);
    private static final File sDir = new File(ROOT_DIR, "AutoTestLog" + File.separator);
    private static GrowingLogUtil sInstance;
    private Context sContext;
    private Queue<JSONObject> mEventLogQueue;
    private BufferedReader mEventLogReader;
    private String mReadingLogPath;

    public static GrowingLogUtil getInstance() {
        if (sInstance == null) sInstance = new GrowingLogUtil();
        return sInstance;
    }

    public void init(Context context) {
        sContext = context.getApplicationContext();
    }

    public String getEventLogPath() {
        return String.format("%s%s%s_%s.log", sDir.getAbsolutePath(), File.separator, "GrowingSdkTest",
                mDateFormat.format(Calendar.getInstance().getTimeInMillis()));
    }

    public void prepareLogQueue() {
        String path = getEventLogPath();
        if (TextUtils.equals(path, mReadingLogPath)) return;
        File logFile = new File(path);
        File logDir = new File(logFile.getParent());
        logDir.mkdirs();
        try {
            logFile.delete();
            logFile.createNewFile();
        } catch (IOException ignored) {
        }
        try {
            FileInputStream stream = new FileInputStream(logFile);
            mEventLogReader = new BufferedReader(new InputStreamReader(stream));
            mEventLogQueue = new LinkedList<>();
            mReadingLogPath = path;
        } catch (IOException ignored) {
        }
    }

    public JSONObject pollEvent() throws IOException, JSONException {
        String newLine = mEventLogReader.readLine();
        int retryCount = 5;
        while (newLine == null) {
            try {
                Thread.sleep(1000);
                newLine = mEventLogReader.readLine();
                if (--retryCount == 0) {
                    break;
                }
            } catch (InterruptedException ignored) {
            }
        }
        if (newLine != null) {
            mEventLogQueue.add(new JSONObject(newLine.substring(EVENT_LOG_LINE_PREFIX_LENGTH)));
        }
        return mEventLogQueue.poll();
    }

    public static JSONObject parseLogFile(String path) {
        try {
            return parseLog(new FileInputStream(path));
        } catch (FileNotFoundException ignored) {
        }
        return null;
    }

    public static JSONObject parseLog(InputStream stream) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        try {
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
            stream.close();
            return new JSONObject(stringBuilder.toString());
        } catch (IOException | JSONException ignored) {
        }
        return null;
    }

    public JSONObject parseLog(int rawId) {
        return parseLog(sContext.getResources().openRawResource(rawId));
    }

}
