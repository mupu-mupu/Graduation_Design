package com.android.graduation.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.graduation.R;
import com.android.graduation.adapter.FragmentRVAdapter;
import com.android.graduation.api.WeatherAPI;
import com.android.graduation.app.MyConstant;
import com.android.graduation.model.weather_data.WeatherDataAnaly;
import com.android.graduation.model.weather_info.WeatherResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by asus on 2017/3/28.
 */

public class CityFragment extends Fragment{

    @BindView(R.id.rv_fragment_weather)
    RecyclerView mRecyclerView;

    private int mCityID;

    private FragmentRVAdapter mAdapter;
    private WeatherDataAnaly mWeatherDataAnaly;
    private List mData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
//        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
    }

    /**
     * 创建fragment并传入参数
     *
     * @param args
     * @return fragment
     */
    public static CityFragment newInstance(Bundle args) {
        CityFragment fragment = new CityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initFragment();
        /**
         *  获取网络数据
         */
        loadWeatherInfo();
    }

    private void initFragment() {

        //获取需要初始化的城市ID
        Bundle bundle = getArguments();
        if (null != bundle) {
            mCityID = bundle.getInt(MyConstant.CITY_ID);
        }
//        //WeatherData初始化
        mWeatherDataAnaly = new WeatherDataAnaly();
        mData = new ArrayList<>();
        //RecyclerView 初始化
        mAdapter = new FragmentRVAdapter(mData);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void loadWeatherInfo(){

        WeatherAPI.getInstance()
                .getWeatherResult(mCityID)
                .cache()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getWeatherInfoObserver);
    }

    private Observer<WeatherResult> getWeatherInfoObserver = new Observer<WeatherResult>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(WeatherResult weatherResult) {

            if(null != weatherResult){
                for (int i = 0; i< mWeatherDataAnaly.createData(weatherResult).size(); i++)
                    mData.add(mWeatherDataAnaly.createData(weatherResult).get(i));
                    mAdapter.notifyDataSetChanged();
                //data= WeatherDataAnaly.createData(weatherResult);
            }
        }
    };

}
