package com.threesome.function.utils;

import android.util.Log;

/**
 * author: L.K.X
 * created on: 2017/7/14 下午5:47
 * description:
 */

public class Sout {
    public static void init(String tag, boolean isDebug) {
        TAG = tag;
        IS_DEBUG = isDebug;
    }
    private static String TAG = "System.out";
    private static Boolean IS_DEBUG = false;
    
    public static void print(String msg) {
        if (IS_DEBUG) {
            Log.v(TAG, msg);
        }
    }
}
