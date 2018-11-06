package com.android.dcxiaolou.innervoice.fragemnt;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.dcxiaolou.innervoice.R;
import com.android.dcxiaolou.innervoice.adapter.CourseIntroduceCatalogAdapter;
import com.android.dcxiaolou.innervoice.mode.CourseIntroduceCatalog;
import com.android.dcxiaolou.innervoice.mode.CourseIntroduceCatalogResult;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
* 课程推荐（简介）界面的目录页碎片
* */

public class CourseCatalogFragment extends Fragment {

    private final static String TAG = "CourseCatalogFragment";

    private Context mContext;
    private View mRootView;
    private Handler mHandler = new Handler();

    private String courseId, catalogPath;

    private RecyclerView courseCatalogRv;
    private List<CourseIntroduceCatalog> courseIntroduceCatalogs = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_course_catalog, null);
        mContext = getContext();
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // https://m.xinli001.com/lesson/getPeriodList?lesson_id=194&__from__=detail
        SharedPreferences sp = mContext.getSharedPreferences("info", Context.MODE_PRIVATE);
        courseId = sp.getString("courseId", null);
        // Log.d(TAG, "courseId = " + courseId);
        catalogPath = "https://m.xinli001.com/lesson/getPeriodList?lesson_id=" + courseId + "&__from__=detail";
        Log.d(TAG, "catalogPath = " + catalogPath);
        // 使用okhttp获取课程目录
        getCatalog(catalogPath);

        // 展示课程目录
        showCatalog();
    }

    private void getCatalog(String catalogPath) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(catalogPath).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                //Log.d(TAG, result);
                Gson gson = new Gson();
                CourseIntroduceCatalogResult catalogResult = gson.fromJson(result, CourseIntroduceCatalogResult.class);
                // 有些课程的目录信息在NoParentListBean中，有些在PassListBean中
                List<CourseIntroduceCatalogResult.DataBean.NoParentListBean> noParentListBeans = catalogResult.getData().getNoParentList();
                List<CourseIntroduceCatalogResult.DataBean.PassListBean> passListBeans = catalogResult.getData().getPassList();
                CourseIntroduceCatalog catalog;
                if (noParentListBeans.size() != 0) {
                    CourseIntroduceCatalogResult.DataBean.NoParentListBean bean;
                    for (int i = 0; i < noParentListBeans.size(); i++) {
                        bean = noParentListBeans.get(i);
                        // Log.d(TAG, bean.getTitle());
                        catalog = new CourseIntroduceCatalog();
                        catalog.setTitle(bean.getTitle());
                        int time = Integer.valueOf(bean.getDuration());
                        // 时间转换
                        String duration = "" + time / 60 + "'" + time % 60 + "\"";
                        catalog.setTime(duration);
                        catalog.setIndex(bean.getIndex());
                        Log.d(TAG, catalog.getTitle());
                        courseIntroduceCatalogs.add(catalog);
                    }
                } else if (passListBeans.get(0).getChild().size() != 0) {
                    CourseIntroduceCatalogResult.DataBean.PassListBean.ChildBean childBean;
                    for (int i = 0; i < passListBeans.get(0).getChild().size(); i++) {
                        childBean = passListBeans.get(0).getChild().get(i);
                        // Log.d(TAG, bean.getTitle());
                        catalog = new CourseIntroduceCatalog();
                        catalog.setTitle(childBean.getTitle());
                        int time = Integer.valueOf(childBean.getDuration());
                        String duration = "" + time / 60 + "'" + time % 60 + "\"";
                        catalog.setTime(duration);
                        catalog.setIndex(childBean.getIndex());
                        Log.d(TAG, catalog.getTitle());
                        courseIntroduceCatalogs.add(catalog);
                    }
                }

            }
        });
    }

    private void showCatalog() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000); // 让主线程停顿1s，便于获取信息
                    // Log.d(TAG, courseIntroduceCatalogs.get(0).getTitle());
                    courseCatalogRv = (RecyclerView) mRootView.findViewById(R.id.course_catalog_rv);
                    LinearLayoutManager manager = new LinearLayoutManager(mContext);
                    manager.setOrientation(LinearLayoutManager.VERTICAL);
                    courseCatalogRv.setLayoutManager(manager);
                    CourseIntroduceCatalogAdapter adapter = new CourseIntroduceCatalogAdapter(courseIntroduceCatalogs);
                    courseCatalogRv.setAdapter(adapter);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
