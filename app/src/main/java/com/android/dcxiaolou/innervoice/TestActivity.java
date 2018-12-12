package com.android.dcxiaolou.innervoice;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

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
