package com.android.dcxiaolou.innervoice;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.dcxiaolou.innervoice.adapter.ShowCourseAdapter;
import com.android.dcxiaolou.innervoice.mode.CourseCollect;
import com.android.dcxiaolou.innervoice.mode.CourseGuide;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ShowCourseActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();

    private List<CourseGuide> courseGuides = new ArrayList<>();

    private RecyclerView courseRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_course);

        // 课程模块 采用RecyclerView来显示
        initCourse(); //初始化内容
        showCourseGuide(courseGuides, this); // 展示内容

    }

    private void initCourse() {
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
    }

    private void showCourseGuide(final List<CourseGuide> courseGuides, final Context context) {
        mHandler.post(new Runnable() { //执行在主线程中
            @Override
            public void run() {
                try {
                    Thread.sleep(1000); // 让主线程等待1s，以便获取相关数据
                    // Log.d(TAG, "courseGuides size = " + courseGuides.size());
                    courseRecyclerView = (RecyclerView) findViewById(R.id.show_course_rv);
                    LinearLayoutManager manager = new LinearLayoutManager(context);
                    manager.setOrientation(LinearLayoutManager.VERTICAL);
                    courseRecyclerView.setLayoutManager(manager);
                    ShowCourseAdapter adapter = new ShowCourseAdapter(courseGuides);
                    courseRecyclerView.setAdapter(adapter);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
