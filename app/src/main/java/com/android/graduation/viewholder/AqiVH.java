package com.android.graduation.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.graduation.R;
import com.android.graduation.app.BaseApplication;
import com.android.graduation.model.weather_data.AqiData;
import com.android.graduation.view.ScaleView;

import org.w3c.dom.Text;

import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2017/4/5.
 */

public class AqiVH extends RecyclerView.ViewHolder{
//    @BindView(R.id.view_aqi_scale)
    private ScaleView mScaleView;
//    @BindView(R.id.lv_item_aqi)
    private ListView mListView;


    private AqiData mData;
    private LvAdapter adapter;

    private float[] value = {0,0,22,16,20,1.12f};

    public AqiVH(View itemView) {
        super(itemView);
//        ButterKnife.bind(this.itemView);
        mScaleView = (ScaleView) itemView.findViewById(R.id.view_aqi_scale);
        mListView = (ListView) itemView.findViewById(R.id.lv_item_aqi);
        adapter= new LvAdapter(value);
        mListView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
    }

    private void initViewHolder() {
        if (mData != null){
            int[] valueRange = new int[3];
            valueRange[2] = Integer.valueOf(mData.getAqi());
            mScaleView.setViewProperty(2,valueRange,mData.getQlty());

            value[0] = Float.valueOf(mData.getPm10());
            value[1] = Float.valueOf(mData.getPm25());

            adapter.notifyDataSetChanged();
        }
    }

    public void loadData(AqiData data){
        mData = data;
        initViewHolder();

    }

    private class LvAdapter extends BaseAdapter{

        private String[] name = {"PM10","PM25","NO2","SO2","O3","CO"};
        private float[] value ;

        public LvAdapter(float[] value) {
            this.value = value;
        }

        @Override
        public int getCount() {
            return 6;
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
            LvViewHolder viewHolder;
            if (convertView == null){
                convertView  =  LayoutInflater.from(BaseApplication.getContext()).inflate(R.layout.item_item_lv,null);
                viewHolder = new LvViewHolder(convertView);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (LvViewHolder)convertView.getTag();
            }
            viewHolder.name.setText(name[position]);
            viewHolder.value.setText(String.valueOf(value[position]));
            return convertView;
        }
    }

    public class LvViewHolder{
//        @BindView(R.id.tv_lv_name)
        TextView name;
//        @BindView(R.id.tv_lv_value)
        TextView value;

        public LvViewHolder(View view) {
            name = (TextView) view.findViewById(R.id.tv_lv_name);
            value = (TextView) view.findViewById(R.id.tv_lv_value);
//            ButterKnife.bind(view);
        }
    }
}
