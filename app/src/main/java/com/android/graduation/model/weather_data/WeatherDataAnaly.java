package com.android.graduation.model.weather_data;

import android.content.SharedPreferences;
import android.util.Log;


import com.android.graduation.app.BaseApplication;
import com.android.graduation.app.MyConstant;
import com.android.graduation.model.weather_info.AqiInfo;
import com.android.graduation.model.weather_info.WeatherInfo;
import com.android.graduation.model.weather_info.WeatherResult;
import com.android.graduation.utils.Util;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/18.
 * Email: 18814182294@163.com
 * Describe ：
 */

public class WeatherDataAnaly {
    private TodayTemData mTadayTemData;
    private DailyForecastData mDailyForecastData;
    private NowData mNowData;
    private WindData mWindData;
    private AstroData mAstroData;
    private AqiData mAqiData;

    private WeatherInfo mResult;

    private SharedPreferences.Editor editor;
    private int code;

    public WeatherDataAnaly(){
        SharedPreferences sp = BaseApplication.getSP();
        //code = sp.getInt(MyConstant.Weather_Code,-1);
        editor = sp.edit();

        mTadayTemData = new TodayTemData();
        mDailyForecastData = new DailyForecastData();
        mAqiData = new AqiData();
        mNowData = new NowData();
        mWindData = new WindData();
        mAstroData = new AstroData();
    }

    public List createData(WeatherResult result) {

        List list = new ArrayList();
        mResult =  result.getWeatherData().get(0);

        setTadayTemData();
        setDailyForecastData();
        setAqiData();
        setNowData();
        setWindData();
        setAstroData();

        list.add(mTadayTemData);
        list.add("Horizon_Line");
        list.add(mDailyForecastData);
        list.add("Horizon_Line");
        list.add(mAqiData);
        list.add("Horizon_Line");
        list.add(mNowData);
        list.add("Horizon_Line");
        list.add(mWindData);
        list.add("Horizon_Line");
        list.add(mAstroData);
        return list;
    }

    public void setTadayTemData() {
        mTadayTemData.setQtly(mResult.getForecastResult().get(0).getCond().getTxtD() + "|" + mResult.getAqiInfo().getCity().getQlty());

        int[] temRange = new int[3];
        temRange[0] = Integer.valueOf(mResult.getForecastResult().get(0).getTmp().getMin());
        temRange[1] = Integer.valueOf(mResult.getForecastResult().get(0).getTmp().getMax());
        temRange[2] = Integer.valueOf(mResult.getNowInfo().getTmp());
        mTadayTemData.setTempRange(temRange);

        mTadayTemData.setIcCode(String.valueOf(mResult.getForecastResult().get(0).getCond().getCodeD()));

        editor.putInt(MyConstant.Weather_Code,mResult.getForecastResult().get(0).getCond().getCodeD());
        editor.apply();
    }

    public void setDailyForecastData() {
        List<TodayTemData> dailyForecast = new ArrayList<>(6);
        for (int i = 1; i < 7; i++){
            TodayTemData temData = new TodayTemData();

            int[] temRange = new int[3];
            temRange[0] = Integer.valueOf(mResult.getForecastResult().get(i).getTmp().getMin());
            temRange[1] = Integer.valueOf(mResult.getForecastResult().get(i).getTmp().getMax());

            temData.setTempRange(temRange);
            temData.setIcCode(String.valueOf(mResult.getForecastResult().get(i).getCond().getCodeD()));
            temData.setWeek(Util.dayForWeek(i));
            dailyForecast.add(temData);
        }
        mDailyForecastData.setDailyForecast(dailyForecast);
    }


    public void setAqiData() {
        AqiInfo.CityEntity cityEntity = mResult.getAqiInfo().getCity();
        mAqiData.setAqi(cityEntity.getAqi());
        mAqiData.setPm10(cityEntity.getPm10());
        mAqiData.setPm25(cityEntity.getPm25());
        mAqiData.setQlty(cityEntity.getQlty());
    }

    public void setNowData() {
        mNowData.setFl(mResult.getNowInfo().getFl());
        mNowData.setHum(mResult.getNowInfo().getHum());
    }

    public void setWindData() {
        mWindData.setDir(mResult.getForecastResult().get(0).getWind().getDir());
        mWindData.setSc(mResult.getForecastResult().get(0).getWind().getSc());
    }

    public void setAstroData() {
        mAstroData.setSr(mResult.getForecastResult().get(0).getAstro().getSr());
        mAstroData.setSs(mResult.getForecastResult().get(0).getAstro().getSs());
    }
}
