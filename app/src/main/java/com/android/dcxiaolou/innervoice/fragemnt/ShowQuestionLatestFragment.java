package com.android.dcxiaolou.innervoice.fragemnt;

import android.content.Context;
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
import android.widget.Toast;

import com.android.dcxiaolou.innervoice.R;
import com.android.dcxiaolou.innervoice.adapter.ShowQuestionAdapter;
import com.android.dcxiaolou.innervoice.mode.Question;
import com.android.dcxiaolou.innervoice.mode.QuestionResult;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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
* 问答功能界面的碎片，用来显示最新问答
* */

public class ShowQuestionLatestFragment extends Fragment {

    private View mRootView;
    private Context mContext;
    private Handler mHandler = new Handler();

    private SmartRefreshLayout refreshLayout;

    private RecyclerView recyclerView;

    private List<QuestionResult> questionResults = new ArrayList<>();

    private ShowQuestionAdapter adapter;

    //标记是否是第一次加载
    private boolean isFiratLoad = true;
    //bmob数据获取分页
    private int skipNum = 0;
    //标记是否还有数据数据可以从bmob获取
    private boolean haveMoreData = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_question_latest, container, false);
        mContext = getContext();
        return mRootView;
    }

    /*
     * 解决ViewPager + Fragment 页面滑动数据丢失，此处采用从新加载数据的方法
     * 注意setUserVisibleHintff先于onActivityCreate方法执行
     * */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        isFiratLoad = true;
        skipNum = 0;
        haveMoreData = true;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        refreshLayout = (SmartRefreshLayout) mRootView.findViewById(R.id.question_latest_smart_refresh_layout);
        recyclerView = (RecyclerView) mRootView.findViewById(R.id.question_latest_rv);
        adapter = new ShowQuestionAdapter(questionResults);

        if (isFiratLoad) {
            refreshLayout.autoRefresh(); //第一次加载自动下拉刷新一次
            refreshLayout.finishRefresh(); //结束刷新
            isFiratLoad = false;
        }

        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if (haveMoreData) {
                    questionResults.clear();
                    getQuestionFromBmob();
                } else {
                    Toast.makeText(mContext, "φ(>ω<*) 暂无更新", Toast.LENGTH_SHORT).show();
                }
                refreshLayout.finishRefresh();
            }
        });
        //上拉加载更多
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (haveMoreData) {
                    getQuestionFromBmob();
                } else {
                    Toast.makeText(mContext, "φ(>ω<*) 没有更多文章了", Toast.LENGTH_SHORT).show();
                }
                refreshLayout.finishLoadMore();
            }
        });

    }

    private void getQuestionFromBmob() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(0);

                    BmobQuery<Question> query = new BmobQuery<>();
                    query.addQueryKeys("question");
                    query.setLimit(10); //每次查询10条数据
                    query.setSkip(skipNum); //分页
                    query.findObjects(new FindListener<Question>() {
                        @Override
                        public void done(List<Question> list, BmobException e) {
                            if (e == null) {
                                int count = list.size();
                                Log.d("TAG", "question count = " + count);
                                if (count < 10) {
                                    haveMoreData = false;
                                }
                                BmobFile file;
                                String fileUrl;
                                for (int i = 0; i < count; i++) {
                                    file = list.get(i).getQuestion();
                                    fileUrl = file.getFileUrl();
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
                                            Gson gson = new Gson();
                                            QuestionResult questionResult = gson.fromJson(result, QuestionResult.class);
                                            questionResults.add(questionResult);
                                        }
                                    });
                                }

                                //展示
                                showQuestions();

                                skipNum += 10;

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

    private void showQuestions() {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    List<QuestionResult> latestQuestionResult = new ArrayList<>();
                    for (int i = 0; i < questionResults.size(); i += 2)
                        latestQuestionResult.add(questionResults.get(i));
                    LinearLayoutManager manager = new LinearLayoutManager(mContext);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged(); //数据更新
                    recyclerView.scrollToPosition(questionResults.size() - 10 - 1); //从新定位
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
