package com.android.graduation.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.graduation.R;
import com.android.graduation.adapter.EditLvAdapter;
import com.android.graduation.app.BaseApplication;
import com.android.graduation.app.MyConstant;

import butterknife.BindView;
import butterknife.ButterKnife;



public class EditActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.lv_activity_common)
    ListView mListView;
    @BindView(R.id.tv_common_title)
    TextView mTitle;
    @BindView(R.id.layout_activity_common)
    RelativeLayout mLayout;

    @BindView(R.id.iv_activity_add)
    ImageView mAddBtn;
    @BindView(R.id.iv_activity_edit)
    ImageView mEditBtn;



    private EditLvAdapter mAdapter;
    private String[] mData ;

    private static final int mRequestCode = 2;

    private SharedPreferences.Editor editor;
    private  SharedPreferences sp;
    private int cityCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        ButterKnife.bind(this);

        initSharePreference();
        initData();
        initView();
    }


    private void initSharePreference() {
        sp = BaseApplication.getSP();
        editor = sp.edit();
    }

    private void initView() {
        mAddBtn.setOnClickListener(this);
        mEditBtn.setOnClickListener(this);

        mLayout.setVisibility(View.VISIBLE);
        mTitle.setText("管理城市");
        mAdapter = new EditLvAdapter(mData,cityCount);

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showMaterialDialog(position);
                return false;
            }
        });
        mListView.setAdapter(mAdapter);

    }

    private void showMaterialDialog(final int position) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
//        builder.setTitle("Material Design Dialog");
        builder.setMessage("确定删除选定城市");
        builder.setNegativeButton("取消", null);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                int num = cityCount - position - 1;
                if (num > 0){
                    for (int i = 1;i <= num ; i ++ ){
                        String temp = sp.getString(MyConstant.CITY_Names[position + i],"error");
                        int id = sp.getInt(MyConstant.CITY_IDs[position + 1],-1);
                        editor.putString(MyConstant.CITY_Names[position],temp);
                        editor.putInt(MyConstant.CITY_IDs[position],id);
                    }

                }
                cityCount --;
                editor.putInt(MyConstant.CITY_COUNT,cityCount);
                editor.apply();
                getData(cityCount);
                mAdapter.setLength(cityCount);
                mAdapter.notifyDataSetChanged();

            }
        });
        builder.show();
    }


    private void initData() {
        Intent intent = getIntent();
        cityCount = intent.getIntExtra("cityCount", -1);
        mData = new String[5];
        getData(cityCount);
    }

    private void getData(int cityCount){
        if (cityCount != -1){
            for (int i = 0; i < cityCount; i++){
                mData[i] = sp.getString(MyConstant.CITY_Names[i], "error");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_activity_add:
                if (cityCount >= 5)
                    return;
                Intent intent = new Intent(EditActivity.this,SearchCityActivity.class);
                startActivityForResult(intent,mRequestCode);

                break;
            case R.id.iv_activity_edit:
                Intent intent1 = new Intent(EditActivity.this,WidgetAcityity.class);
                startActivity(intent1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == mRequestCode && resultCode == RESULT_OK){
            String cityName = data.getExtras().getString("name");
            int code = data.getExtras().getInt("code");
            if (!cityName.isEmpty() && code != 0){
                editor.putInt(MyConstant.CITY_COUNT,cityCount + 1);
                editor.putString(MyConstant.CITY_Names[cityCount],cityName);
                editor.putInt(MyConstant.CITY_IDs[cityCount],code);
                editor.apply();
                cityCount ++;
                getData(cityCount);
                mAdapter.setLength(cityCount);
                mAdapter.notifyDataSetChanged();
            }
        }
//        super.onActivityResult(requestCode, resultCode, data);
    }
}
