package com.android.graduation.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.graduation.R;
import com.android.graduation.adapter.EditLvAdapter;
import com.android.graduation.adapter.SettingLvAdapter;
import com.android.graduation.app.BaseApplication;
import com.android.graduation.app.MyConstant;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2017/4/12.
 */

public class EditActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.lv_activity_common)
    ListView mListView;
    @BindView(R.id.tv_common_title)
    TextView mTitle;
    @BindView(R.id.layout_activity_common)
    RelativeLayout mLayout;

    @BindView(R.id.iv_activity_add)
    ImageView mAddBtn;
    @BindView(R.id.iv_activity_edit)
    ImageView mEditBtn;



    private EditLvAdapter mAdapter;
    private String[] mData ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        ButterKnife.bind(this);

        initData();
        initView();
    }

    private void initView() {
        mAddBtn.setOnClickListener(this);
        mEditBtn.setOnClickListener(this);

        mLayout.setVisibility(View.VISIBLE);
        mTitle.setText("管理城市");
        mAdapter = new EditLvAdapter(mData);
        mListView.setAdapter(mAdapter);
    }


    private void initData() {
        Intent intent = getIntent();
        int cityCount = intent.getIntExtra("cityCount", -1);
        if (cityCount != -1){
            SharedPreferences sp = BaseApplication.getSP();
            mData = new String[cityCount];
            for (int i = 0; i < cityCount; i++){
                mData[i] = sp.getString(MyConstant.CITY_Names[i], "error");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_activity_add:
                break;
            case R.id.iv_activity_edit:
                break;
        }
    }
}
