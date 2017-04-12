package com.android.graduation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.graduation.R;
import com.android.graduation.app.BaseApplication;
import com.android.graduation.viewholder.CommonVH;

/**
 * Created by asus on 2017/4/12.
 */

public class EditLvAdapter extends BaseAdapter {

    private String[] mData;

    public EditLvAdapter(String[] data) {
        mData = data;
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
        return convertView;
    }
}
