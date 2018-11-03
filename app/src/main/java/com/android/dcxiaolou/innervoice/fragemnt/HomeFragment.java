package com.android.dcxiaolou.innervoice.fragemnt;

import android.content.Context;
import android.content.RestrictionsManager;
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
import android.widget.RelativeLayout;

import com.android.dcxiaolou.innervoice.R;
import com.android.dcxiaolou.innervoice.adapter.CourseRecommendAdapter;
import com.android.dcxiaolou.innervoice.mode.ADBanner;
import com.android.dcxiaolou.innervoice.mode.CourseCollect;
import com.android.dcxiaolou.innervoice.mode.CourseGuide;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
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
 * 主页
 * */

public class HomeFragment extends Fragment implements OnBannerListener {

    private final static String TAG = "HomeFragment";

    private Banner mAdBanner;

    private View mRootView;
    private Context mComtext;
    private Handler mHandler = new Handler();

    private List<String> bannerPathList;

    private RecyclerView recyclerView;
    private List<CourseGuide> courseGuides;

    // 创建view
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_home, null);
        mComtext = getContext();

        return mRootView;
    }

    //处理逻辑
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdBanner = (Banner) mRootView.findViewById(R.id.adbanner);
        recyclerView = (RecyclerView) mRootView.findViewById(R.id.daily_best_rv);

        //请求后台数据 从bmob获取banner图片，并用banner展示
        requestHomeBanner();

        //课程推荐模块 采用RecyclerView来显示
        initReadArticles(); //初始化内容
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

    private void initReadArticles() {
        courseGuides = new ArrayList<>();
        // 使用okhttp获取课程引导信息
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://m.xinli001.com/lesson/tagList?tag_name=free&page=1&size=20&lesson_type=normal").build();
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
    }

    private void showCourseGuide(final List<CourseGuide> courseGuides) {
        mHandler.post(new Runnable() { //执行在主线程中
            @Override
            public void run() {
                try {
                    Thread.sleep(1000); // 让主线程等待1s，以便获取相关数据
                    // Log.d(TAG, "courseGuides size = " + courseGuides.size());
                    GridLayoutManager manager = new GridLayoutManager(mComtext, 1);
                    manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    recyclerView.setLayoutManager(manager);
                    CourseRecommendAdapter adapter = new CourseRecommendAdapter(courseGuides);
                    recyclerView.setAdapter(adapter);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // banner的点击响应事件，下标从0开始
    @Override
    public void OnBannerClick(int position) {

    }

    //重写banner图片加载器
    public class GlideImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    }


}
