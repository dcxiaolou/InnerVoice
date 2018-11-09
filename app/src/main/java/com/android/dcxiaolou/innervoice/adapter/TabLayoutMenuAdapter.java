package com.android.dcxiaolou.innervoice.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/*
* 阅读模块顶部菜单的适配器
* */

public class TabLayoutMenuAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<Fragment> fragments;
    private List<String> strings;

    public TabLayoutMenuAdapter(List<Fragment> fragments, List<String> strings, FragmentManager fragmentManager, Context context){
        super(fragmentManager);
        this.strings = strings;
        this.context = context;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return strings.get(position);
    }
}
