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

import com.android.dcxiaolou.innervoice.R;
import com.android.dcxiaolou.innervoice.adapter.ShowQuestionAdapter;
import com.android.dcxiaolou.innervoice.mode.Question;
import com.android.dcxiaolou.innervoice.mode.QuestionResult;
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

/*
* 问答功能界面的碎片，用来显示最热问答
* */

public class ShowQuestionHotFragment extends Fragment {

    private View mRootView;
    private Context mContext;
    private Handler mHandler = new Handler();

    private RecyclerView recyclerView;

    private List<QuestionResult> questionResults = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_question_hot, container, false);
        mContext = getContext();
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getQuestionFromBmob();
    }

    private void getQuestionFromBmob() {

        BmobQuery<Question> query = new BmobQuery<>();
        query.addQueryKeys("question");
        query.findObjects(new FindListener<Question>() {
            @Override
            public void done(List<Question> list, BmobException e) {
                if (e == null) {
                    int count = list.size();
                    Log.d("TAG", "question count = " + count);
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

                } else {
                    e.printStackTrace();
                }
            }
        });

    }

    private void showQuestions() {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    List<QuestionResult> hotQuestionResult = new ArrayList<>();
                    for (int i = 29; i < questionResults.size(); i++)
                        hotQuestionResult.add(questionResults.get(i));
                    recyclerView = (RecyclerView) mRootView.findViewById(R.id.question_hot_rv);
                    LinearLayoutManager manager = new LinearLayoutManager(mContext);
                    recyclerView.setLayoutManager(manager);
                    ShowQuestionAdapter adapter = new ShowQuestionAdapter(hotQuestionResult);
                    recyclerView.setAdapter(adapter);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
