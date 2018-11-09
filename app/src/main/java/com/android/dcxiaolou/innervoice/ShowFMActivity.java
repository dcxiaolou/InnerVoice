package com.android.dcxiaolou.innervoice;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.android.dcxiaolou.innervoice.adapter.FMIntroduceAdapter;
import com.android.dcxiaolou.innervoice.mode.FM;
import com.android.dcxiaolou.innervoice.mode.FMResult;
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
 * FM模块详情页
 * */

public class ShowFMActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private final static String TAG = "ShowFMActivity";

    private Handler mHandler = new Handler();
    private Context mContext;

    private Toolbar fmToolbar;
    private DrawerLayout fmDrawerLayout;

    private NavigationView fmNavView;

    private RadioButton introduceRb, moodRb, sceneRb;

    private LinearLayout introduceLinearLayout;

    private RecyclerView introduceRv;

    private List<FMResult> fmResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_fm);

        mContext = getApplicationContext();

        //初始化数据
        initData();
        //初始化场景
        initView();
    }

    private void initView() {
        fmToolbar = (Toolbar) findViewById(R.id.fm_toolbar);
        setSupportActionBar(fmToolbar);
        fmDrawerLayout = (DrawerLayout) findViewById(R.id.fm_drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.rb_home);
        }
        fmNavView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = fmNavView.getHeaderView(0);
        introduceRb = (RadioButton) headerView.findViewById(R.id.introduce_rb);
        moodRb = (RadioButton) headerView.findViewById(R.id.mood_rb);
        sceneRb = (RadioButton) headerView.findViewById(R.id.scene_rb);

        introduceLinearLayout = (LinearLayout) headerView.findViewById(R.id.fm_introduce);

        introduceRb.setOnClickListener(this);
        moodRb.setOnClickListener(this);
        sceneRb.setOnClickListener(this);

    }

    private void initData() {

        //从bmob中获取FM，并展示
        GetFMFromBmob();
    }

    private void GetFMFromBmob() {

        BmobQuery<FM> query = new BmobQuery<>();
        query.addQueryKeys("fm");
        query.addWhereEqualTo("type", "1");
        query.addWhereEqualTo("kind", "1");
        query.findObjects(new FindListener<FM>() {
            @Override
            public void done(List<FM> list, BmobException e) {
                if (e == null) {
                    int count = list.size();
                    Log.d(TAG, "count = " + count);
                    BmobFile file;
                    String fileUrl;
                    for (int i = 0; i < count; i++) {
                        file = list.get(i).getFm();
                        fileUrl = file.getUrl();
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder().url(fileUrl).build();
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String result = response.body().string().trim();
                                int len = result.length();
                                result = result.substring(1, len - 1);
                                String newResult = "";
                                for (int i = 0; i <= result.length() - 1; i++) {
                                    if (result.charAt(i) == '\\' && result.charAt(i + 1) != '\\') {
                                        continue;
                                    } else {
                                        newResult += result.charAt(i);
                                    }
                                }
                                //Log.d(TAG, newResult);
                                Gson gson = new Gson();
                                FMResult fmResult = gson.fromJson(newResult, FMResult.class);

                                fmResults.add(fmResult);
                            }
                        });
                    }

                    //展示FM
                    ShowIntroduce();

                } else {
                    e.printStackTrace();
                }
            }
        });
    }


    private void ShowIntroduce() {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);

                    Log.d(TAG, "fmResults size = " + fmResults.size());

                    introduceRv = (RecyclerView) introduceLinearLayout.findViewById(R.id.fm_introduce_recycler_view);
                    LinearLayoutManager manager = new LinearLayoutManager(mContext);
                    manager.setOrientation(LinearLayoutManager.VERTICAL);
                    introduceRv.setLayoutManager(manager);
                    FMIntroduceAdapter adapter = new FMIntroduceAdapter(fmResults);
                    introduceRv.setAdapter(adapter);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu:
                fmDrawerLayout.openDrawer(GravityCompat.END);
                break;
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.introduce_rb:

                break;
            case R.id.mood_rb:

                break;
            case R.id.scene_rb:

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

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
