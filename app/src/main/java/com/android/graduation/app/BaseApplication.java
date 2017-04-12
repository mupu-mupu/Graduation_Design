package com.android.graduation.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

/**
 * Created by Administrator on 2016/7/12.
 * Email: 18814182294@163.com
 * Describe ：
 */

public class BaseApplication extends Application {


    private static Context mContext;
    private Thread mMainThread;
    private Handler mHandler;
    private static SharedPreferences mSharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mSharedPreferences = getSharedPreferences(MyConstant.CITI_TABLE, MODE_PRIVATE);
        mMainThread = Thread.currentThread();
        mHandler = new Handler();
    }

    public static SharedPreferences getSP(){
        return mSharedPreferences;
    }
    public static Context getContext() {
        return mContext;
    }

    public Thread getThread() {
        return mMainThread;
    }

    public Handler getHandler() {
        return mHandler;
    }
}
