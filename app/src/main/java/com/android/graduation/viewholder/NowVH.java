package com.android.graduation.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.graduation.R;
import com.android.graduation.model.weather_data.NowData;
import com.android.graduation.view.ScaleView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2017/4/6.
 */

public class NowVH extends RecyclerView.ViewHolder{

    @BindView(R.id.view_now_scale)
    ScaleView mScaleView;
    @BindView(R.id.tv_now_value)
    TextView nowValue;

    private NowData mData;

    public NowVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);

    }

    private void initNowViewHolder() {
        if (mData != null){
            int[] value = new int[3];
            value[2] = Integer.valueOf(mData.getHum());
            mScaleView.setViewProperty(3,value,null);

            nowValue.setText(mData.getFl() + "°C");
        }
    }

    public void loadData(NowData data){
        mData = data;
        initNowViewHolder();

    }
}
