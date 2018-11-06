package com.android.dcxiaolou.innervoice;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import com.android.dcxiaolou.innervoice.adapter.FragmentAdapter;
import com.android.dcxiaolou.innervoice.fragemnt.CenterFragment;
import com.android.dcxiaolou.innervoice.fragemnt.HomeFragment;
import com.android.dcxiaolou.innervoice.fragemnt.MessageFragment;
import com.android.dcxiaolou.innervoice.fragemnt.TreeHoleFragment;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

/*
* 建立主框架布局
* */

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;

    private RadioButton mHomeRb, mTreeHoleRb, mMessageRb, mCenterRb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //布局 ViewGroup + RadioGroup + RadioButton

        // 初始化BmobSDK
        Bmob.initialize(this, "35c39c93bd729b73efb27f9d8df9e72d");

        //初始化界面
        initView();

        //初始化数据
        initData();

        permissionRequest();
    }

    private void permissionRequest() {

        //SD卡读写权限申请
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }

    }

    private void initData() {

        //给ViewPager设置adapter
        List<Fragment> mFragments = new ArrayList<>();

        mFragments.add(new HomeFragment());
        mFragments.add(new TreeHoleFragment());
        mFragments.add(new MessageFragment());
        mFragments.add(new CenterFragment());

        FragmentPagerAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), mFragments);

        mViewPager.setAdapter(adapter);

    }


    private void initView() {

        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        mHomeRb = (RadioButton) findViewById(R.id.home_rb);
        mTreeHoleRb = (RadioButton) findViewById(R.id.tree_hole_rb);
        mMessageRb = (RadioButton) findViewById(R.id.message_rb);
        mCenterRb = (RadioButton) findViewById(R.id.center_rb);

        //给按钮添加监听器，在按钮点击后，页面要相应的切换
        mHomeRb.setOnClickListener(this);
        mTreeHoleRb.setOnClickListener(this);
        mMessageRb.setOnClickListener(this);
        mCenterRb.setOnClickListener(this);

        //给ViewPager设置监听器，在页面滑动切换后，按钮要相应的切换
        mViewPager.setOnPageChangeListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_rb:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.tree_hole_rb:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.message_rb:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.center_rb:
                mViewPager.setCurrentItem(3);
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
                mHomeRb.setChecked(true);
                break;
            case 1:
                mTreeHoleRb.setChecked(true);
                break;
            case 2:
                mMessageRb.setChecked(true);
                break;
            case 3:
                mCenterRb.setChecked(true);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }


    //SD卡读写权限申请响应
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("TAG", "read permission");
                } else {
                    Log.d("TAG", "filed read permission");
                }
                break;
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("TAG", "write permission");
                } else {
                    Log.d("TAG", "filed write permission");
                }
                break;
            default:
                break;
        }

    }


}
