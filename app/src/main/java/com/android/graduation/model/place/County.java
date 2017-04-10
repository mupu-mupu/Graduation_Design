package com.android.graduation.model.place;

/**
 * Created by Administrator on 2016/7/13.
 * Email: 18814182294@163.com
 * Describe ：
 */

public class County {


    private int id;
    private String name;
    private String code;
    private int city_code;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCityId() {
        return city_code;
    }

    public void setCityId(int city_code) {
        this.city_code = city_code;
    }
}
