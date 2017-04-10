package com.android.graduation.utils.network;

import android.text.TextUtils;

import com.android.graduation.datebase.WeatherDB;
import com.android.graduation.model.place.City;
import com.android.graduation.model.place.County;
import com.android.graduation.model.place.Province;


/**
*@author mupu
*created at 2015/11/12 12:18
 * 提供工具类来解析和处理数据
*/
public class Utility {
    /**
    *   解析和处理 服务器 返回的省级数据
    */
    public synchronized static boolean handleProvincesResponse(WeatherDB weatherDB, String response) {

        if (!TextUtils.isEmpty(response)) {
            String[] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length >0) {
                for (String p : allProvinces) {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setCode(array[0]);
                    province.setName(array[1]);
                    //将解析出来的数据储存到 Province
                    weatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    /**
    *   解析和处理 服务器 返回的市级数据
    */
    public static boolean handleCitiesResponse(WeatherDB weatherDB, String response, int provinceId) {

        if (!TextUtils.isEmpty(response)) {
            String[] allCities = response.split(",");
            if (allCities != null && allCities.length >0) {
                for (String p : allCities) {
                    String[] array = p.split("\\|");
                    City city = new City();
                    city.setCode(array[0]);
                    city.setName(array[1]);
                    city.setProvinceId(provinceId);
                    //将解析出来的数据储存到 Province
                    weatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    /**
    *   解析和处理 服务器 返回的县级数据
    */
    public static boolean handleCountiesResponse(WeatherDB weatherDB, String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCounties = response.split(",");
            if (allCounties != null && allCounties.length >0) {
                for (String p : allCounties) {
                    String[] array = p.split("\\|");
                    County county = new County();
                    county.setCode(array[0]);
                    county.setName(array[1]);
                    county.setCityId(cityId);
                    //将解析出来的数据储存到 Province
                    weatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }

    /**
     *   解析和处理 服务器 返回的城市编号数据
     */
    public static String handleCityNumResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCounties = response.split(",");
            if (allCounties != null && allCounties.length >0) {
                for (String p : allCounties) {
                    String[] array = p.split("\\|");
                    return array[1];
                }
            }
        }
        return null;
    }

}

