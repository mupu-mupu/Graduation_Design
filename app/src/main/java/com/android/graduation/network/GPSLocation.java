package com.android.graduation.network;


import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.android.graduation.app.BaseApplication;
import com.android.graduation.utils.LogUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by asus on 2017/3/26.
 */

public class GPSLocation implements AMapLocationListener,PlaceNumQuerier.OnCityNumListener {


    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    private onGPSLocationListener mListener;
    private Context mContext;

    //GPS定位获取得到城市编号
    private String mCityNum;

    private PlaceNumQuerier mCityNumQuerier;

    private String mDistricName;

    public GPSLocation(Context context) {
        mContext = context;

        initAmapLoc();

    }

    private void initAmapLoc() {
        //初始化定位
        mLocationClient = new AMapLocationClient(mContext);
        //设置定位回调监听
        mLocationClient.setLocationListener(this);

        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);

        if (mLocationOption.isOnceLocationLatest()) {
            mLocationOption.setOnceLocationLatest(true);
            //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。
            //如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会。
        }
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);

    }

    public void startLocation(){
        //启动定位
        mLocationClient.startLocation();
    }

    //定位回调监听器
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);//定位时间
                date.getTime();
                mDistricName = aMapLocation.getDistrict();//街区信息

                queryCityNum(aMapLocation);
            } else {
                mListener.onFailed();
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                LogUtil.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    private void queryCityNum(AMapLocation aMapLocation){
        mCityNumQuerier = new PlaceNumQuerier(aMapLocation,mContext);
        mCityNumQuerier.setOnCityNumListener(this);
    }


    public int getCityID(){
        if (mCityNum == null)
            return 0;
        return Integer.decode(mCityNum);
    }

    public String getDistricName(){
        return mDistricName;
    }

    public void onDestroyGPS(){
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
            mLocationClient = null;
            mLocationOption = null;
        }
    }

    public void onStopGPS(){
        if(null != mLocationClient)
            mLocationClient.stopLocation();
    }

    public void setOnGPSLocationListener(onGPSLocationListener listener){
        mListener = listener;
    }

    @Override
    public void onSuccess(String cityNum) {
        if (cityNum != null)
            mCityNum = cityNum;
        else
            mCityNum = null;
        mListener.onSuccess();
    }

    @Override
    public void onFailed() {
        mListener.onFailed();
        LogUtil.e("PlaceNumQuerier","error" );
    }

    public interface onGPSLocationListener{
        void onSuccess();
        void onFailed();
    }
}
