package com.snow.app.smartsmsfilter.util;

import android.util.Log;

/**
 * Created by Administrator on 2016.03.08.
 */
public class LogUtil {
    private static int VERBOSE = 1;
    private static int DEBUG = 2;
    private static int INFO = 3;
    private static int WARN = 4;
    private static int ERROR = 5;
    private static int NOTHING = 6;
    private static int CURRENT_LEVEL = VERBOSE;

    public static void v(String tag, String msg) {
        if (CURRENT_LEVEL <= VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (CURRENT_LEVEL <= DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (CURRENT_LEVEL <= INFO) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (CURRENT_LEVEL <= WARN) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (CURRENT_LEVEL <= ERROR) {
            Log.e(tag, msg);
        }
    }
}
