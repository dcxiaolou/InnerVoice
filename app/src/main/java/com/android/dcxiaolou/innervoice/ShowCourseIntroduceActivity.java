package com.android.dcxiaolou.innervoice;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.android.dcxiaolou.innervoice.adapter.FragmentAdapter;
import com.android.dcxiaolou.innervoice.fragemnt.CourseCatalogFragment;
import com.android.dcxiaolou.innervoice.fragemnt.CourseCommonFragment;
import com.android.dcxiaolou.innervoice.fragemnt.CourseDetailFragment;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/*
* 课程推荐（简介）界面展示
* */

public class ShowCourseIntroduceActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private static final String TAG = "ShowCourseIntroduceActivity";

    public static final String COURSE_ID = "course_id";
    public static final String COVER_PATH = "cover_path";
    public static final String COURSE_TITLE = "cover_title";

    private ImageView coverImageView;

    private RadioButton detailRb, catalogRb, commonRb;

    private ViewPager viewPagerl;

    private String courseId, coverPath, courseTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_introduce);

        // 获取courseId和coverPath，并将其用SharePreferences保存到文件中
        courseId = getIntent().getStringExtra(COURSE_ID);
        coverPath = getIntent().getStringExtra(COVER_PATH);
        courseTitle = getIntent().getStringExtra(COURSE_TITLE);
        SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);
        sp.edit().putString("courseId", courseId).apply();
        sp.edit().putString("courseCover", coverPath).apply();
        sp.edit().putString("courseTitle", courseTitle).apply();
        // Log.d(TAG, courseId);
        // Log.d(TAG, coverPath);

        initView();

        initData();

    }

    private void initView() {
        coverImageView = (ImageView) findViewById(R.id.cover_image);

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
        // 使用glide加载课程封面
        Glide.with(this).load(coverPath).into(coverImageView);

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
