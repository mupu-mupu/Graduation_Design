package com.android.graduation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.graduation.R;
import com.android.graduation.app.BaseApplication;
import com.android.graduation.model.weather_data.TodayTemData;
import com.android.graduation.utils.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2017/4/5.
 */

public class DailyForecastAdapter extends RecyclerView.Adapter<DailyForecastAdapter.DailyForecastVH>{

    private List<TodayTemData> mData = new ArrayList<>();

    public DailyForecastAdapter(List<TodayTemData> data) {
        mData = data;
    }

    @Override
    public DailyForecastVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(BaseApplication.getContext()).inflate(R.layout.item_item_daily_forecast,null);
        return new DailyForecastVH(view);
    }

    @Override
    public void onBindViewHolder(DailyForecastVH holder, int position) {
        holder.loadData(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class DailyForecastVH extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_item_weather)
        ImageView mWeatherIC;
        @BindView(R.id.tv_item_tem)
        TextView mTemRange;
        @BindView(R.id.tv_item_week)
        TextView mWeek;

        public DailyForecastVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void loadData(int position){
            mWeatherIC.setImageResource(Util.getWeatherType(Integer.valueOf(mData.get(position).getIcCode())));

            int[] temValue = mData.get(position).getTempRange();
            String temRange = temValue[1] + "°/ " + temValue[0] + "°";
            mTemRange.setText(temRange);

            mWeek.setText(mData.get(position).getWeek());
        }
    }

}
