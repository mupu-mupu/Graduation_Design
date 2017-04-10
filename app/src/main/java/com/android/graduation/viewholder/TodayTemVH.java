package com.android.graduation.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.graduation.R;
import com.android.graduation.model.weather_data.TodayTemData;
import com.android.graduation.view.ScaleView;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2017/4/5.
 */

public class TodayTemVH extends RecyclerView.ViewHolder{

    @BindView(R.id.view_today_tem)
    ScaleView mScaleView;
    @BindView(R.id.tv_today_qity)
    TextView mTodayDesc;


    public TodayTemVH(View itemView) {
        super(itemView);

        ButterKnife.bind(this,itemView);
    }

    public void loadData(TodayTemData data){
        mScaleView.setViewProperty(1,data.getTempRange(),data.getIcCode());
        mTodayDesc.setText(data.getQtly());
    }
}
