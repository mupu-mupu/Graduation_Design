package com.android.graduation.widget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.android.graduation.R;
import com.android.graduation.app.BaseApplication;
import com.android.graduation.app.MyConstant;
import com.android.graduation.utils.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class ClockService extends Service{

    Calendar c;
    // 定时器
    private Timer timer;
    // 日期格式

    private Date date;
    private String time;
    private String week;
    private RemoteViews rViews;
    private AppWidgetManager manager;
    private ComponentName cName;
    private String hour;
    private String minu;
    private SharedPreferences sp;


    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        timer = new Timer();
        rViews = new RemoteViews(getPackageName(),
                R.layout.widget_clock);
        sp = BaseApplication.getSP();
        //code = sp.getInt(MyConstant.Weather_Code,-1);
        // 刷新
         manager = AppWidgetManager
                .getInstance(BaseApplication.getContext());
         cName = new ComponentName(BaseApplication.getContext(),
                ClockProvider.class);
        /**
         * 参数：1.事件2.延时事件3.执行间隔事件
         */
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateView();
            }
        }, 0, 60000);
    }



    private void updateView() {
        // 时间
//        Date date = new Date(System.currentTimeMillis());
//        sdf.format(date);


        c = Calendar.getInstance();
         date =  c.getTime();
        if (date.getHours() < 10)
             hour = "0" + date.getHours();
        else
            hour = String.valueOf(date.getHours());
        if (date.getMinutes()<10)
             minu = "0" + date.getMinutes();
        else
            minu = String.valueOf(date.getMinutes());
         time = hour + ":" + minu;
         week = c.get(Calendar.MONTH) + 1 + "月" + c.get(Calendar.DAY_OF_MONTH) + "日" + " " + Util.dayForWeek(0) ;
        // 显示当前事件
        rViews.setTextViewText(R.id.tv_widget_time, time);
        rViews.setTextViewText(R.id.tv_widget_week,week);
        rViews.setImageViewBitmap(R.id.iv_widget_icon, BitmapFactory. decodeResource (getResources(), Util.getWeatherType(sp.getInt(MyConstant.Weather_Code,-1)))
                );
        manager.updateAppWidget(cName, rViews);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        timer = null;
    }


}
