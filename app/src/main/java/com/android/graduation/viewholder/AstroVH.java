package com.android.graduation.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.graduation.R;
import com.android.graduation.model.weather_data.AstroData;
import com.android.graduation.view.AstroView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2017/4/9.
 */

public class AstroVH extends RecyclerView.ViewHolder {

//    @BindView(R.id.view_astro)
    AstroView mAstroView;

    private AstroData mData;

    public AstroVH(View itemView) {
        super(itemView);
//        ButterKnife.bind(this.itemView);
        mAstroView = (AstroView) itemView.findViewById(R.id.view_astro);
    }

    private void initViewHolder(){
        if (mData != null){
            mAstroView.setViewProperty(mData.getSr(),mData.getSs());
        }
    }

    public void loadData(AstroData data){
        mData = data;
        initViewHolder();
    }
}
