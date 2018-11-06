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
import android.widget.LinearLayout;

import com.android.dcxiaolou.innervoice.R;
import com.android.dcxiaolou.innervoice.adapter.CourseIntroduceCommonAdapter;
import com.android.dcxiaolou.innervoice.mode.CourseIntroduceCommon;
import com.android.dcxiaolou.innervoice.mode.CourseIntroduceCommonResult;
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
* 课程推荐（简介）界面的评论页碎片
* */

public class CourseCommonFragment extends Fragment {

    private final static String TAG = "CourseCommonFragment";

    private View mViewRoot;
    private Context mContext;
    private Handler mHandler = new Handler();

    private RecyclerView courseCommonRv;

    private List<CourseIntroduceCommon> commons = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mViewRoot = inflater.inflate(R.layout.fragment_course_common, null);
        mContext = getContext();
        return mViewRoot;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 初始化数据
        initData();
        // 展示评论
        showCommon(commons);
    }

    private void initData() {
        // 获取courseId
        SharedPreferences ps = mContext.getSharedPreferences("info", Context.MODE_PRIVATE);
        String courseId = ps.getString("courseId", null);
        // https://m.xinli001.com/lesson/rate-list?lesson_id=194&page=1&size=10
        String commonUrl = "https://m.xinli001.com/lesson/rate-list?lesson_id=" + courseId + "&page=1&size=10";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(commonUrl).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.d(TAG, result);
                Gson gson = new Gson();
                CourseIntroduceCommonResult commonResult = gson.fromJson(result, CourseIntroduceCommonResult.class);
                List<CourseIntroduceCommonResult.DataBean> dataBeans = commonResult.getData();
                CourseIntroduceCommonResult.DataBean dataBean;
                CourseIntroduceCommon common;
                for (int i = 0; i < dataBeans.size(); i++) {
                    dataBean = dataBeans.get(i);
                    common = new CourseIntroduceCommon();
                    common.setId("" + dataBean.getId());
                    common.setUserId("" + dataBean.getUserId());
                    common.setUserName(dataBean.getUserNickname());
                    common.setUserImagePath(dataBean.getUserAvatar());
                    common.setPushTime("" + dataBean.getCreated());
                    common.setContent(dataBean.getContent());
                    common.setLikeNum("" + dataBean.getZanNum());
                    common.setReplyNum("" + dataBean.getCollegeRateReplyVos().size());
                    common.setReplayCommons(dataBean.getCollegeRateReplyVos());
                    commons.add(common);
                }
            }
        });
    }


    private void showCommon(final List<CourseIntroduceCommon> commons) {
        mHandler.post(new Runnable() { // 执行在主线程中
            @Override
            public void run() {
                try {
                    Thread.sleep(1000); // 让主线程等待1s，以便于获取到数据
                    courseCommonRv = (RecyclerView) mViewRoot.findViewById(R.id.course_common_rv);
                    LinearLayoutManager manager = new LinearLayoutManager(mContext);
                    manager.setOrientation(LinearLayoutManager.VERTICAL);
                    courseCommonRv.setLayoutManager(manager);
                    CourseIntroduceCommonAdapter adapter = new CourseIntroduceCommonAdapter(commons);
                    courseCommonRv.setAdapter(adapter);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
