package com.android.dcxiaolou.innervoice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.android.dcxiaolou.innervoice.util.*;

/*
 * FM模块详情页
 * */

public class ShowFMActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "ShowFMActivity";
    public final static String FM_CONTENT = "fm_content";
    public final static String JUMP = "jump";

    //活动菜单是否打开
    private boolean menuOpen = false;
    //心情、场景下的子项是否处于打开状态
    private int fmMenuMoodItemOpen = 0, fmMenuSceneItemOpen = 0;
    //推荐、心情、场景按钮标识1/2/3
    private int menuId = 1;

    //记录播放状态
    private int isPlay = 0;
    //记录有多少首FM
    private int countFM = 0;
    //记录当前播放的是第几首
    private int currentFM = 0;

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

    //FM正在播放列表
    private LinearLayout songListLayout;

    private RecyclerView introduceRv, moodRv, sceneRv, songListRv;

    private List<FMResult> fmResults = new ArrayList<>(), moodResults = new ArrayList<>(),
            sceneResults = new ArrayList<>(), songList = new ArrayList<>();

    private ImageView playAndPauseIv, previousIv, nextIv, songListIv, songListCloseIv;
    private SeekBar skbProgress;
    private static TextView tv_progress;
    private static TextView tv_total;
    private BroadcastPlayer player;
    private ImageView fmBackground;
    private TextView fmTitle, fmSpeak, fmIntroduce, fmViewNum, fmLikeNum;

    //zLoading加载控件
    private LinearLayout zLoadingLayout;
    private LinearLayout fmContextLayout;

    //播放列表的适配器
    private FMItemAdapter songListAdapter = new FMItemAdapter(songList);

    //获取列表跳转的传递参数
    private Intent intent;

    private SharedPreferences sp;
    //用于放置从bmob获取到的result，以便存放在本地
    private Set<String> saveToLocalSet = new HashSet<>();
    //用于放置从本地获取的result
    private List<FMResult> fmResultList = new ArrayList<>();

    //是否从bmob获取
    private boolean isLoadFromBmob = false;

    private Gson gson = new Gson();

    private boolean firstInitBroadcastPlayer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_fm);

        mContext = getApplicationContext();

        //初始化场景
        initView();

        sp = mContext.getSharedPreferences("info", Context.MODE_PRIVATE);

        //处理通过列表点击跳转过来的
        intent = getIntent();

        //若是从主界面跳转过来的，则从bmob中获取，其他页面跳转，则从本地获取
        boolean jump = intent.getBooleanExtra(JUMP, false);
        Log.d(TAG, "jump: " + jump);
        if (!jump) {
            firstInitBroadcastPlayer = true;
            GetFMFromBmob("1", "1", introduceRv, fmResults);
        } else {
            initBroadcastPlayer();
        }

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

        zLoadingLayout = (LinearLayout) findViewById(R.id.z_loading_layout);
        fmContextLayout = (LinearLayout) findViewById(R.id.fm_context);

        fmBackground = (ImageView) findViewById(R.id.fm_background);
        fmTitle = (TextView) findViewById(R.id.fm_title);
        fmSpeak = (TextView) findViewById(R.id.fm_speak);
        fmIntroduce = (TextView) findViewById(R.id.fm_introduce);
        fmViewNum = (TextView) findViewById(R.id.fm_view_num);
        fmLikeNum = (TextView) findViewById(R.id.fm_like_num);

        //播放列表
        songListLayout = (LinearLayout) findViewById(R.id.song_list_Layout);

        previousIv = (ImageView) findViewById(R.id.previous_iv);
        playAndPauseIv = (ImageView) findViewById(R.id.play_pause_iv);
        nextIv = (ImageView) findViewById(R.id.next_iv);
        //打开播放列表按钮
        songListIv = (ImageView) findViewById(R.id.song_list);
        //关闭播放按钮
        songListCloseIv = (ImageView) findViewById(R.id.song_list_close);

        previousIv.setOnClickListener(new ClickEvent());
        playAndPauseIv.setOnClickListener(new ClickEvent());
        nextIv.setOnClickListener(new ClickEvent());

        songListIv.setOnClickListener(this);
        songListCloseIv.setOnClickListener(this);

        skbProgress = (SeekBar) findViewById(R.id.skbProgress);
        skbProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());

        tv_progress = (TextView) findViewById(R.id.tv_progress);
        tv_total = (TextView) findViewById(R.id.tv_total);

        introduceLinearLayout = (LinearLayout) headerView.findViewById(R.id.fm_introduce_item);
        moodLinearLayout = (LinearLayout) headerView.findViewById(R.id.fm_mood);
        sceneLinearLayout = (LinearLayout) headerView.findViewById(R.id.fm_scene);

        introduceRv = (RecyclerView) introduceLinearLayout.findViewById(R.id.fm_introduce_recycler_view);
        moodRv = (RecyclerView) moodLinearLayout.findViewById(R.id.fm_mood_Rv).findViewById(R.id.fm_mood_recycler_view);
        sceneRv = (RecyclerView) sceneLinearLayout.findViewById(R.id.fm_scene_Rv).findViewById(R.id.fm_scene_recycler_view);

        songListRv = (RecyclerView) findViewById(R.id.song_list_recycler_view);

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

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(200);

                            //推荐列表数据的获取
                            Set<String> fmResultSet = sp.getStringSet("fmResults", null);
                            FMResult fmResult;
                            if (fmResultSet != null) {
                                //如果本地存储了推荐列表，直接从本地获取
                                for (String str : fmResultSet) {
                                    fmResult = gson.fromJson(str, FMResult.class);
                                    fmResults.add(fmResult);
                                }
                                ShowIntroduce(introduceRv, fmResults);
                            } else {
                                //如果本地没有存储了推荐列表，直接从bmob获取
                                GetFMFromBmob("1", "1", introduceRv, fmResults);
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();

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
                    switch (fmMenuMoodItemOpen) {
                        case 0:
                            fmDrawerLayout.closeDrawer(GravityCompat.END);
                            menuOpen = false;
                            break;
                        case 1:
                            fmMoodFirst.setVisibility(View.VISIBLE);
                            fmMoodRv.setVisibility(View.GONE);
                            fmMenuMoodItemOpen = 0;
                            break;
                        default:
                            break;
                    }
                    break;
                case 3:
                    switch (fmMenuSceneItemOpen) {
                        case 0:
                            fmDrawerLayout.closeDrawer(GravityCompat.END);
                            menuOpen = false;
                            break;
                        case 1:
                            fmSceneFirst.setVisibility(View.VISIBLE);
                            fmSceneRv.setVisibility(View.GONE);
                            fmMenuSceneItemOpen = 0;
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
            //点击返回键事件处理
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //关掉要到的Activity中间的所有Activity
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.introduce_rb:
                menuId = 1;
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

            case R.id.song_list: //打开播放列表按钮事件处理
                songListLayout.setVisibility(View.VISIBLE);
                break;

            case R.id.song_list_close: //关闭播放列表按钮事件处理
                songListLayout.setVisibility(View.GONE);
                break;

            case R.id.mood_linear_layout_1:
                fmMenuMoodItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展
                moodResults = new ArrayList<>();
                GetFMFromBmob("2", "1", moodRv, moodResults);
                fmMoodFirst.setVisibility(View.GONE);
                fmMoodRv.setVisibility(View.VISIBLE);
                break;
            case R.id.mood_linear_layout_2:
                fmMenuMoodItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                moodResults = new ArrayList<>();
                GetFMFromBmob("2", "2", moodRv, moodResults);
                fmMoodFirst.setVisibility(View.GONE);
                fmMoodRv.setVisibility(View.VISIBLE);
                break;
            case R.id.mood_linear_layout_3:
                fmMenuMoodItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                fmResults = new ArrayList<>();
                GetFMFromBmob("2", "3", moodRv, moodResults);
                fmMoodFirst.setVisibility(View.GONE);
                fmMoodRv.setVisibility(View.VISIBLE);
                break;
            case R.id.mood_linear_layout_4:
                fmMenuMoodItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                moodResults = new ArrayList<>();
                GetFMFromBmob("2", "4", moodRv, moodResults);
                fmMoodFirst.setVisibility(View.GONE);
                fmMoodRv.setVisibility(View.VISIBLE);
                break;
            case R.id.mood_linear_layout_5:
                fmMenuMoodItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                moodResults = new ArrayList<>();
                GetFMFromBmob("2", "5", moodRv, moodResults);
                fmMoodFirst.setVisibility(View.GONE);
                fmMoodRv.setVisibility(View.VISIBLE);
                break;
            case R.id.mood_linear_layout_6:
                fmMenuMoodItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                moodResults = new ArrayList<>();
                GetFMFromBmob("2", "6", moodRv, moodResults);
                fmMoodFirst.setVisibility(View.GONE);
                fmMoodRv.setVisibility(View.VISIBLE);
                break;
            case R.id.mood_linear_layout_7:
                fmMenuMoodItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                moodResults = new ArrayList<>();
                GetFMFromBmob("2", "7", moodRv, moodResults);
                fmMoodFirst.setVisibility(View.GONE);
                fmMoodRv.setVisibility(View.VISIBLE);
                break;
            case R.id.mood_linear_layout_8:
                fmMenuMoodItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                moodResults = new ArrayList<>();
                GetFMFromBmob("2", "8", moodRv, moodResults);
                fmMoodFirst.setVisibility(View.GONE);
                fmMoodRv.setVisibility(View.VISIBLE);
                break;
            case R.id.mood_linear_layout_9:
                fmMenuMoodItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                moodResults = new ArrayList<>();
                GetFMFromBmob("2", "9", moodRv, moodResults);
                fmMoodFirst.setVisibility(View.GONE);
                fmMoodRv.setVisibility(View.VISIBLE);
                break;

            case R.id.scene_linear_layout_1:
                fmMenuSceneItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                sceneResults = new ArrayList<>();
                GetFMFromBmob("3", "1", sceneRv, sceneResults);
                fmSceneFirst.setVisibility(View.GONE);
                fmSceneRv.setVisibility(View.VISIBLE);
                break;
            case R.id.scene_linear_layout_2:
                fmMenuSceneItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                sceneResults = new ArrayList<>();
                GetFMFromBmob("3", "2", sceneRv, sceneResults);
                fmSceneFirst.setVisibility(View.GONE);
                fmSceneRv.setVisibility(View.VISIBLE);
                break;
            case R.id.scene_linear_layout_3:
                fmMenuSceneItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                sceneResults = new ArrayList<>();
                GetFMFromBmob("3", "3", sceneRv, sceneResults);
                fmSceneFirst.setVisibility(View.GONE);
                fmSceneRv.setVisibility(View.VISIBLE);
                break;
            case R.id.scene_linear_layout_4:
                fmMenuSceneItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                sceneResults = new ArrayList<>();
                GetFMFromBmob("3", "4", sceneRv, sceneResults);
                fmSceneFirst.setVisibility(View.GONE);
                fmSceneRv.setVisibility(View.VISIBLE);
                break;
            case R.id.scene_linear_layout_5:
                fmMenuSceneItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                sceneResults = new ArrayList<>();
                GetFMFromBmob("3", "5", sceneRv, sceneResults);
                fmSceneFirst.setVisibility(View.GONE);
                fmSceneRv.setVisibility(View.VISIBLE);
                break;
            case R.id.scene_linear_layout_6:
                fmMenuSceneItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                sceneResults = new ArrayList<>();
                GetFMFromBmob("3", "6", sceneRv, sceneResults);
                fmSceneFirst.setVisibility(View.GONE);
                fmSceneRv.setVisibility(View.VISIBLE);
                break;
            case R.id.scene_linear_layout_7:
                fmMenuSceneItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                sceneResults = new ArrayList<>();
                GetFMFromBmob("3", "7", sceneRv, sceneResults);
                fmSceneFirst.setVisibility(View.GONE);
                fmSceneRv.setVisibility(View.VISIBLE);
                break;
            case R.id.scene_linear_layout_8:
                fmMenuSceneItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                sceneResults = new ArrayList<>();
                GetFMFromBmob("3", "8", sceneRv, sceneResults);
                fmSceneFirst.setVisibility(View.GONE);
                fmSceneRv.setVisibility(View.VISIBLE);
                break;
            case R.id.scene_linear_layout_9:
                fmMenuSceneItemOpen = 1;
                //从bmob中获取Mood_1_FM，并展示
                sceneResults = new ArrayList<>();
                GetFMFromBmob("3", "9", sceneRv, sceneResults);
                fmSceneFirst.setVisibility(View.GONE);
                fmSceneRv.setVisibility(View.VISIBLE);
                break;

            default:
                break;
        }
    }

    private void GetFMFromBmob(final String type, final String kind, final RecyclerView recyclerView, final List<FMResult> results) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //等待动画
                zLoadingLayout.setVisibility(View.VISIBLE);
                //隐藏FM详情
                fmContextLayout.setVisibility(View.GONE);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(2000);

                    BmobQuery<FM> query = new BmobQuery<>();
                    query.addQueryKeys("fm");
                    query.addWhereEqualTo("type", type);
                    query.addWhereEqualTo("kind", kind);
                    query.findObjects(new FindListener<FM>() {
                        @Override
                        public void done(List<FM> list, BmobException e) {
                            if (e == null) {
                                isLoadFromBmob = true;
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

                                                //对result进行处理，去除多余的\
                                                String newResult = "";
                                                for (int i = 0; i <= result.length() - 1; i++) {
                                                    if (result.charAt(i) == '\\' && result.charAt(i + 1) != '\\') {
                                                        continue;
                                                    } else {
                                                        newResult += result.charAt(i);
                                                    }
                                                }
                                                //Log.d(TAG, newResult);

                                                saveToLocalSet.add(newResult); //用于本地存储

                                                FMResult fmResult = gson.fromJson(newResult, FMResult.class);

                                                results.add(fmResult);
                                            }
                                        }
                                    });
                                }

                                //展示FM
                                ShowIntroduce(recyclerView, results);
                                if (firstInitBroadcastPlayer) {
                                    //初始化播放器
                                    initBroadcastPlayer();
                                }

                            } else {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    private void initBroadcastPlayer() {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {

                    Thread.sleep(100); //便于加载动画的流畅，及数据的加载

                    //对来电进行监听
                    TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    telephonyManager.listen(new MyPhoneListener(), PhoneStateListener.LISTEN_CALL_STATE);

                    //Log.d(TAG, "size = " + fmResults.size());

                    if (player != null) {
                        player.stop();
                        player.mediaPlayer.reset();
                    }
                    String url, cover, title, speak, introduce, viewNum, likeNum;

                    FMResult fmResult = (FMResult) intent.getSerializableExtra(FM_CONTENT);
                    FMResult.DataBean dataBean;

                    if (fmResult != null) {
                        //将从其它分类跳转过来的FM加入播放列表
                        //先从本地获取播放列表
                        Set<String> fmResultSet = sp.getStringSet("songSet", null);
                        //将JSON对象转换为String，并添加到播放列表
                        String fmResultStr = gson.toJson(fmResult);
                        fmResultSet.add(fmResultStr);
                        //确定当前项在播放列表中是第几项
                        Iterator<String> it = fmResultSet.iterator();
                        currentFM = 0;
                        countFM = fmResultSet.size();
                        while (it.hasNext()) {
                            String str = it.next();
                            if (str.equals(fmResultStr))
                                break;
                            ++currentFM;
                        }
                        Log.d(TAG, "currentFM: " + currentFM);
                        //将播放列表存入本地
                        sp.edit().putStringSet("songSet", fmResultSet).apply();

                        dataBean = fmResult.getData();
                        url = dataBean.getUrl();
                        cover = dataBean.getCover();
                        title = dataBean.getTitle();
                        speak = dataBean.getSpeak();
                        introduce = dataBean.getContent();
                        viewNum = dataBean.getViewnum();
                        likeNum = dataBean.getFavnum();
                        player = new BroadcastPlayer(url, skbProgress);
                        player.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        Glide.with(mContext).load(cover).into(fmBackground);
                        fmTitle.setText(title);
                        fmSpeak.setText(speak);
                        fmIntroduce.setText(introduce);
                        fmViewNum.setText(viewNum);
                        fmLikeNum.setText(likeNum);
                        player.play();
                        isPlay = 1;
                        playAndPauseIv.setImageResource(R.drawable.pause);
                    } else {
                        //如果是第一次进入播放界面
                        //先判断本地播放列表是否为空
                        Set<String> fmResultSet = sp.getStringSet("songSet", null);
                        if (fmResultSet == null) {
                            Log.d(TAG, "null: ");
                            //本地播放列表为空，则将从bmob中获取推荐列表中的第一首放入播放列表中，然后将播放列表存于本地
                            Set<String> songSet = new HashSet<>();
                            String fmStr = gson.toJson(fmResults.get(0));
                            songSet.add(fmStr);
                            sp.edit().putStringSet("songSet", songSet).apply();
                            dataBean = fmResults.get(0).getData();
                        } else {
                            Log.d(TAG, "not null: ");
                            //播放列表不为空，则从本地获取播放列表中的第一首
                            FMResult fmResult1;
                            Iterator it = fmResultSet.iterator();
                            fmResult1 = gson.fromJson(it.next().toString(), FMResult.class);
                            dataBean = fmResult1.getData();
                        }
                        currentFM = 0; //记录当前是播放列表中的第几首
                        url = dataBean.getUrl();
                        cover = dataBean.getCover();
                        title = dataBean.getTitle();
                        speak = dataBean.getSpeak();
                        introduce = dataBean.getContent();
                        viewNum = dataBean.getViewnum();
                        likeNum = dataBean.getFavnum();
                        player = new BroadcastPlayer(url, skbProgress);
                        Glide.with(mContext).load(cover).into(fmBackground);
                        fmTitle.setText(title);
                        fmSpeak.setText(speak);
                        fmIntroduce.setText(introduce);
                        fmViewNum.setText(viewNum);
                        fmLikeNum.setText(likeNum);
                    }

                    //播放列表展示
                    ShowFMInSongList();

                    //去除等待动画
                    zLoadingLayout.setVisibility(View.GONE);
                    //显示FM详情
                    fmContextLayout.setVisibility(View.VISIBLE);


                    Log.d(TAG, "自动播放下一首");
                    //自动播放下一首
                    player.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            Log.d(TAG, "currentFM: " + currentFM + " countFM: " + countFM);
                            if (currentFM < countFM - 1) {
                                player.stop();
                                currentFM += 1;
                                FMResult.DataBean dataBean = songList.get(currentFM).getData();
                                String url = dataBean.getUrl();
                                player = new BroadcastPlayer(url, skbProgress);
                                Glide.with(mContext).load(dataBean.getCover()).into(fmBackground);
                                fmTitle.setText(dataBean.getTitle());
                                fmSpeak.setText(dataBean.getSpeak());
                                fmIntroduce.setText(dataBean.getContent());
                                fmViewNum.setText(dataBean.getViewnum());
                                fmLikeNum.setText(dataBean.getFavnum());
                                player.play();
                                isPlay = 1;
                                playAndPauseIv.setImageResource(R.drawable.pause);
                            } else {
                                Toast.makeText(mContext, "φ(>ω<*)，播放完了，快去添加歌曲吧", Toast.LENGTH_SHORT).show();
                                playAndPauseIv.setImageResource(R.drawable.play);
                            }
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /*
     * 只有电话来了之后才暂停音乐的播放
     * */
    private final class MyPhoneListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING://电话来了
                    player.callIsComing();
                    break;
                case TelephonyManager.CALL_STATE_IDLE: //通话结束
                    player.callIsDown();
                    break;
            }
        }
    }

    //播放按钮事件处理
    class ClickEvent implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            if (arg0 == playAndPauseIv) {
                if (isPlay == 0) {
                    player.play();
                    isPlay = 1;
                    playAndPauseIv.setImageResource(R.drawable.pause);
                } else if (isPlay == 1) {
                    boolean pause = player.pause();
                    if (pause) {
                        playAndPauseIv.setImageResource(R.drawable.play);
                    } else {
                        playAndPauseIv.setImageResource(R.drawable.pause);
                    }
                }
            } else if (arg0 == previousIv) { //上一首
                Log.d(TAG, "previousIv previousIv = " + currentFM);
                if (currentFM > 0) {
                    player.stop();
                    currentFM -= 1;
                    FMResult.DataBean dataBean = songList.get(currentFM).getData();
                    String url = dataBean.getUrl();
                    player = new BroadcastPlayer(url, skbProgress);
                    Glide.with(mContext).load(dataBean.getCover()).into(fmBackground);
                    fmTitle.setText(dataBean.getTitle());
                    fmSpeak.setText(dataBean.getSpeak());
                    fmIntroduce.setText(dataBean.getContent());
                    fmViewNum.setText(dataBean.getViewnum());
                    fmLikeNum.setText(dataBean.getFavnum());
                    player.play();
                    isPlay = 1;
                    playAndPauseIv.setImageResource(R.drawable.pause);
                } else {
                    Toast.makeText(mContext, "φ(>ω<*)，已经是第一首了", Toast.LENGTH_SHORT).show();
                }
            } else if (arg0 == nextIv) { //下一首
                Log.d(TAG, "nextIv previousIv = " + currentFM + " countFM = " + countFM);
                if (currentFM < countFM - 1) {
                    player.stop();
                    currentFM += 1;
                    FMResult.DataBean dataBean = songList.get(currentFM).getData();
                    String url = dataBean.getUrl();
                    player = new BroadcastPlayer(url, skbProgress);
                    Glide.with(mContext).load(dataBean.getCover()).into(fmBackground);
                    fmTitle.setText(dataBean.getTitle());
                    fmSpeak.setText(dataBean.getSpeak());
                    fmIntroduce.setText(dataBean.getContent());
                    fmViewNum.setText(dataBean.getViewnum());
                    fmLikeNum.setText(dataBean.getFavnum());
                    player.play();
                    isPlay = 1;
                    playAndPauseIv.setImageResource(R.drawable.pause);
                } else {
                    Toast.makeText(mContext, "φ(>ω<*)，已经是最后一首了", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //进度条事件处理
    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
        int progress;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            // 原本是(progress/seekBar.getMax())*player.mediaPlayer.getDuration()
            this.progress = progress * player.mediaPlayer.getDuration()
                    / seekBar.getMax();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字
            player.mediaPlayer.seekTo(progress);
        }
    }

    //创建消息处理器对象
    @SuppressLint("HandlerLeak")
    public static Handler handler = new Handler() {

        //在主线程中处理从子线程发送过来的消息
        @Override
        public void handleMessage(Message msg) {

            //获取从子线程发送过来的音乐播放的进度
            Bundle bundle = msg.getData();

            //歌曲的总时长(毫秒)
            int duration = bundle.getInt("duration");

            //歌曲的当前进度(毫秒)
            int currentPostition = bundle.getInt("currentPosition");

            //歌曲的总时长
            int minute = duration / 1000 / 60;
            int second = duration / 1000 % 60;

            String strMinute = null;
            String strSecond = null;

            //如果歌曲的时间中的分钟小于10
            if (minute < 10) {

                //在分钟的前面加一个0
                strMinute = "0" + minute;
            } else {

                strMinute = minute + "";
            }

            //如果歌曲的时间中的秒钟小于10
            if (second < 10) {
                //在秒钟前面加一个0
                strSecond = "0" + second;
            } else {

                strSecond = second + "";
            }

            tv_total.setText(strMinute + ":" + strSecond);

            //歌曲当前播放时长
            minute = currentPostition / 1000 / 60;
            second = currentPostition / 1000 % 60;

            //如果歌曲的时间中的分钟小于10
            if (minute < 10) {

                //在分钟的前面加一个0
                strMinute = "0" + minute;
            } else {

                strMinute = minute + "";
            }

            //如果歌曲的时间中的秒钟小于10
            if (second < 10) {

                //在秒钟前面加一个0
                strSecond = "0" + second;
            } else {

                strSecond = second + "";
            }

            tv_progress.setText(strMinute + ":" + strSecond);
        }
    };

    private void ShowIntroduce(final RecyclerView recyclerView, final List<FMResult> results) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);

                    Log.d(TAG, "fmResults size = " + results.size());

                    LinearLayoutManager manager = new LinearLayoutManager(mContext);
                    manager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(manager);
                    FMItemAdapter adapter = new FMItemAdapter(results);
                    recyclerView.setAdapter(adapter);


                    //将获取到的FM列表保存到本地，且只有从bmob获取时才保存，从本地获取不保存
                    if (isLoadFromBmob) {
                        isLoadFromBmob = false;
                        Log.d(TAG, "saveToLocalSet.size() = " + saveToLocalSet.size());
                        sp.edit().putStringSet("fmResults", saveToLocalSet).apply();
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //展示播放列表
    private void ShowFMInSongList() {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);

                    Set<String> fmResultSet = sp.getStringSet("songSet", null);
                    FMResult fmResult;
                    for (String str : fmResultSet) {
                        //Log.d(TAG, "fmResultSet: " + str);
                        fmResult = gson.fromJson(str, FMResult.class);
                        songList.add(fmResult);
                    }

                    Log.d(TAG, "songSet.size() = " + songList.size());

                    LinearLayoutManager manager = new LinearLayoutManager(mContext);
                    songListRv.setLayoutManager(manager);
                    songListRv.setAdapter(songListAdapter);

                    countFM = songList.size(); //播放列表中有多少首

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stop();
        sp.edit().remove("fmResults"); //活动销毁时删除存储在本地的fm列表
        sp.edit().commit();
    }
}
