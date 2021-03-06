package com.iocm.freetime.util;

import android.util.Log;

/**
 * Created by liubo on 15/6/22.
 */
public class TLog {
    public static void i(String tag, String message) {
        Log.i(tag, message);
    }

    public static void d(String tag, String message) {
        Log.d(tag, message);
    }

    public static void w(String tag, String message) {
        Log.w(tag, message);
    }

    public static void e(String tag, String message) {
        Log.e(tag, message);
    }
}
