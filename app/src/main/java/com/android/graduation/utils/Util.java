package com.android.graduation.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;

import com.android.graduation.R;
import com.android.graduation.app.BaseApplication;

import java.util.Calendar;

/**
 * Created by hugo on 2016/2/20 0020.
 */
public class Util {



    /**
     * 只关注是否联网
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 获取系统时间
     * @retuern hh
     */
    public static String getNowHour(int time){
        String mTime;
        ContentResolver cv = BaseApplication.getContext().getContentResolver();
        String strTimeFormat = android.provider.Settings.System.getString(cv,
                android.provider.Settings.System.TIME_12_24);
        int hour = 0;
        if(strTimeFormat.equals("24")) {
            Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            if(hour > 12) hour -=12;
        }
        hour += time;
        if (hour > 12) hour -= 12;
        if (hour < 10) mTime = "0" + hour;
        else  mTime = String.valueOf(hour) ;
        return mTime + "时";
    }

    /**
     * 判断当前日期是星期几
     *
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
    public static String dayForWeek(int i){
        Calendar c = Calendar.getInstance();
        int dayForWeek = 0;
        String week = "";
        dayForWeek = c.get(Calendar.DAY_OF_WEEK) + i;
        if (dayForWeek > 7)
            dayForWeek -= 7;
        switch (dayForWeek) {
            case 1:
                week = "Sun";
                break;
            case 2:
                week = "Mon";
                break;
            case 3:
                week = "Thes";
                break;
            case 4:
                week = "Wedn";
                break;
            case 5:
                week = "Thu";
                break;
            case 6:
                week = "Fri";
                break;
            case 7:
                week = "Sat";
                break;
        }
        return week;
    }

    /**
     * 安全的 String 返回
     *
     * @param prefix 默认字段
     * @param obj 拼接字段 (需检查)
     */
    public static String safeText(String prefix, String obj) {
        if (TextUtils.isEmpty(obj)) return "";
        return TextUtils.concat(prefix, obj).toString();
    }

    public static String safeText(String msg) {
        return safeText("", msg);
    }

    /**
     * 天气代码 100 为晴 101-213 500-901 为阴 300-406为雨
     *
     * @param code 天气代码
     * @return 天气情况
     */
    public static int getWeatherType(int code) {
        if (code == 100) {
            //return "晴";
            return R.drawable.weather_sun;
        }
        if ((code >= 101 && code <= 213) || (code >= 500 && code <= 901)) {
            //return "阴";
            return R.drawable.weather_cloudy;
        }
        if (code >= 300 && code <= 406) {
            //return "雨";
            return R.drawable.weather_rain;
        }
        return R.drawable.weather_sun;
    }

    /**
     * 正则匹配掉错误信息
     */
    public static String replaceCity(String city) {
        city = safeText(city).replaceAll("(?:省|市|自治区|特别行政区|地区|盟|区)", "");
        return city;
    }

    /**
     *  px to dp
     *
     */
    public static float px2dp(Context context, int pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxValue/scale + 0.5f);
    }

    /**
     *  dp to px
     *
     */
    public static int dptopx(Context context, int dpValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)((dpValue - 0.5) * scale);
    }

    private static int screenHeight = 0;
    private static int screenWidth = 0;
    public static int getScreenHeight(Context c) {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
        }

        return screenHeight;
    }

    public static int getScreenWidth(Context c) {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }

        return screenWidth;
    }

}