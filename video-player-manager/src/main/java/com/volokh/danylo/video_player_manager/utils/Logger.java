package com.volokh.danylo.video_player_manager.utils;

import android.util.Log;

public class Logger {

    public static int err(final String TAG, final String message) {
        return Log.e(TAG, attachThreadId(message));
    }

    public static int e( final String message) {
        return Log.e("CLog", message);
    }

    public static int err(final String TAG, final String message, Throwable throwable) {
        return Log.e(TAG, attachThreadId(message), throwable);
    }

    public static int w(final String TAG, final String message) {
        return 0;
    }

    public static int inf(final String TAG, final String message) {
        return Log.e("CLog", message);
    }

    public static int d(final String TAG, final String message) {
        return 0;
    }

    public static int v(final String TAG, final String message) {
        return 0;
    }

    private static String attachThreadId(String str){
        return "" + Thread.currentThread().getId() + " " + str;
    }

}
