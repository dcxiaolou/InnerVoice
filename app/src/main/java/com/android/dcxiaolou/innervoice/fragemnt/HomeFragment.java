package com.android.dcxiaolou.innervoice.fragemnt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.dcxiaolou.innervoice.ConsultActivity;
import com.android.dcxiaolou.innervoice.R;
import com.android.dcxiaolou.innervoice.ShowCourseActivity;
import com.android.dcxiaolou.innervoice.ShowFMActivity;
import com.android.dcxiaolou.innervoice.ShowQuestionActivity;
import com.android.dcxiaolou.innervoice.ShowReadArticleActivity;
import com.android.dcxiaolou.innervoice.TestActivity;
import com.android.dcxiaolou.innervoice.adapter.CourseIntroduceAdapter;
import com.android.dcxiaolou.innervoice.adapter.ReadArticleAdapter;
import com.android.dcxiaolou.innervoice.mode.ADBanner;
import com.android.dcxiaolou.innervoice.mode.CourseCollect;
import com.android.dcxiaolou.innervoice.mode.CourseGuide;
import com.android.dcxiaolou.innervoice.mode.ReadArticle;
import com.android.dcxiaolou.innervoice.mode.ReadArticleResult;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

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
 * 主页碎片
 * */

public class HomeFragment extends Fragment implements OnBannerListener, View.OnClickListener {

    private final static String TAG = "HomeFragment";

    private Banner mAdBanner;

    private View mRootView;
    private Context mContext;
    private Handler mHandler = new Handler();

    private List<String> bannerPathList;

    private RecyclerView recyclerView;
    private List<CourseGuide> courseGuides;

    private RecyclerView dailyBestRv;
    private  List<ReadArticleResult> readArticleResults = new ArrayList<>();

    private Boolean isFirstLoad = true;
    private int skipNum = 0;
    private boolean haveMoreData = true;

    private ReadArticleAdapter readArticleAdapter;

    //刷新模块
    private SmartRefreshLayout refreshLayout;

    //子菜单项
    private LinearLayout readLayout, courseLayout, fmLayout, questionAndAnswerLayout, consultLayout, testLayout;

