package com.android.graduation.datebase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.android.graduation.model.place.City;
import com.android.graduation.model.place.County;
import com.android.graduation.model.place.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/13.
 * Email: 18814182294@163.com
 * Describe ：
 */

public class WeatherDB {

    /**
     *  数据库名
     */
    public static final String DB_Name = "mupuWeather";
    //数据库版本
    public static final int VERSION = 1;
    private SQLiteDatabase db;

    private static WeatherDB instance;
    public synchronized static WeatherDB getInstance(Context context){
        if(null == instance){
                instance = new WeatherDB(context);
        }
        return instance;
    }

    private WeatherDB(Context context){
        WeatherSQLiteHelper dbHelper = new WeatherSQLiteHelper(context
        ,DB_Name,null,VERSION);
        db = dbHelper.getWritableDatabase();
    }


    /**
     * 将Province实例存储到数据库中
     * @param province
     */
    public void saveProvince(Province province){
        if (province != null) {
            ContentValues values = new ContentValues();
            values.put("province_name",province.getName());
            values.put("province_code",province.getCode());
            db.insert("Province",null,values);
        }
    }

    public List<Province> loadProvinces(){
        List<Province> list = new ArrayList<>();
        Cursor cursor = db.query("Province",null,null,null,null,null,null);
        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                province.setId(cursor.getInt((cursor.getColumnIndex("id"))));
                province.setName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            } while (cursor.moveToNext());
        }
        return list;
    }



    public void saveCity(City city){
        if (city != null) {
            ContentValues values = new ContentValues();
            values.put("city_name",city.getName());
            values.put("city_code",city.getCode());
            values.put("province_id",city.getProvinceId());
            db.insert("City",null,values);
        }
    }

    public List<City> loadCities(int provinceId){
        List<City> list = new ArrayList<>();
        Cursor cursor = db.query("City",null,"province_id = ?",new String[]{String.valueOf(provinceId)},null,null,null);
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setId(cursor.getInt((cursor.getColumnIndex("id"))));
                city.setName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(provinceId);
                list.add(city);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public void saveCounty(County county){
        if (county != null) {
            ContentValues values = new ContentValues();
            values.put("county_name",county.getName());
            values.put("county_code",county.getCode());
            values.put("city_id",county.getCityId());
            db.insert("County",null,values);
        }
    }

    public List<County> loadCounties(int cityId){
        List<County> list = new ArrayList<>();
        Cursor cursor = db.query("County",null,"city_id = ?",new String[]{String.valueOf(cityId)},null,null,null);
        if (cursor.moveToFirst()) {
            do {
                County county = new County();
                county.setId(cursor.getInt((cursor.getColumnIndex("id"))));
                county.setName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCityId(cityId);
                list.add(county);
            } while (cursor.moveToNext());
        }
        return list;
    }

}
