package com.android.graduation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.graduation.R;
import com.android.graduation.app.BaseApplication;
import com.android.graduation.model.weather_data.AqiData;
import com.android.graduation.model.weather_data.AstroData;
import com.android.graduation.model.weather_data.DailyForecastData;
import com.android.graduation.model.weather_data.NowData;
import com.android.graduation.model.weather_data.TodayTemData;
import com.android.graduation.model.weather_data.WindData;
import com.android.graduation.viewholder.AqiVH;
import com.android.graduation.viewholder.AstroVH;
import com.android.graduation.viewholder.DailyForecastVH;
import com.android.graduation.viewholder.HorizontalLineVH;
import com.android.graduation.viewholder.NowVH;
import com.android.graduation.viewholder.TodayTemVH;
import com.android.graduation.viewholder.WindVH;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2017/3/28.
 */

public class FragmentRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_TODAY_TEMP = 0;
    private static final int TYPE_FORECAST_TEMP = 2;
    private static final int TYPE_AQI = 4;
    private static final int TYPE_NOW = 6;
    private static final int TYPE_WIND = 8;
    private static final int TYPE_ASTRO = 10;
    private static final int TYPE_HORIZONTAL_LINE = -1;


    private List mData = new ArrayList();

    public FragmentRVAdapter(List data) {
        mData = data;
    }

    @Override
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
        if (position % 2 != 0  && position > 0){
            position = -1;
        }
        return position;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_TODAY_TEMP){
            View view = LayoutInflater.from(BaseApplication.getContext()).inflate(R.layout.item_today_tem,parent,false);
            return new TodayTemVH(view);
        }else if (viewType == TYPE_FORECAST_TEMP){
            View view = LayoutInflater.from(BaseApplication.getContext()).inflate(R.layout.item_daily_forecast,parent,false);
            return new DailyForecastVH(view);
        }else if (viewType == TYPE_AQI){
            View view = LayoutInflater.from(BaseApplication.getContext()).inflate(R.layout.item_aqi,parent,false);
            return new AqiVH(view);
        }else if (viewType == TYPE_NOW){
            View view = LayoutInflater.from(BaseApplication.getContext()).inflate(R.layout.item_now,parent,false);
            return new NowVH(view);
        }else if (viewType == TYPE_WIND){
            View view = LayoutInflater.from(BaseApplication.getContext()).inflate(R.layout.item_wind,parent,false);
            return new WindVH(view);
        }else if (viewType == TYPE_ASTRO){
            View view = LayoutInflater.from(BaseApplication.getContext()).inflate(R.layout.item_astro,parent,false);
            return new AstroVH(view);
        }else if (viewType == TYPE_HORIZONTAL_LINE){
            View view = LayoutInflater.from(BaseApplication.getContext()).inflate(R.layout.item_herizontal_line,parent,false);
            return new HorizontalLineVH(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if(viewType == TYPE_TODAY_TEMP){
            TodayTemVH VH = (TodayTemVH) holder;
            VH.loadData((TodayTemData) mData.get(position));
        } else if (viewType == TYPE_FORECAST_TEMP) {
            DailyForecastVH VH= (DailyForecastVH) holder;
            VH.loadData((DailyForecastData) mData.get(position));
        }
        else if (viewType == TYPE_AQI) {
            AqiVH VH = (AqiVH) holder;
            VH.loadData((AqiData) mData.get(position));
        } else if (viewType == TYPE_NOW) {
            NowVH VH = (NowVH) holder;
            VH.loadData((NowData) mData.get(position));
        } else if (viewType == TYPE_WIND) {
            WindVH VH = (WindVH) holder;
            VH.loadData((WindData) mData.get(position));
        } else if (viewType == TYPE_ASTRO) {
            AstroVH VH = (AstroVH) holder;
            VH.loadData((AstroData) mData.get(position));
        } else if (viewType == TYPE_HORIZONTAL_LINE) {
            // not need to add data ,it just a horizontal view
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
