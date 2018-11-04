package com.android.dcxiaolou.innervoice.fragemnt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.dcxiaolou.innervoice.R;
import com.android.dcxiaolou.innervoice.mode.CourseIntroduce;
import com.android.dcxiaolou.innervoice.mode.CourseIntroduceForShow;
import com.google.gson.Gson;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

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
* 课程简介界面的详情页碎片
* */

public class CourseDetailFragment extends Fragment {

    private final static String TAG = "CourseDetailFragment";

    private String courseId, coverPath;

    private Context mContext;
    private View mRootView;
    private Handler mHandler = new Handler();

    private HtmlTextView courseDetail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_course_detail, null);
        mContext = getContext();
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        courseDetail = (HtmlTextView) mRootView.findViewById(R.id.course_detail);
        SharedPreferences sp = mContext.getSharedPreferences("info", Context.MODE_PRIVATE);
        courseId = sp.getString("courseId", null);
        Log.d(TAG, "courseId " + courseId);
        // 从bmob查询该课程id为courseId的json文件的url
        getJsonForCourseIntroduce(courseId);
    }

    private void getJsonForCourseIntroduce(final String courseId) {
        Log.d(TAG, "getJsonForCourseIntroduce courseId = " + courseId);
        BmobQuery<CourseIntroduce> query = new BmobQuery<>();
        query.addQueryKeys("introduce");
        query.findObjects(new FindListener<CourseIntroduce>() {
            @Override
            public void done(List<CourseIntroduce> list, BmobException e) {
                if (e == null) {
                    Log.d(TAG, "list size = " + list.size());
                    String filePath = null;
                    for (CourseIntroduce courseIntroduce : list) {
                        BmobFile file = courseIntroduce.getIntroduce();
                        String filename = file.getFilename();
                        Log.d(TAG, filename);
                        if (filename.equals(courseId + ".json")) {
                            filePath = file.getUrl();
                            Log.d(TAG, filePath);
                        }
                    }
                    if (filePath != null) {
                        getCourseIntroduce(filePath); // 获取json文件
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getCourseIntroduce(String filePath) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(filePath).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Gson gson = new Gson();
                CourseIntroduceForShow courseIntroduceForShow = gson.fromJson(result, CourseIntroduceForShow.class);
                final String content = courseIntroduceForShow.getIntroduce().get(0);
                content.replace("\n", "");
                 Log.d(TAG, result);
                mHandler.post(new Runnable() { // 显示课程详细信息
                    @Override
                    public void run() {
                        courseDetail.setHtml(content, new HtmlHttpImageGetter(courseDetail));
                    }
                });
            }
        });

    }

}
