package com.android.dcxiaolou.innervoice;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android.dcxiaolou.innervoice.adapter.TabLayoutMenuAdapter;
import com.android.dcxiaolou.innervoice.fragemnt.ShowReadArticleFragment1;
import com.android.dcxiaolou.innervoice.fragemnt.ShowReadArticleFragment2;
import com.android.dcxiaolou.innervoice.fragemnt.ShowReadArticleFragment3;
import com.android.dcxiaolou.innervoice.fragemnt.ShowReadArticleFragment4;
import com.android.dcxiaolou.innervoice.fragemnt.ShowReadArticleFragment5;
import com.android.dcxiaolou.innervoice.fragemnt.ShowReadArticleFragment6;
import com.android.dcxiaolou.innervoice.fragemnt.ShowReadArticleFragment7;
import com.android.dcxiaolou.innervoice.fragemnt.ShowReadArticleFragment8;

import java.util.ArrayList;
import java.util.List;

/*
* 阅读模块
* */

public class ShowReadArticleActivity extends AppCompatActivity {

    private String titles[] = {"心理科普", "婚恋情感", "家庭关系", "人际社交", "自我察觉", "成长学习", "心理健康", "职场技能"};

    private TabLayout menuTabLayout;
    private ViewPager viewPager;
    private List<String> strings = new ArrayList<String>();
    private List<Fragment> fragments = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_read);
        //初始化数据
        initData();
        //初始化视图
        initView();
    }

    private void initView() {
        menuTabLayout = (TabLayout) findViewById(R.id.menu_tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new TabLayoutMenuAdapter(fragments, strings,
                getSupportFragmentManager(), this));
        menuTabLayout.setupWithViewPager(viewPager);

    }

    private void initData() {
        for (String str : titles)
            strings.add(str);

        fragments.add(new ShowReadArticleFragment1());
        fragments.add(new ShowReadArticleFragment2());
        fragments.add(new ShowReadArticleFragment3());
        fragments.add(new ShowReadArticleFragment4());
        fragments.add(new ShowReadArticleFragment5());
        fragments.add(new ShowReadArticleFragment6());
        fragments.add(new ShowReadArticleFragment7());
        fragments.add(new ShowReadArticleFragment8());
    }

}