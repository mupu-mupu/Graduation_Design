package com.android.graduation.model.weather_info;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/31.
 * Email: 18814182294@163.com
 * Describe ：
 */

public class HourlyInfo implements Serializable {

    @SerializedName("tmp") private String tmp;

    public String getTmp() {
        return tmp;
    }
}
