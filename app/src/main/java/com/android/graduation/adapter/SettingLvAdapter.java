package com.android.graduation.adapter;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.graduation.R;
import com.android.graduation.app.BaseApplication;
import com.android.graduation.app.MyConstant;
import com.android.graduation.view.ToggleView;
import com.android.graduation.viewholder.CommonVH;

import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2017/4/12.
 */

public class SettingLvAdapter extends BaseAdapter{

    private String[] mData = {"天气声音","自动更新","更新间隔"};

    private boolean isTurnOnSound ;
    private boolean isAutoUpdate;
    private String updateTime;

    private SharedPreferences.Editor editor;
    public SettingLvAdapter() {
        SharedPreferences sp = BaseApplication.getSP();
        isTurnOnSound = sp.getBoolean(MyConstant.ISTurnOnSound, false);
        isAutoUpdate = sp.getBoolean(MyConstant.ISAutoUpdate, false);
        updateTime = sp.getString(MyConstant.UpdateTime,"error");
        editor = sp.edit();
    }

    @Override
    public int getCount() {
        return mData.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonVH viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(BaseApplication.getContext()).inflate(R.layout.item_conmon_lv,null);
            viewHolder = new CommonVH(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (CommonVH) convertView.getTag();
        }

        viewHolder.name.setText(mData[position]);
        switch (position){
            case 0:
                viewHolder.toggleView.setVisibility(View.VISIBLE);
                viewHolder.toggleView.setToggleState(isTurnOnSound);
                viewHolder.toggleView.setOnStateChangeListener(new ToggleView.OnStateChangeListener() {
                    @Override
                    public void onToggleStateChanged(boolean state) {
                        editor.putBoolean(MyConstant.ISTurnOnSound,state);
                        editor.apply();
                    }
                });
                break;

            case 1:
                viewHolder.toggleView.setVisibility(View.VISIBLE);
                viewHolder.toggleView.setToggleState(isAutoUpdate);
                viewHolder.toggleView.setOnStateChangeListener(new ToggleView.OnStateChangeListener() {
                    @Override
                    public void onToggleStateChanged(boolean state) {
                        editor.putBoolean(MyConstant.ISAutoUpdate,state);
                        editor.apply();
                    }
                });
                break;
            case 2:
                viewHolder.desc.setVisibility(View.VISIBLE);
                viewHolder.desc.setText(updateTime);
                break;
        }

        return convertView;
    }



}
