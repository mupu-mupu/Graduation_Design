package com.android.graduation.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.graduation.R;
import com.android.graduation.model.weather_data.WindData;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2017/4/7.
 */

public class WindVH extends RecyclerView.ViewHolder {

//    @BindView(R.id.tv_wind_dir)
    TextView windDir;
//    @BindView(R.id.tv_wind_sc)
    TextView windSc;

    private WindData mData;

    public WindVH(View itemView) {
        super(itemView);
//        ButterKnife.bind(this.itemView);
        windDir = (TextView) itemView.findViewById(R.id.tv_wind_dir);
        windSc = (TextView) itemView.findViewById(R.id.tv_wind_sc);


    }

    private void initViewHolder() {
        if (mData != null){
            windDir.setText(mData.getDir());
            windSc.setText(mData.getSc());
        }
    }

    public void loadData(WindData data){
        mData = data;
        initViewHolder();
    }
}

