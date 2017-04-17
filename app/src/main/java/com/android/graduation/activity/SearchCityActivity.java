package com.android.graduation.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.graduation.R;
import com.android.graduation.app.BaseApplication;
import com.android.graduation.app.MyConstant;
import com.android.graduation.datebase.WeatherDB;
import com.android.graduation.model.place.City;
import com.android.graduation.model.place.County;
import com.android.graduation.model.place.Province;
import com.android.graduation.utils.Util;
import com.android.graduation.utils.network.HttpCallbackListener;
import com.android.graduation.utils.network.HttpUtil;
import com.android.graduation.utils.network.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchCityActivity extends AppCompatActivity{

    @BindView(R.id.et_activity_search)
    EditText mEditText;
    @BindView(R.id.btn_search_city)
    Button mSearchCityBtn;
    @BindView(R.id.lv_search_city)
    ListView mCityListLV;
    @BindView(R.id.iv_search_cancle)
    ImageView mCancelBtn;


    private WeatherDB weatherDB;
    //省列表
    private List<Province> provinceList;
    //市列表
    private List<City> cityList;
    //县列表
    private List<County> countyList;

    //选中的省份
    private Province selectedProvince;
    //选中的城市
    private City selectedCity;
    private String searchCounty = "揭阳";
    private int mCityNum = 0;

    private List<County> data;
    private List<String> mData;
    private ArrayAdapter<String> mAdapter;

    private int countyCode;
    private int clickPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searcher);
        ButterKnife.bind(this);

        initView();
      //  searchFromDB();
    }


    private void initView() {
        weatherDB =  WeatherDB.getInstance(this);
        data = new ArrayList<>();
        mData = new ArrayList<>();

        mAdapter = new ArrayAdapter<>(this,R.layout.item_lv_searcher,mData);
        mCityListLV.setAdapter(mAdapter);

        mCityListLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (data.size() != 0){
                    clickPosition = position;
                    queryCountyNum(data.get(position).getCode());
                }
            }
        });

        mSearchCityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchCounty = mEditText.getText().toString();
                searchFromDB();
            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void endActivity(){
        //数据是使用Intent返回
        Intent intent = new Intent();
        //把返回数据存入Intent
        intent.putExtra("code",countyCode);
        intent.putExtra("name",data.get(clickPosition).getName());
        //设置返回数据
        SearchCityActivity.this.setResult(RESULT_OK, intent);
        //关闭Activity
        SearchCityActivity.this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getAllCity();
    }

    private void searchFromDB(){
        if (searchCounty.isEmpty()){
            Toast.makeText(this,"城市名不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        data.clear();
        data = weatherDB.searchCountyFormDB(Util.replaceCity(searchCounty));
        processData(data);
        if (mData.size() == 0){
            mData.add("未搜索到符合要求的城市");
        }
        mAdapter.notifyDataSetChanged();
        //queryProvinces();
    }

    private void processData(List<County> data) {
        mData.clear();
        for (County county : data){
            int countyId = Integer.valueOf(county.getCode());
            int cityId = countyId / 100;
            int provinceId = cityId / 100;
            String name = county.getName() + "," + weatherDB.searchCityFromDB(cityId) + "," +
                    weatherDB.searchProvinceFromDB(provinceId);
            mData.add(name);
        }
    }

    public void getAllCity() {
        selectedProvince = provinceList.get(mCityNum);
        queryCities();
    }

    public void getAllCounties() {
            selectedCity = cityList.get(mCityNum);
            queryCounties();
    }

    private void queryProvinces(){

        provinceList = weatherDB.loadProvinces();
        if (provinceList.size() > 0) {
        } else {
            queryFromServer(null,"province");
        }
    }

    private  void queryCities(){
            cityList = weatherDB.loadCities(selectedProvince.getId());
        if (cityList.size() > 0) {
            mCityNum ++;
            if (mCityNum >= provinceList.size()){
                mCityNum = 0;
                cityList = weatherDB.loadAllCity();
                getAllCounties();
                return;
            }
            getAllCity();
        } else {
            queryFromServer(selectedProvince.getCode(),"city");
        }
    }

    private void queryCounties(){
        countyList = weatherDB.loadCounties(selectedCity.getId());
        if (countyList.size() > 0) {
            mCityNum ++;
            if (mCityNum >= cityList.size()){
                return;
            }

            getAllCounties();
        } else {
            queryFromServer(selectedCity.getCode(),"county");
        }
    }

    private void queryCountyNum(String countyCode){
        queryFromServer(countyCode,"cityNum");
    }


    private void queryFromServer(final String code,final String type){
        String address;
        if(!TextUtils.isEmpty(code)){
            address = "http://www.weather.com.cn/data/list3/city" + code + ".xml";
        }else {
            address = "http://www.weather.com.cn/data/list3/city.xml";
        }
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {

                boolean result = false;
                if("province".equals(type)){
                    result = Utility.handleProvincesResponse(weatherDB,response);
                } else if ("city".equals(type)) {
                    result = Utility.handleCitiesResponse(weatherDB,response,selectedProvince.getId());
                } else if ("county".equals(type)) {
                    result = Utility.handleCountiesResponse(weatherDB,response,selectedCity.getId());
                }else if ("cityNum".equals(type)){
                    countyCode = Integer.valueOf(Utility.handleCityNumResponse(response));
                    result = true;
                }
                if(result){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ("province".equals(type)) {
                                queryProvinces();
                            } else if ("city".equals(type)) {
                                queryCities();
                            }else if("county".equals(type)){
                                queryCounties();
                            }else if ("cityNum".equals(type)){
                                endActivity();
                            }
                        }
                    });

                }
            }

            @Override
            public void onError(Exception e) {
                // 通过runOnUiThread()方法回到主线程处理逻辑
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SearchCityActivity.this,
                                "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }


}
