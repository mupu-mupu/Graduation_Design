package com.android.graduation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2017/3/28.
 */

public class ViewPaperAdapter extends android.support.v4.app.FragmentPagerAdapter{

    private final List<Fragment> mFragments = new ArrayList<>();
    private int mFragmentNum;

    public ViewPaperAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragmentNum(int fragmentNum){
        mFragmentNum = fragmentNum;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentNum;
    }

    public void addFragment(Fragment fragment) {
        mFragments.add(fragment);
    }

    public void removeFragment(int index){
        mFragments.remove(index);
    }

    public void setFragment(int index,Fragment target){
        mFragments.set(index,target);
    }

}