    // 创建view
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_home, null);
        mContext = getContext();

        return mRootView;
    }

    //处理逻辑
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        refreshLayout = (SmartRefreshLayout) mRootView.findViewById(R.id.smart_refresh_layout);
        dailyBestRv = (RecyclerView) mRootView.findViewById(R.id.daily_best);

        mAdBanner = (Banner) mRootView.findViewById(R.id.adbanner);
        recyclerView = (RecyclerView) mRootView.findViewById(R.id.course_recommend_rv);

        readLayout = (LinearLayout) mRootView.findViewById(R.id.read_layout);
        courseLayout = (LinearLayout) mRootView.findViewById(R.id.course_layout);
        fmLayout = (LinearLayout) mRootView.findViewById(R.id.fm_layout);
        questionAndAnswerLayout = (LinearLayout) mRootView.findViewById(R.id.question_and_answer_Linear_layout);
        consultLayout = (LinearLayout) mRootView.findViewById(R.id.consult_linear_layout);
        testLayout = (LinearLayout) mRootView.findViewById(R.id.test_linear_layout);

        readArticleAdapter = new ReadArticleAdapter(readArticleResults);

        //首次加载数据
        if (isFirstLoad) {
            refreshLayout.autoRefresh();//首次加载启动自动刷新
            refreshLayout.finishRefresh();//结束刷新
            isFirstLoad = false;
        }

        //刷新模块添加下拉刷新监听
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if (haveMoreData) {
                    readArticleResults.clear();
                    // 每日精选模块 采用RecyclerView来显示
                    initDailyBest();
                } else {
                    Toast.makeText(mContext, "φ(>ω<*) 暂无更新", Toast.LENGTH_SHORT).show();
                }
                refreshLayout.finishRefresh();
            }
        });

        //刷新模块添加上拉加载更多模块
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (haveMoreData) {
                    initDailyBest();
                } else {
                    Toast.makeText(mContext, "φ(>ω<*) 没有更多文章了", Toast.LENGTH_SHORT).show();
                }
                refreshLayout.finishLoadMore();
            }
        });

        // 请求后台数据 从bmob获取banner图片，并用banner展示
        requestHomeBanner();

        // 课程推荐模块 采用RecyclerView来显示
        initCourse(); //初始化内容

        //给子菜单项添加点击事件
        readLayout.setOnClickListener(this);
        courseLayout.setOnClickListener(this);
        fmLayout.setOnClickListener(this);
        questionAndAnswerLayout.setOnClickListener(this);
        consultLayout.setOnClickListener(this);
        testLayout.setOnClickListener(this);

    }

    // banner的点击响应事件，下标从0开始
    @Override
    public void OnBannerClick(int position) {

    }

    //子菜单项的点击事件处理
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.read_layout:
                Intent readIntent = new Intent(mContext, ShowReadArticleActivity.class);
                mContext.startActivity(readIntent);
                break;
            case R.id.course_layout:
                Intent courseIntent = new Intent(mContext, ShowCourseActivity.class);
                mContext.startActivity(courseIntent);
                break;
            case R.id.fm_layout:
                Intent fmIntent = new Intent(mContext, ShowFMActivity.class);
                mContext.startActivity(fmIntent);
                break;
            case R.id.question_and_answer_Linear_layout:
                Intent qaaIntent = new Intent(mContext, ShowQuestionActivity.class);
                mContext.startActivity(qaaIntent);
                break;
            case R.id.consult_linear_layout:
                Intent consultIntent = new Intent(mContext, ConsultActivity.class);
                mContext.startActivity(consultIntent);
                break;
            case R.id.test_linear_layout:
                Intent testIntent = new Intent(mContext, TestActivity.class);
                mContext.startActivity(testIntent);
                break;
            default:
                break;
        }
    }

    //重写banner图片加载器
    public class GlideImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    }

    private void requestHomeBanner() {

        //从bmob获取图片，并用banner展示
        BmobQuery<ADBanner> bmobQuery = new BmobQuery<>();
        bmobQuery.addQueryKeys("banner"); // 按列查询
        bmobQuery.findObjects(new FindListener<ADBanner>() {
            @Override
            public void done(List<ADBanner> list, BmobException e) {
                if (e == null) {
                    Log.d(TAG, "" + list.size());
                    bannerPathList = new ArrayList<>();
                    BmobFile bannerFile;
                    for (ADBanner banner : list) {
                        Log.d(TAG, banner.getObjectId());
                        bannerFile = banner.getBmobFile();
                        if (bannerFile != null) {
                            String url = bannerFile.getUrl();
                            Log.d(TAG, bannerFile.getUrl());
                            bannerPathList.add(url);
                        } else {
                            Log.d(TAG, "bannerFile is null");
                        }
                    }

                    mAdBanner.setImageLoader(new GlideImageLoader()); // 设置图片加载器
                    mAdBanner.setImages(bannerPathList); // 设置图片集合
                    mAdBanner.setBannerAnimation(Transformer.Stack); // 设置banner动画效果
                    mAdBanner.isAutoPlay(true); // 设置自动轮播
                    mAdBanner.setDelayTime(2000); // 设置轮播时间
                    mAdBanner.setIndicatorGravity(BannerConfig.CENTER); // 设置指示器位置（当banner模式中有指示器时）
                    mAdBanner.start(); // banner设置方法全部调用完时最后使用

                } else {
                    Log.d(TAG, e.getMessage());
                }
            }
        });
    }

    private void initCourse() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(0);

                    courseGuides = new ArrayList<>();
                    // 使用okhttp获取课程引导信息
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url("http://bmob-cdn-22224.b0.upaiyun.com/2018/11/11/03e4f6834038a54f80124fdfb9c6555a.json").build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String result = response.body().string();
                            //Log.d(TAG, result);
                            // 使用Gson解析返回的json信息
                            Gson gson = new Gson();
                            CourseCollect courseCollect = gson.fromJson(result, CourseCollect.class);
                            CourseGuide courseGuide;
                            for (int i = 0; i < courseCollect.getData().getItems().size(); i++) {
                                courseGuide = new CourseGuide();
                                courseGuide.setId(courseCollect.getData().getItems().get(i).getId());
                                courseGuide.setTitle(courseCollect.getData().getItems().get(i).getTitle());
                                courseGuide.setCover(courseCollect.getData().getItems().get(i).getCover());
                                courseGuide.setJoinnum(courseCollect.getData().getItems().get(i).getJoinnum());
                                courseGuide.setTeacherName(courseCollect.getData().getItems().get(i).getTeacherName());
                                courseGuides.add(courseGuide);
                            }

                        }
                    });
                    showCourseGuide(courseGuides); // 展示内容

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void showCourseGuide(final List<CourseGuide> courseGuides) {
        mHandler.post(new Runnable() { //执行在主线程中
            @Override
            public void run() {
                try {
                    Thread.sleep(500); // 让主线程等待1s，以便获取相关数据
                    Log.d(TAG, "courseGuides size = " + courseGuides.size());
                    GridLayoutManager manager = new GridLayoutManager(mContext, 1);
                    manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    recyclerView.setLayoutManager(manager);
                    CourseIntroduceAdapter courseIntroduceAdapter = new CourseIntroduceAdapter(courseGuides);
                    recyclerView.setAdapter(courseIntroduceAdapter);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initDailyBest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(0);

                    BmobQuery<ReadArticle> query = new BmobQuery<>();
                    query.addQueryKeys("article");
                    query.setLimit(5); // 返回5条数据
                    query.setSkip(skipNum); //每次跳过的数据项，即分页
                    query.order("-createdAt");
                    query.findObjects(new FindListener<ReadArticle>() {
                        @Override
                        public void done(List<ReadArticle> list, BmobException e) {
                            if (list.size() < 5) {
                                haveMoreData = false;
                            }
                            if (e == null) {
                                Log.d(TAG, "dailyBestRv size = " + list.size());
                                BmobFile file;
                                for (ReadArticle readArticle : list) {
                                    file = readArticle.getBmobFile();
                                    if (file != null) {
                                        String fileUrl = file.getUrl();
                                        String fileName = file.getFilename();
                                        Log.d(TAG, fileName);
                                        // 使用okhttp获取相应得文章
                                        OkHttpClient client = new OkHttpClient();
                                        Request request = new Request.Builder().url(fileUrl).build();
                                        client.newCall(request).enqueue(new Callback() {
                                            @Override
                                            public void onFailure(Call call, IOException e) {
                                                e.printStackTrace();
                                            }

                                            @Override
                                            public void onResponse(Call call, Response response) throws IOException {
                                                String result = response.body().string();
                                                Log.d(TAG, result);
                                                // 使用Gson解析返回的json数据
                                                Gson gson = new Gson();
                                                ReadArticleResult readArticleResult = gson.fromJson(result, ReadArticleResult.class);
                                                readArticleResults.add(readArticleResult);
                                            }
                                        });
                                    } else {
                                        Log.d(TAG, "dailyBestRv bmobFile is null");
                                    }
                                }

                                showDailyBest(readArticleResults, mContext); //展示每日精选模块
                                skipNum += 5;

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

    private void showDailyBest(final List<ReadArticleResult> readArticleResults, final Context mContext) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500); // 让主线程等待，以便获取相关数据
                    Log.d(TAG, "showDailyBest size = " + readArticleResults.size());
                    //采用NestedScrollView + RecyclerView （NestedScrollView代替ScrollView可解决滑动冲突问题）
                    //解决触摸到RecyclerView的时候滑动有些粘连的感觉
                    dailyBestRv.setNestedScrollingEnabled(false);
                    LinearLayoutManager manager = new LinearLayoutManager(mContext);
                    manager.setOrientation(LinearLayoutManager.VERTICAL);
                    dailyBestRv.setLayoutManager(manager);
                    dailyBestRv.setAdapter(readArticleAdapter);
                    readArticleAdapter.notifyDataSetChanged();//数据改变
                    dailyBestRv.scrollToPosition(readArticleResults.size() - 5 - 1);//从新定位
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
