package com.android.graduation.adapter;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.graduation.R;
import com.android.graduation.app.BaseApplication;
import com.android.graduation.app.MyConstant;
import com.android.graduation.view.ToggleView;
import com.android.graduation.viewholder.CommonVH;

public class WidgetLvAdapter extends BaseAdapter{

    private String[] mData = {"当前位置"};
    private boolean isLocation;
    private SharedPreferences.Editor editor;

    public WidgetLvAdapter() {
        SharedPreferences sp = BaseApplication.getSP();
        isLocation = sp.getBoolean(MyConstant.Location,false);
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
        viewHolder.toggleView.setVisibility(View.VISIBLE);
        viewHolder.toggleView.setOnStateChangeListener(new ToggleView.OnStateChangeListener() {
            @Override
            public void onToggleStateChanged(boolean state) {
                editor.putBoolean(MyConstant.Location,state);
                editor.apply();
            }
        });

        return convertView;
    }

}
