package com.android.graduation.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.android.graduation.R;
import com.android.graduation.adapter.SettingLvAdapter;


import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2017/4/10.
 */

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.lv_activity_common)
    ListView mListView;
    @BindView(R.id.tv_common_title)
    TextView mTitle;

    private SettingLvAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        ButterKnife.bind(this);

        mTitle.setText("设置");
        mAdapter = new SettingLvAdapter();
        mListView.setAdapter(mAdapter);
    }


}
