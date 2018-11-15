package com.android.dcxiaolou.innervoice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.dcxiaolou.innervoice.adapter.FMItemAdapter;
import com.android.dcxiaolou.innervoice.mode.FM;
import com.android.dcxiaolou.innervoice.mode.FMResult;
import com.android.dcxiaolou.innervoice.util.BroadcastPlayer;
import com.bumptech.glide.Glide;
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

public class TestActivity extends AppCompatActivity {

    private boolean menuOpen = false;
    private int fmMenuItemOpen = 0;
    private int menuId = 1;

    private Toolbar testToolbar;
    private DrawerLayout testDrawerLayout;

    private NavigationView testNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //初始化场景
        initView();

    }

    private void initView() {
        testToolbar = (Toolbar) findViewById(R.id.test_toolbar);
        setSupportActionBar(testToolbar);
        testDrawerLayout = (DrawerLayout) findViewById(R.id.test_drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.rb_home);
        }
        testNavView = (NavigationView) findViewById(R.id.test_nav_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    //处理顶部返回主页按钮和菜单按钮相关事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu:
                testDrawerLayout.openDrawer(GravityCompat.END);
                menuOpen = true;
                break;
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //关掉要到的Activity中间的所有Activity
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    //处理返回键与滑动菜单页面间的关联

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && menuOpen) {
            switch (menuId) {
                case 1:
                    testDrawerLayout.closeDrawer(GravityCompat.END);
                    menuOpen = false;
                    break;
                case 2:
                    switch (fmMenuItemOpen) {
                        case 0:
                            testDrawerLayout.closeDrawer(GravityCompat.END);
                            menuOpen = false;
                            break;
                        case 1:
                            fmMenuItemOpen = 0;
                            break;
                        default:
                            break;
                    }
                    break;
                case 3:
                    switch (fmMenuItemOpen) {
                        case 0:
                            testDrawerLayout.closeDrawer(GravityCompat.END);
                            menuOpen = false;
                            break;
                        case 1:
                            fmMenuItemOpen = 0;
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
