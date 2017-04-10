package com.android.graduation.network;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.android.graduation.datebase.WeatherDB;
import com.android.graduation.model.place.City;
import com.android.graduation.model.place.County;
import com.android.graduation.model.place.Province;
import com.android.graduation.utils.LogUtil;
import com.android.graduation.utils.Util;
import com.android.graduation.utils.network.HttpCallbackListener;
import com.android.graduation.utils.network.HttpUtil;
import com.android.graduation.utils.network.Utility;

import java.util.List;

/**
 * Created by asus on 2017/3/27.
 */

public class PlaceNumQuerier {

    private WeatherDB weatherDB;
    //省列表
    private List<Province> provinceList;
    //市列表
    private List<City> cityList;
    //县列表
    private List<County> countyList;

    private AMapLocation mAMapLocation;

    private String cityNum;

    private OnCityNumListener mListener;

    private Context mContext;

    public PlaceNumQuerier(AMapLocation aMapLocation, Context context) {
        if (aMapLocation != null){
            mAMapLocation = aMapLocation;

        }
        mContext = context;
        weatherDB =  WeatherDB.getInstance(mContext);
    }


    private void queryProvinces(){
        String provinceName = Util.replaceCity(mAMapLocation.getProvince());
        List<Province> provinceList = weatherDB.loadProvinces();
        if (provinceList.size() > 0) {
            for (Province province : provinceList) {
                if (province.getName().equals(provinceName)){
                    queryCities(province.getId(),province.getCode());
                    return;
                }
            }
        } else {
            queryFromServer(null,-1,"province");
        }
    }

    private  void queryCities(int provinceID,String provinceCode){
        String cityName = Util.replaceCity(mAMapLocation.getCity());
        cityList = weatherDB.loadCities(provinceID);
        if (cityList.size() > 0) {
            for (City city : cityList) {
                if (city.getName().equals(cityName)){
                    queryCounties(city.getId(),city.getCode());
                    return;
                }
            }
        } else {
            queryFromServer(provinceCode,provinceID,"city");
        }
    }

    private void queryCounties(int cityID,String cityCode){
        String countryName = Util.replaceCity(mAMapLocation.getDistrict());
        String cityName = Util.replaceCity(mAMapLocation.getCity());
        countyList = weatherDB.loadCounties(cityID);
        if (countyList.size() > 0) {
            for (County county : countyList) {
                if (county.getName().equals(countryName) || county.getName().equals(cityName)) {
                    queryCityNum(county.getCode());
                    return;
                }
            }
            if (cityNum == null)
                mListener.onFailed();
        }else
           queryFromServer(cityCode,cityID,"county");
    }

    private void queryCityNum(String countyCode){
            queryFromServer(countyCode,-1,"cityNum");
    }


    private void queryFromServer(final String code,final int ID,final String type) {
        String address;
        if (!TextUtils.isEmpty(code)) {
            address = "http://www.weather.com.cn/data/list3/city" + code + ".xml";
        } else {
            address = "http://www.weather.com.cn/data/list3/city.xml";
        }
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {

                boolean result = false;
                if ("province".equals(type)) {
                    result = Utility.handleProvincesResponse(weatherDB, response);
                } else if ("city".equals(type)) {
                    result = Utility.handleCitiesResponse(weatherDB, response, ID);
                } else if ("county".equals(type)) {
                    result = Utility.handleCountiesResponse(weatherDB, response, ID);
                }else if ("cityNum".equals(type)){
                    cityNum = Utility.handleCityNumResponse(response);
                    result = true;
                }
                if (result) {
                    ((Activity)mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ("province".equals(type)) {
                                queryProvinces();
                            } else if ("city".equals(type)) {
                                queryCities(ID,code);
                            } else if ("county".equals(type)){
                                queryCounties(ID,code);
                            }else if ("cityNum".equals(type)){
                                if (cityNum != null){
                                    mListener.onSuccess(cityNum);
                                }else{
                                    mListener.onFailed();
                                }
                            }
                        }
                    });

                }
            }

            @Override
            public void onError(Exception e) {
                mListener.onFailed();
            }
        });
    }

    public void setOnCityNumListener(OnCityNumListener listener){
        mListener = listener;
        queryProvinces();
    }

    public interface OnCityNumListener{
        void onSuccess(String cityNum);
        void onFailed();
    }
}
