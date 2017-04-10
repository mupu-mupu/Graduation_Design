package com.android.graduation.model.place;


/**
 * Created by Administrator on 2016/7/13.
 * Email: 18814182294@163.com
 * Describe ：
 */

public class City {



    private int id;
    private String code;
    private String name;
    private int province_code;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProvinceId() {
        return province_code;
    }

    public void setProvinceId(int province_code) {
        this.province_code = province_code;
    }
}
