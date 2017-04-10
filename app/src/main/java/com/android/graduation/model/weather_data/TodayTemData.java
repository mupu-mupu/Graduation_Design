package com.android.graduation.model.weather_data;

/**
 * Created by asus on 2017/4/4.
 */

public class TodayTemData {

    private int[] tempRange;
    private String qtly;
    private String icCode;
    private String week;

    public String getQtly() {
        return qtly;
    }

    public void setQtly(String qtly) {
        this.qtly = qtly;
    }

    public int[] getTempRange() {
        return tempRange;
    }

    public void setTempRange(int[] tempRange) {
        this.tempRange = tempRange;
    }


    public String getIcCode() {
        return icCode;
    }

    public void setIcCode(String icCode) {
        this.icCode = icCode;
    }


    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
