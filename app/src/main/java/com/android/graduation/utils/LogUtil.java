package com.android.graduation.utils;

import android.util.Log;

/**
 * Created by asus on 2017/3/25.
 */

public class LogUtil {
    private static boolean isLogcat = true;

    public static void e(String tag,String msg){
        if (isLogcat){
            Log.e(tag,msg);
        }
    }

    public static void d(String tag,String msg){
        if (isLogcat){
            Log.d(tag,msg);
        }
    }
}
