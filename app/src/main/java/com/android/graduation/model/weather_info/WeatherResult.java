package com.android.graduation.model.weather_info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/11.
 * Email: 18814182294@163.com
 * Describe ：
 */

public class WeatherResult {

    @SerializedName("HeWeather data service 3.0")
    @Expose
    private List<WeatherInfo> mWeatherData  = new ArrayList<>();


    public List<WeatherInfo> getWeatherData() {
        return mWeatherData;
    }

}
