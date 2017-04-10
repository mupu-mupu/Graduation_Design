package com.android.graduation.model.weather_info;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/11.
 * Email: 18814182294@163.com
 * Describe ：空气质量
 *      PM2.5    pm25
 *      qlty    空气质量类别
 */

public class AqiInfo implements Serializable {


    @SerializedName("city") private CityEntity city;
    public CityEntity getCity() {
        return city;
    }

         public static class CityEntity implements Serializable {
        @SerializedName("pm25") private String pm25;
        @SerializedName("qlty") private String qlty;
        @SerializedName("pm10") private String pm10;
        @SerializedName("aqi")  private String aqi;

         public String getPm10() {
             return pm10;
         }

         public String getAqi() {
             return aqi;
         }

             public String getQlty() {
            return qlty;
        }
        public String getPm25() {
            return pm25;
        }
    }

}
