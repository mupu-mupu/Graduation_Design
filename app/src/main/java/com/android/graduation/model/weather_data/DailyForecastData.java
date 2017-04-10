package com.android.graduation.model.weather_data;

import java.util.List;

/**
 * Created by asus on 2017/4/4.
 */

public class DailyForecastData {
    private List<TodayTemData> dailyForecast;

    public List<TodayTemData> getDailyForecast() {
        return dailyForecast;
    }

    public void setDailyForecast(List<TodayTemData> dailyForecast) {
        this.dailyForecast = dailyForecast;
    }

}
