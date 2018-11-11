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

import com.android.dcxiaolou.innervoice.util.*;

/*
 * FM模块详情页
 * */

public class ShowFMActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "ShowFMActivity";
    public final static String FM_CONTENT = "fm_content";
    public final static String FM_ITEMNO = "fm_item_no";

    private boolean menuOpen = false;
    private int fmMenuItemOpen = 0;
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

    private RecyclerView introduceRv, moodRv, sceneRv;

    private List<FMResult> fmResults, fmNewResults;

    private ImageView playAndPauseIv, previousIv, nextIv;
    private SeekBar skbProgress;
    private static TextView tv_progress;
    private static TextView tv_total;
    private BroadcastPlayer player;
    private ImageView fmBackground;
    private TextView fmTitle, fmSpeak, fmIntroduce, fmViewNum, fmLikeNum;

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
            android.os.Process.killProcess(android.os.Process.myPid());
            return true;
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
                    //初始化播放器
                    initBroadcastPlayer();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }


    private void initBroadcastPlayer() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);

                    fmBackground = (ImageView) findViewById(R.id.fm_background);
                    fmTitle = (TextView) findViewById(R.id.fm_title);
                    fmSpeak = (TextView) findViewById(R.id.fm_speak);
                    fmIntroduce = (TextView) findViewById(R.id.fm_introduce);
                    fmViewNum = (TextView) findViewById(R.id.fm_view_num);
                    fmLikeNum = (TextView) findViewById(R.id.fm_like_num);

                    previousIv = (ImageView) findViewById(R.id.previous_iv);
                    playAndPauseIv = (ImageView) findViewById(R.id.play_pause_iv);
                    nextIv = (ImageView) findViewById(R.id.next_iv);

                    previousIv.setOnClickListener(new ClickEvent());
                    playAndPauseIv.setOnClickListener(new ClickEvent());
                    nextIv.setOnClickListener(new ClickEvent());

                    skbProgress = (SeekBar) findViewById(R.id.skbProgress);
                    skbProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());

                    tv_progress = (TextView) findViewById(R.id.tv_progress);
                    tv_total = (TextView) findViewById(R.id.tv_total);

                    TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    telephonyManager.listen(new MyPhoneListener(), PhoneStateListener.LISTEN_CALL_STATE);

                    //Log.d(TAG, "size = " + fmResults.size());
                    countFM = fmResults.size();
                    currentFM = 0;
                    if (player != null) {
                        player.stop();
                        player.mediaPlayer.reset();
                    }
                    String url, cover, title, speak, introduce, viewNum, likeNum;
                    //处理通过列表点击跳转过来的
                    Intent intent = getIntent();
                    FMResult fmResult = (FMResult) intent.getSerializableExtra(FM_CONTENT);
                    int itemNo = intent.getIntExtra(FM_ITEMNO, -1);
                    if (itemNo != -1) {
                        currentFM = itemNo;
                        Log.d(TAG, "currentFM = " + currentFM);
                    }
                    FMResult.DataBean dataBean;

                    if (fmResult != null) {
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
                        dataBean = fmResults.get(0).getData();
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

                    //自动播放下一首
                    player.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            if (currentFM < countFM - 1) {
                                player.stop();
                                currentFM += 1;
                                FMResult.DataBean dataBean = fmResults.get(currentFM).getData();
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
                                Toast.makeText(mContext, "φ(>ω<*)，播放完了，换个歌单吧", Toast.LENGTH_SHORT).show();
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

    /**
     * 只有电话来了之后才暂停音乐的播放
     */
    private final class MyPhoneListener extends android.telephony.PhoneStateListener {
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
            } else if (arg0 == previousIv) {
                Log.d(TAG, "previousIv previousIv = " + currentFM);
                if (currentFM > 0) {
                    player.stop();
                    currentFM -= 1;
                    FMResult.DataBean dataBean = fmResults.get(currentFM).getData();
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
            } else if (arg0 == nextIv) {
                Log.d(TAG, "nextIv previousIv = " + currentFM + " countFM = " + countFM);
                if (currentFM < countFM - 1) {
                    player.stop();
                    currentFM += 1;
                    FMResult.DataBean dataBean = fmResults.get(currentFM).getData();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stop();
    }
}
