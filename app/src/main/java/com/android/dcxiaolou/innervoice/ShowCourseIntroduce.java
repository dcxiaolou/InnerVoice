package com.android.dcxiaolou.innervoice;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import com.android.dcxiaolou.innervoice.adapter.FragmentAdapter;
import com.android.dcxiaolou.innervoice.fragemnt.CourseCatalogFragment;
import com.android.dcxiaolou.innervoice.fragemnt.CourseCommonFragment;
import com.android.dcxiaolou.innervoice.fragemnt.CourseDetailFragment;

import java.util.ArrayList;
import java.util.List;

/*
* 课程简介界面展示
* */

public class ShowCourseIntroduce extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private static final String TAG = "ShowCourseIntroduce";
    public static final String COURCE_ID = "course_id";

    private RadioButton detailRb, catalogRb, commonRb;

    private ViewPager viewPagerl;

    private String courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_introduce);

        initView();

        initData();
        // 获取courseId，并将其用SharePreferences保存到文件中
        courseId = getIntent().getStringExtra(COURCE_ID);
        SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);
        sp.edit().putString("courseId", courseId).apply();
        // Log.d(TAG, courseId);
    }

    private void initView() {
        detailRb = (RadioButton) findViewById(R.id.rb_detail);
        catalogRb = (RadioButton) findViewById(R.id.rb_catalog);
        commonRb = (RadioButton) findViewById(R.id.rb_common);

        viewPagerl = (ViewPager) findViewById(R.id.view_pager);

        detailRb.setOnClickListener(this);
        catalogRb.setOnClickListener(this);
        commonRb.setOnClickListener(this);

        viewPagerl.setOnPageChangeListener(this);
    }

    private void initData() {
        // 给ViewGroup设置适配器
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new CourseDetailFragment());
        fragments.add(new CourseCatalogFragment());
        fragments.add(new CourseCommonFragment());
        FragmentPagerAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        viewPagerl.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_detail:
                viewPagerl.setCurrentItem(0);
            break;
            case R.id.rb_catalog:
                viewPagerl.setCurrentItem(1);
            break;
            case R.id.rb_common:
                viewPagerl.setCurrentItem(2);
            break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        switch (i) {
            case 0:
                detailRb.setChecked(true);
            break;
            case 1:
                catalogRb.setChecked(true);
            break;
            case 2:
                commonRb.setChecked(true);
            break;
            default :
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
