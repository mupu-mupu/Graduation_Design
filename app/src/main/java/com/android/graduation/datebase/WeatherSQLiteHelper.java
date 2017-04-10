package com.android.graduation.datebase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/7/14.
 * Email: 18814182294@163.com
 * Describe ：
 */

public class WeatherSQLiteHelper extends SQLiteOpenHelper {



    public WeatherSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROVINCE);
        db.execSQL(CREATE_CITY);
        db.execSQL(CREATE_COUNTY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     *  Province 建表语句
     */
    public static final String CREATE_PROVINCE = "create table Province ("
            + "id integer primary key autoincrement,"
            + "province_name text,"
            + "province_code text)";

    /**
     *  City 建表语句
     */
    public static final String CREATE_CITY = "create table City ("
            + "id integer primary key autoincrement,"
            + "province_id integer,"
            + "city_name text,"
            + "city_code text)";

    /**
     *   County 建表语句
     */
    public static final String CREATE_COUNTY  = "create table County ("
            + "id integer primary key autoincrement ,"
            + "city_id integer ,"
            + "county_name text,"
            + "county_code text)";

}
