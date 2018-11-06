package com.android.dcxiaolou.innervoice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.dcxiaolou.innervoice.mode.CourseDetail;
import com.android.dcxiaolou.innervoice.mode.CourseDetailResult;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.io.IOException;
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
* 课程的播放
* */

public class CoursePlay extends AppCompatActivity {

    private final static String TAG = "CoursePlay";
    public final static String COURSE_NO = "course_no"; //当前是该课程的第几个音频

    private Context mContext;
    private Handler mHandler = new Handler();

    private String courseId, courseCover, courseTitle;
    private int courseNo;

    private String courseUrl;

    StandardGSYVideoPlayer videoPlayer;

    OrientationUtils orientationUtils;

    private String playType;

    private int playState = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_play);

        mContext = getApplicationContext();

        videoPlayer =  (StandardGSYVideoPlayer)findViewById(R.id.course_player);

        Intent intent = getIntent();
        courseNo = intent.getIntExtra(COURSE_NO, 0); // 从0开始

        SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);
        courseId = sp.getString("courseId", null);
        courseCover = sp.getString("courseCover", null);
        courseTitle = sp.getString("courseTitle", null);
        Log.d(TAG, courseId + " " + courseNo + " " + courseCover + " " + courseTitle);

        //从bmob获取相应的课程音频json文件地址
        getCourseForBmob(courseId, courseNo + 1);
        //使用Gson解析courseUrl的json文件，得到相应的课程音频地址
        parseJsonByGson();
    }

    private void getCourseForBmob(String courseId, int courseNo) {
        final String courseFileName = courseId + "_" + courseNo + ".json";
        Log.d(TAG, "courseFileName = " + courseFileName);
        BmobQuery<CourseDetail> query = new BmobQuery<>();
        query.addQueryKeys("detail");
        query.setLimit(200); //默认最大返回100条
        query.findObjects(new FindListener<CourseDetail>() {
            @Override
            public void done(List<CourseDetail> list, BmobException e) {
                if (e == null) {
                    String fileName;
                    BmobFile file;
                    for (CourseDetail detail : list) {
                        file = detail.getDetail();
                        fileName = file.getFilename();
                        //Log.d(TAG, "fileName = " + fileName);
                        if (fileName.equals(courseFileName)) {
                            Log.d(TAG, fileName);
                            courseUrl = file.getUrl();
                            break;
                        }
                    }
                }
            }
        });
    }

    private void parseJsonByGson() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000); //停顿1s，以便获取数据
                    Log.d(TAG, "courseUrl = " + courseUrl);
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(courseUrl).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String result = response.body().string();
                            Gson gson = new Gson();
                            CourseDetailResult detailResult = gson.fromJson(result, CourseDetailResult.class);
                            String coursePlayUrl = detailResult.getMedia();
                            // 初始化GSYVideoPlayer，用来播放课程
                            init(coursePlayUrl);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void init(final String coursePlayUrl) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000); //停顿1s，便于获取coursePlayUrl
                    Log.d(TAG, "coursePlayUrl = " + coursePlayUrl);
                    int length = coursePlayUrl.length();
                    playType = coursePlayUrl.substring(length-3, length);
                    Log.d(TAG, "playType = " + playType); //获取视频的类型（MP3、MP4）
                    videoPlayer.setUp(coursePlayUrl, true, "测试视频");
                    //增加封面
                    final ImageView imageView = new ImageView(mContext);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                    options.override(500, 400);
                    Glide.with(mContext).load(courseCover).apply(options).into(imageView);
                    videoPlayer.setThumbImageView(imageView);
                    //增加title
                    videoPlayer.getTitleTextView().setText(courseTitle);
                    //设置返回键
                    videoPlayer.getBackButton().setImageResource(R.drawable.video_back);
                    //设置旋转
                    orientationUtils = new OrientationUtils(CoursePlay.this, videoPlayer);
                    //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
                    videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            orientationUtils.resolveByClick();
                        }
                    });
                    //是否可以滑动调整
                    videoPlayer.setIsTouchWiget(true);
                    //设置返回按键功能
                    videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    });

                    final int imageHeight = imageView.getHeight();
                    final int imageWidth = imageView.getWidth();

                    final int width = videoPlayer.getWidth();
                    final int height = videoPlayer.getHeight();

                    // 播放按钮
                    final View playBtn = videoPlayer.getStartButton();
                    playBtn.setVisibility(View.VISIBLE);
                    playBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (playState == 1) { // 第一次播放
                                videoPlayer.startPlayLogic();
                                if (playType.equals("mp3")) { // 播放mp3时只显示光碟旋转动画
                                    ViewGroup parent = (ViewGroup) imageView.getParent();
                                    parent.removeView(imageView);
                                    // 使用glide加载gif动画
                                    RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                                    options.override(400, 400);
                                    Glide.with(mContext).load(R.drawable.rotation).apply(options).into(imageView);
                                    videoPlayer.addView(imageView);
                                    playBtn.setY(height - 120);
                                    playBtn.setX(-imageWidth + 10);
                                }
                                playState = 2;
                            } else if (playState == 2) { // 暂停
                                videoPlayer.onVideoPause();
                                if (playType.equals("mp3")) {
                                    ViewGroup parent = (ViewGroup) imageView.getParent();
                                    parent.removeView(imageView);
                                    RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                                    options.override(400, 400);
                                    Glide.with(mContext).load(courseCover).apply(options).into(imageView);
                                    videoPlayer.addView(imageView);
                                    playBtn.setY(height - 120);
                                    playBtn.setX(-imageWidth + 10);
                                }
                                playBtn.setVisibility(View.VISIBLE);
                                playState = 3;
                            } else { // 恢复播放
                                videoPlayer.onVideoResume();
                                if (playType.equals("mp3")) {
                                    ViewGroup parent = (ViewGroup) imageView.getParent();
                                    parent.removeView(imageView);
                                    // 使用glide加载gif动画
                                    RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                                    options.override(400, 400);
                                    Glide.with(mContext).load(R.drawable.rotation).apply(options).into(imageView);
                                    videoPlayer.addView(imageView);
                                    playBtn.setY(height - 120);
                                    playBtn.setX(-imageWidth + 10);
                                }
                                playState = 2;
                            }
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onBackPressed() {
        //先返回正常状态
        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            videoPlayer.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        videoPlayer.setVideoAllCallBack(null);
        super.onBackPressed();
    }
}
