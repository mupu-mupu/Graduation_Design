package com.android.graduation.viewholder;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.graduation.R;
import com.android.graduation.adapter.DailyForecastAdapter;
import com.android.graduation.app.BaseApplication;
import com.android.graduation.model.weather_data.DailyForecastData;
import com.android.graduation.model.weather_data.TodayTemData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2017/4/5.
 */

public class DailyForecastVH extends RecyclerView.ViewHolder{

    @BindView(R.id.rv_daily_forecast)
    RecyclerView mRecyclerView;

    private List<TodayTemData> mData = new ArrayList<>();
    private DailyForecastAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;


    public DailyForecastVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);

    }

    private void initViewHolder() {
        mAdapter = new DailyForecastAdapter(mData);
        mLayoutManager = new LinearLayoutManager(BaseApplication.getContext(),LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void loadData(DailyForecastData data){
        mData = data.getDailyForecast();
        initViewHolder();

    }

}
