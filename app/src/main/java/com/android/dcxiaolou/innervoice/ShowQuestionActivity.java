package com.android.dcxiaolou.innervoice;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import com.android.dcxiaolou.innervoice.adapter.FragmentAdapter;
import com.android.dcxiaolou.innervoice.adapter.ShowQuestionAdapter;
import com.android.dcxiaolou.innervoice.fragemnt.ShowQuestionAllFragment;
import com.android.dcxiaolou.innervoice.fragemnt.ShowQuestionHotFragment;
import com.android.dcxiaolou.innervoice.fragemnt.ShowQuestionLatestFragment;
import com.android.dcxiaolou.innervoice.mode.Question;
import com.android.dcxiaolou.innervoice.mode.QuestionResult;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
* 问答功能详情页问题项展示
* */
public class ShowQuestionActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private final static String TAG = "ShowQuestionActivity";

    private RadioButton questionAllRb, questionLatestRb, questionHotRb;

    private ViewPager questionViewPager;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_question);
        //从bmob获取question
        initData();
        //初始化界面
        initView();

    }

    private void initView() {

        questionAllRb = (RadioButton) findViewById(R.id.rb_all);
        questionLatestRb = (RadioButton) findViewById(R.id.rb_latest);
        questionHotRb = (RadioButton) findViewById(R.id.rb_hot);

        questionViewPager = (ViewPager) findViewById(R.id.question_view_pager);

        questionAllRb.setOnClickListener(this);
        questionLatestRb.setOnClickListener(this);
        questionHotRb.setOnClickListener(this);

        questionViewPager.addOnPageChangeListener(this);

        fragments = new ArrayList<>();
        fragments.add(new ShowQuestionAllFragment());
        fragments.add(new ShowQuestionLatestFragment());
        fragments.add(new ShowQuestionHotFragment());
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        questionViewPager.setAdapter(adapter);

    }

    private void initData() {



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_all:
                questionViewPager.setCurrentItem(0);
                break;
            case R.id.rb_latest:
                questionViewPager.setCurrentItem(1);
                break;
            case R.id.rb_hot:
                questionViewPager.setCurrentItem(2);
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
                questionAllRb.setChecked(true);
                break;
            case 1:
                questionLatestRb.setChecked(true);
                break;
            case 2:
                questionHotRb.setChecked(true);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
