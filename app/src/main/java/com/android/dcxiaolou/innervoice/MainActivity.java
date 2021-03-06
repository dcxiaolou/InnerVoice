package com.android.dcxiaolou.innervoice;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

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

    public final static String LOGIN = "log_in";
    private boolean login = false;

    private ViewPager mViewPager;

    private RadioButton mHomeRb, mTreeHoleRb, mMessageRb, mCenterRb;

    private ImageView pushIv, pushCloseIv;

    private RelativeLayout pushInclude;

    private RadioGroup mainPageRadioPage;

    private CardView pushArticleCv, pushSecretCv;

    private Intent intent;

    private long exitTime = 0; //通过计算按键间隔时间差，实现按两次BACK键退出程序

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

        login = getIntent().getBooleanExtra(LOGIN, false);
        if (login) {
            mViewPager.setCurrentItem(3);
        }

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

        pushIv = (ImageView) findViewById(R.id.iv_push);
        pushCloseIv = (ImageView) findViewById(R.id.iv_push_close);

        pushInclude = (RelativeLayout) findViewById(R.id.include_push);

        mainPageRadioPage = (RadioGroup) findViewById(R.id.radio_group_main_page);

        pushArticleCv = (CardView) findViewById(R.id.cv_push_article);
        pushSecretCv = (CardView) findViewById(R.id.cv_push_secret);

        //给按钮添加监听器，在按钮点击后，页面要相应的切换
        mHomeRb.setOnClickListener(this);
        mTreeHoleRb.setOnClickListener(this);
        mMessageRb.setOnClickListener(this);
        mCenterRb.setOnClickListener(this);

        pushIv.setOnClickListener(this);
        pushCloseIv.setOnClickListener(this);

        pushArticleCv.setOnClickListener(this);
        pushSecretCv.setOnClickListener(this);

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
            case R.id.iv_push:
                pushInclude.setVisibility(View.VISIBLE);
                mViewPager.setVisibility(View.GONE);
                mainPageRadioPage.setVisibility(View.GONE);
                break;
            case R.id.iv_push_close:
                pushInclude.setVisibility(View.GONE);
                mViewPager.setVisibility(View.VISIBLE);
                mainPageRadioPage.setVisibility(View.VISIBLE);
                break;
            case R.id.cv_push_article:
                intent = new Intent(this, PushArticleActivity.class);
                startActivity(intent);
                break;
            case R.id.cv_push_secret:
                intent = new Intent(this, PushSecretActivity.class);
                startActivity(intent);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void exit() {

        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }

    }
}
