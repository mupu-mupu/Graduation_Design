package com.android.graduation.model.weather_info;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/11.
 * Email: 18814182294@163.com
 * Describe ：天气信息汇总
 *     Aqi
 *     Now
 *     Forecast
 */

public class WeatherInfo implements Serializable {


    @SerializedName("aqi") private AqiInfo aqiInfo;
    @SerializedName("now") private NowInfo  nowInfo;
    @SerializedName("daily_forecast") private ArrayList<ForecastInfo> forecastResult;
    //@SerializedName("hourly_forecast") private ArrayList<HourlyInfo> hourlyResult;

    public AqiInfo getAqiInfo() {
        return aqiInfo;
    }
    public NowInfo getNowInfo() {
        return nowInfo;
    }
//    public ArrayList<HourlyInfo> getHourlyResult() {
//        return hourlyResult;
//    }
    public ArrayList<ForecastInfo> getForecastResult() {
        return forecastResult;
    }

}