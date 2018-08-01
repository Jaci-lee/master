package com.canbot.u05.sdk.clientdemo.util;

import android.util.Log;

/**
 * @author xiaodong
 * @date 20150617
 */
public class Logger {

        private static final String TAG = "U05_";

        public static boolean DEBUG = true;

        public static void i(String msg) {
                if (DEBUG) {
                        Log.i(TAG, msg);
                }
        }

        public static void v(String msg) {
                if (DEBUG) {
                        Log.v(TAG, msg);
                }
        }

        public static void i(String tag, String msg) {
                if (DEBUG)
                        Log.i(TAG + tag, tag + "-->" + msg);
        }

        public static void v(String tag, String msg) {
                if (DEBUG)
                        Log.v(TAG + tag, tag + "-->" + msg);
        }

        public static void d(Object obj) {
                if (DEBUG)
                        Log.d(TAG, obj.toString());
        }

        public static void d(String tag, String msg) {
                if (DEBUG)
                        Log.d(TAG + tag, tag + "--->" + msg);
        }

        public static void d(String msg) {
                if (DEBUG)
                        Log.d(TAG, msg);
        }

        public static void d(String tag, Exception e) {
                Log.d(TAG + tag, e.toString());
        }

        public static void w(String msg) {
                if (DEBUG)
                        Log.w(TAG, msg);
        }

        public static void w(String tag, String msg) {
                if (DEBUG)
                        Log.w(TAG + tag, tag + "-->" + msg);
        }

        public static void e(String msg) {
                if (DEBUG) {
                        Log.e(TAG, msg);
                }
        }

        public static void e(String tag, String msg) {
                if (DEBUG)
                        Log.e(TAG + tag, tag + "-->" + msg);
        }

        public static void e(String tag, String msg, Throwable tr) {
                if (DEBUG)
                        Log.e(TAG + tag, tag + "-->" + msg, tr);
        }

        public static void printStackTrace(Exception e) {
                if (DEBUG)
                        Log.e(TAG, e.toString());
        }

}
