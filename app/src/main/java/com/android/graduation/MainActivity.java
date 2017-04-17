package com.android.graduation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.graduation.activity.EditActivity;
import com.android.graduation.activity.SettingActivity;
import com.android.graduation.adapter.ViewPaperAdapter;
import com.android.graduation.app.BaseApplication;
import com.android.graduation.app.MyConstant;
import com.android.graduation.fragment.CityFragment;
import com.android.graduation.network.GPSLocation;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //viewpaper
    @BindView(R.id.view_paper)
    ViewPager mViewPaper;
    //指示器
    @BindView(R.id.layout_main_indicator)
    LinearLayout mIndicatorLayout;
    //setting and menu
    @BindView(R.id.ib_bar_setting)
    ImageButton mSetting;
    @BindView(R.id.ib_bar_menu)
    ImageButton mMenu;
    //district GPS time
    @BindView(R.id.tv_bar_district)
    TextView mTitleTxt;
    @BindView(R.id.im_gps)
    ImageView mGPS;
    @BindView(R.id.tv_bar_time)
    TextView mTimeTxt;

    //城市数量
    private int mCityCount;
    //城市ID数组
    private int[] mCityID = new int[5];
    //城市名称数组
    private String[] mCityName = new String[5];

    private ViewPaperAdapter mPagerAdapter;

    //GPS定位对象引用
    private GPSLocation mGPSLocation;
    //共享内存
    private SharedPreferences sp;

    private static final int mRequestCode = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        initActivity();
        initView();
        initGPSLocation();
        checkCityList();

    }

    private void initActivity() {
        sp = BaseApplication.getSP();
    }

    private void initView() {
        mSetting.setOnClickListener(this);
        mMenu.setOnClickListener(this);
    }

    /**
     * GPS定位回调接口
     */
    private void initGPSLocation() {
        mGPSLocation = new GPSLocation(this);
        mGPSLocation.setOnGPSLocationListener(new GPSLocation.onGPSLocationListener() {
            @Override
            public void onSuccess() {
                checkCityList();
                initViewPager();
            }

            @Override
            public void onFailed() {
                Toast.makeText(MainActivity.this,"定位失败，请查看Log信息",Toast.LENGTH_SHORT).show();
            }
        });

        if (sp.getInt(MyConstant.CITY_COUNT, -1) == -1){
            mGPSLocation.startLocation();
        }
    }


    /**
     * 初始化ViewPaper
     */
    private void initViewPager() {
        mPagerAdapter = new ViewPaperAdapter(getSupportFragmentManager());
        mPagerAdapter.setFragmentNum(mCityCount);
        for (int i = 0; i < mCityCount; i++) {
            Bundle args = new Bundle();
            args.putInt(MyConstant.CITY_ID, mCityID[i]);
            mPagerAdapter.addFragment(CityFragment.newInstance(args));
        }
       mViewPaper.setAdapter(mPagerAdapter);
        mViewPaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mCityName!= null){
                    mTitleTxt.setText(mCityName[position]);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 获取将要显示的城市列表
     */
    private void checkCityList() {
        //获取SharedPreference对象
//        Context context = MainActivity.this;
//        SharedPreferences sp = context.getSharedPreferences(MyConstant.CITI_TABLE, MODE_PRIVATE);

        mCityCount = sp.getInt(MyConstant.CITY_COUNT, -1);

        if (mCityCount == -1) {
            //通过Amap定位通过检索得到城市编号
           // mCityID[0] = getCityByAmap();
            mCityID[0] = mGPSLocation.getCityID();
            if (mCityID[0] == 0)
                return;
            mCityCount = 1;
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt(MyConstant.CITY_COUNT, mCityCount);
            for (int i = 0; i < mCityCount; i ++){
                editor.putInt(MyConstant.CITY_IDs[i],mCityID[i]);
                editor.putString(MyConstant.CITY_Names[i],mGPSLocation.getDistricName());
            }
            editor.apply();
            mTitleTxt.setText(mGPSLocation.getDistricName());
        } else {
            for (int i = 0; i < mCityCount; i++) {
                mCityID[i] = sp.getInt(MyConstant.CITY_IDs[i], -1);
                mCityName[i] = sp.getString(MyConstant.CITY_Names[i],"error");
            }
            mTitleTxt.setText(mCityName[0]);
            initViewPager();
        }

    }


    /**
     * 更新ViewPager
     */
    private void updateViewPaper() {
        int tempCityNum = -1;
        int[] tempCityId = new int[5];
        String[] tempCityName = new String[5];

        tempCityNum = sp.getInt(MyConstant.CITY_COUNT, -1);
        mPagerAdapter.setFragmentNum(tempCityNum);
        //获取Sharepreference的最新数据
        if (tempCityNum > 0) {
            for (int i = 0; i < tempCityNum; i++) {
                tempCityId[i] = sp.getInt(MyConstant.CITY_IDs[i], -1);
                tempCityName[i] = sp.getString(MyConstant.CITY_Names[i],"error");
            }
        }
        //与当前数据比较，替换
        for (int i = 0; i < tempCityNum; i++) {
            if (mCityID[i] != tempCityId[i] && mCityCount >= tempCityNum) {
                mCityID[i] = tempCityId[i];
                mCityName[i] = tempCityName[i];
                Bundle args = new Bundle();
                args.putInt(MyConstant.CITY_ID, mCityID[i]);
                mPagerAdapter.setFragment(i, CityFragment.newInstance(args));
            }else if (mCityID[i] != tempCityId[i] && mCityCount < tempCityNum){
                mCityID[i] = tempCityId[i];
                mCityName[i] = tempCityName[i];
                Bundle args = new Bundle();
                args.putInt(MyConstant.CITY_ID, mCityID[i]);
                mPagerAdapter.addFragment(CityFragment.newInstance(args));

            }
        }
        if (tempCityNum < mCityCount) {
            int index = mCityCount - tempCityNum;
            for(int i = tempCityNum - 1; i > 0;i--) {
                mPagerAdapter.removeFragment(i);
            }
        }
        mCityCount = tempCityNum;
        mPagerAdapter.notifyDataSetChanged();
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_bar_setting:
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.ib_bar_menu:
                Intent intent1 = new Intent(MainActivity.this, EditActivity.class);
                intent1.putExtra("cityCount", mCityCount);
                startActivityForResult(intent1,mRequestCode);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == mRequestCode && resultCode == RESULT_OK ){
            updateViewPaper();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGPSLocation != null){
            mGPSLocation.onDestroyGPS();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGPSLocation != null){
            mGPSLocation.onStopGPS();
        }
    }

}
