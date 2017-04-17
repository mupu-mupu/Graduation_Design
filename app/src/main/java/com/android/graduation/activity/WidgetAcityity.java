package com.android.graduation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.graduation.R;
import com.android.graduation.adapter.SettingLvAdapter;
import com.android.graduation.adapter.WidgetLvAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WidgetAcityity extends AppCompatActivity {
    @BindView(R.id.lv_activity_common)
    ListView mListView;
    @BindView(R.id.tv_common_title)
    TextView mTitle;

    private WidgetLvAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        ButterKnife.bind(this);

        initView();

    }

    private void initView() {
        mTitle.setText("桌面天气");
        mAdapter = new WidgetLvAdapter();
        mListView.setAdapter(mAdapter);
    }
}
