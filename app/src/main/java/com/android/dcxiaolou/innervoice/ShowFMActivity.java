package com.android.dcxiaolou.innervoice;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.android.dcxiaolou.innervoice.adapter.FMItemAdapter;
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

public class ShowFMActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "ShowFMActivity";

    private boolean menuOpen = false;
    private int fmMenuItemOpen = 0;
    private int menuId = 1;

    private Handler mHandler = new Handler();
    private Context mContext;

    private Toolbar fmToolbar;
    private DrawerLayout fmDrawerLayout;

    private NavigationView fmNavView;

    private RadioButton introduceRb, moodRb, sceneRb;

    private LinearLayout introduceLinearLayout, moodLinearLayout, sceneLinearLayout;

    private LinearLayout fmMoodFirst, fmMoodRv, fmSceneFirst, fmSceneRv;

    private LinearLayout fmMoodLinearLayout1, fmMoodLinearLayout2, fmMoodLinearLayout3, fmMoodLinearLayout4,
            fmMoodLinearLayout5, fmMoodLinearLayout6, fmMoodLinearLayout7, fmMoodLinearLayout8, fmMoodLinearLayout9;

    private LinearLayout fmSceneLinearLayout1, fmSceneLinearLayout2, fmSceneLinearLayout3, fmSceneLinearLayout4,
            fmSceneLinearLayout5, fmSceneLinearLayout6, fmSceneLinearLayout7, fmSceneLinearLayout8, fmSceneLinearLayout9;

    private RecyclerView introduceRv, moodRv, sceneRv;

    private List<FMResult> fmResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_fm);

        mContext = getApplicationContext();

        //初始化场景
        initView();

        //从bmob中获取IntroduceFM，并展示
        fmResults = new ArrayList<>();
        GetFMFromBmob("1", "1", introduceRv);

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
        moodLinearLayout = (LinearLayout) headerView.findViewById(R.id.fm_mood);
        sceneLinearLayout = (LinearLayout) headerView.findViewById(R.id.fm_scene);

        introduceRv = (RecyclerView) introduceLinearLayout.findViewById(R.id.fm_introduce_recycler_view);
        moodRv = (RecyclerView) moodLinearLayout.findViewById(R.id.fm_mood_Rv).findViewById(R.id.fm_mood_recycler_view);
        sceneRv = (RecyclerView) sceneLinearLayout.findViewById(R.id.fm_scene_Rv).findViewById(R.id.fm_scene_recycler_view);

        fmMoodFirst = (LinearLayout) headerView.findViewById(R.id.fm_mood).findViewById(R.id.fm_mood_first);
        fmMoodRv = (LinearLayout) headerView.findViewById(R.id.fm_mood).findViewById(R.id.fm_mood_Rv);
        fmSceneFirst = (LinearLayout) headerView.findViewById(R.id.fm_scene).findViewById(R.id.fm_scene_first);
        fmSceneRv = (LinearLayout) headerView.findViewById(R.id.fm_scene).findViewById(R.id.fm_scene_Rv);


        LinearLayout fmMoodLinearLayout = (LinearLayout) headerView.findViewById(R.id.fm_mood).findViewById(R.id.fm_mood_first);
        fmMoodLinearLayout1 = (LinearLayout) fmMoodLinearLayout.findViewById(R.id.mood_linear_layout_1);
        fmMoodLinearLayout2 = (LinearLayout) fmMoodLinearLayout.findViewById(R.id.mood_linear_layout_2);
        fmMoodLinearLayout3 = (LinearLayout) fmMoodLinearLayout.findViewById(R.id.mood_linear_layout_3);
        fmMoodLinearLayout4 = (LinearLayout) fmMoodLinearLayout.findViewById(R.id.mood_linear_layout_4);
        fmMoodLinearLayout5 = (LinearLayout) fmMoodLinearLayout.findViewById(R.id.mood_linear_layout_5);
        fmMoodLinearLayout6 = (LinearLayout) fmMoodLinearLayout.findViewById(R.id.mood_linear_layout_6);
        fmMoodLinearLayout7 = (LinearLayout) fmMoodLinearLayout.findViewById(R.id.mood_linear_layout_7);
        fmMoodLinearLayout8 = (LinearLayout) fmMoodLinearLayout.findViewById(R.id.mood_linear_layout_8);
        fmMoodLinearLayout9 = (LinearLayout) fmMoodLinearLayout.findViewById(R.id.mood_linear_layout_9);

        LinearLayout fmSceneLinearLayout = (LinearLayout) headerView.findViewById(R.id.fm_scene).findViewById(R.id.fm_scene_first);
        fmSceneLinearLayout1 = (LinearLayout) fmSceneLinearLayout.findViewById(R.id.scene_linear_layout_1);
        fmSceneLinearLayout2 = (LinearLayout) fmSceneLinearLayout.findViewById(R.id.scene_linear_layout_2);
        fmSceneLinearLayout3 = (LinearLayout) fmSceneLinearLayout.findViewById(R.id.scene_linear_layout_3);
        fmSceneLinearLayout4 = (LinearLayout) fmSceneLinearLayout.findViewById(R.id.scene_linear_layout_4);
        fmSceneLinearLayout5 = (LinearLayout) fmSceneLinearLayout.findViewById(R.id.scene_linear_layout_5);
        fmSceneLinearLayout6 = (LinearLayout) fmSceneLinearLayout.findViewById(R.id.scene_linear_layout_6);
        fmSceneLinearLayout7 = (LinearLayout) fmSceneLinearLayout.findViewById(R.id.scene_linear_layout_7);
        fmSceneLinearLayout8 = (LinearLayout) fmSceneLinearLayout.findViewById(R.id.scene_linear_layout_8);
        fmSceneLinearLayout9 = (LinearLayout) fmSceneLinearLayout.findViewById(R.id.scene_linear_layout_9);

        introduceRb.setOnClickListener(this);
        moodRb.setOnClickListener(this);
        sceneRb.setOnClickListener(this);

        fmMoodLinearLayout1.setOnClickListener(this);
        fmMoodLinearLayout2.setOnClickListener(this);
        fmMoodLinearLayout3.setOnClickListener(this);
        fmMoodLinearLayout4.setOnClickListener(this);
        fmMoodLinearLayout5.setOnClickListener(this);
        fmMoodLinearLayout6.setOnClickListener(this);
        fmMoodLinearLayout7.setOnClickListener(this);
        fmMoodLinearLayout8.setOnClickListener(this);
        fmMoodLinearLayout9.setOnClickListener(this);

        fmSceneLinearLayout1.setOnClickListener(this);
        fmSceneLinearLayout2.setOnClickListener(this);
        fmSceneLinearLayout3.setOnClickListener(this);
        fmSceneLinearLayout4.setOnClickListener(this);
        fmSceneLinearLayout5.setOnClickListener(this);
        fmSceneLinearLayout6.setOnClickListener(this);
        fmSceneLinearLayout7.setOnClickListener(this);
        fmSceneLinearLayout8.setOnClickListener(this);
        fmSceneLinearLayout9.setOnClickListener(this);

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
                fmDrawerLayout.openDrawer(GravityCompat.END);
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
                    fmDrawerLayout.closeDrawer(GravityCompat.END);
                    menuOpen = false;
                    break;
                case 2:
                    switch (fmMenuItemOpen) {
                        case 0:
                            fmDrawerLayout.closeDrawer(GravityCompat.END);
                            menuOpen = false;
                            break;
                        case 1:
                            fmMoodFirst.setVisibility(View.VISIBLE);
                            fmMoodRv.setVisibility(View.GONE);
                            fmMenuItemOpen = 0;
                            break;
                        default:
                            break;
                    }
                    break;
                case 3:
                    switch (fmMenuItemOpen) {
                        case 0:
                            fmDrawerLayout.closeDrawer(GravityCompat.END);
                            menuOpen = false;
                            break;
                        case 1:
                            fmSceneFirst.setVisibility(View.VISIBLE);
                            fmSceneRv.setVisibility(View.GONE);
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
        } else if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && !menuOpen) {
            return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.introduce_rb:
                menuId = 1;
                //从bmob中获取IntroduceFM，并展示
                fmResults = new ArrayList<>();
                GetFMFromBmob("1", "1", introduceRv);
                introduceLinearLayout.setVisibility(View.VISIBLE);
                moodLinearLayout.setVisibility(View.GONE);
                sceneLinearLayout.setVisibility(View.GONE);
                break;
            case R.id.mood_rb:
                menuId = 2;
                introduceLinearLayout.setVisibility(View.GONE);
                moodLinearLayout.setVisibility(View.VISIBLE);
                sceneLinearLayout.setVisibility(View.GONE);
                break;
            case R.id.scene_rb:
                menuId = 3;
                introduceLinearLayout.setVisibility(View.GONE);
                moodLinearLayout.setVisibility(View.GONE);
                sceneLinearLayout.setVisibility(View.VISIBLE);
                break;

            case R.id.mood_linear_layout_1:
                fmMenuItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                fmResults = new ArrayList<>();
                GetFMFromBmob("2", "1", moodRv);
                fmMoodFirst.setVisibility(View.GONE);
                fmMoodRv.setVisibility(View.VISIBLE);
                break;
            case R.id.mood_linear_layout_2:
                fmMenuItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                fmResults = new ArrayList<>();
                GetFMFromBmob("2", "2", moodRv);
                fmMoodFirst.setVisibility(View.GONE);
                fmMoodRv.setVisibility(View.VISIBLE);
                break;
            case R.id.mood_linear_layout_3:
                fmMenuItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                fmResults = new ArrayList<>();
                GetFMFromBmob("2", "3", moodRv);
                fmMoodFirst.setVisibility(View.GONE);
                fmMoodRv.setVisibility(View.VISIBLE);
                break;
            case R.id.mood_linear_layout_4:
                fmMenuItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                fmResults = new ArrayList<>();
                GetFMFromBmob("2", "4", moodRv);
                fmMoodFirst.setVisibility(View.GONE);
                fmMoodRv.setVisibility(View.VISIBLE);
                break;
            case R.id.mood_linear_layout_5:
                fmMenuItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                fmResults = new ArrayList<>();
                GetFMFromBmob("2", "5", moodRv);
                fmMoodFirst.setVisibility(View.GONE);
                fmMoodRv.setVisibility(View.VISIBLE);
                break;
            case R.id.mood_linear_layout_6:
                fmMenuItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                fmResults = new ArrayList<>();
                GetFMFromBmob("2", "6", moodRv);
                fmMoodFirst.setVisibility(View.GONE);
                fmMoodRv.setVisibility(View.VISIBLE);
                break;
            case R.id.mood_linear_layout_7:
                fmMenuItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                fmResults = new ArrayList<>();
                GetFMFromBmob("2", "7", moodRv);
                fmMoodFirst.setVisibility(View.GONE);
                fmMoodRv.setVisibility(View.VISIBLE);
                break;
            case R.id.mood_linear_layout_8:
                fmMenuItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                fmResults = new ArrayList<>();
                GetFMFromBmob("2", "8", moodRv);
                fmMoodFirst.setVisibility(View.GONE);
                fmMoodRv.setVisibility(View.VISIBLE);
                break;
            case R.id.mood_linear_layout_9:
                fmMenuItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                fmResults = new ArrayList<>();
                GetFMFromBmob("2", "9", moodRv);
                fmMoodFirst.setVisibility(View.GONE);
                fmMoodRv.setVisibility(View.VISIBLE);
                break;

            case R.id.scene_linear_layout_1:
                fmMenuItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                fmResults = new ArrayList<>();
                GetFMFromBmob("3", "1", sceneRv);
                fmSceneFirst.setVisibility(View.GONE);
                fmSceneRv.setVisibility(View.VISIBLE);
                break;
            case R.id.scene_linear_layout_2:
                fmMenuItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                fmResults = new ArrayList<>();
                GetFMFromBmob("3", "2", sceneRv);
                fmSceneFirst.setVisibility(View.GONE);
                fmSceneRv.setVisibility(View.VISIBLE);
                break;
            case R.id.scene_linear_layout_3:
                fmMenuItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                fmResults = new ArrayList<>();
                GetFMFromBmob("3", "3", sceneRv);
                fmSceneFirst.setVisibility(View.GONE);
                fmSceneRv.setVisibility(View.VISIBLE);
                break;
            case R.id.scene_linear_layout_4:
                fmMenuItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                fmResults = new ArrayList<>();
                GetFMFromBmob("3", "4", sceneRv);
                fmSceneFirst.setVisibility(View.GONE);
                fmSceneRv.setVisibility(View.VISIBLE);
                break;
            case R.id.scene_linear_layout_5:
                fmMenuItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                fmResults = new ArrayList<>();
                GetFMFromBmob("3", "5", sceneRv);
                fmSceneFirst.setVisibility(View.GONE);
                fmSceneRv.setVisibility(View.VISIBLE);
                break;
            case R.id.scene_linear_layout_6:
                fmMenuItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                fmResults = new ArrayList<>();
                GetFMFromBmob("3", "6", sceneRv);
                fmSceneFirst.setVisibility(View.GONE);
                fmSceneRv.setVisibility(View.VISIBLE);
                break;
            case R.id.scene_linear_layout_7:
                fmMenuItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                fmResults = new ArrayList<>();
                GetFMFromBmob("3", "7", sceneRv);
                fmSceneFirst.setVisibility(View.GONE);
                fmSceneRv.setVisibility(View.VISIBLE);
                break;
            case R.id.scene_linear_layout_8:
                fmMenuItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                fmResults = new ArrayList<>();
                GetFMFromBmob("3", "8", sceneRv);
                fmSceneFirst.setVisibility(View.GONE);
                fmSceneRv.setVisibility(View.VISIBLE);
                break;
            case R.id.scene_linear_layout_9:
                fmMenuItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                fmResults = new ArrayList<>();
                GetFMFromBmob("3", "9", sceneRv);
                fmSceneFirst.setVisibility(View.GONE);
                fmSceneRv.setVisibility(View.VISIBLE);
                break;

            default:
                break;
        }
    }


    private void GetFMFromBmob(String type, String kind, final RecyclerView recyclerView) {

        BmobQuery<FM> query = new BmobQuery<>();
        query.addQueryKeys("fm");
        query.addWhereEqualTo("type", type);
        query.addWhereEqualTo("kind", kind);
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
                                if (result != null) {
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
                            }
                        });
                    }

                    //展示FM
                    ShowIntroduce(recyclerView);

                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    private void ShowIntroduce(final RecyclerView recyclerView) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);

                    Log.d(TAG, "fmResults size = " + fmResults.size());

                    LinearLayoutManager manager = new LinearLayoutManager(mContext);
                    manager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(manager);
                    FMItemAdapter adapter = new FMItemAdapter(fmResults);
                    recyclerView.setAdapter(adapter);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
