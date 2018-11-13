package com.android.dcxiaolou.innervoice;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.dcxiaolou.innervoice.adapter.ShowQuestionAndAnswerAdapter;
import com.android.dcxiaolou.innervoice.mode.Answer;
import com.android.dcxiaolou.innervoice.mode.AnswerResult;
import com.android.dcxiaolou.innervoice.mode.QuestionResult;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
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

public class ShowQuestionAndAnswerActivity extends AppCompatActivity {

    private final static String TAG = "ShowQAAActivity";
    public final static String QUESTION_POSITION = "question_position";
    public final static String QUESTION = "question";

    private Context mContext;
    private Handler mHandler = new Handler();

    private ImageView questionUserImage;
    private TextView questionTitle, questionPushTime, questionViewNum, questionAnswerNum;
    private LinearLayout questionTag;
    private TextView questionContext;

    private RecyclerView answerRecyclerView;

    private List<AnswerResult> answerResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_question_and_answer);

        mContext = getApplicationContext();

        //初始化界面
        initView();
        //初始化数据
        initData();

    }


    private void initView() {

        questionUserImage = (ImageView) findViewById(R.id.detail_question_user_img);
        questionTitle = (TextView) findViewById(R.id.detail_question_title);
        questionPushTime = (TextView) findViewById(R.id.detail_question_push_time);
        questionViewNum = (TextView) findViewById(R.id.detail_question_view_num);
        questionAnswerNum = (TextView) findViewById(R.id.detail_question_answer_num);
        questionTag = (LinearLayout) findViewById(R.id.detail_question_tag);
        questionContext = (TextView) findViewById(R.id.detail_question_context);

        answerRecyclerView = (RecyclerView) findViewById(R.id.detail_answer_recycler_view);

    }

    private void initData() {
        Intent intent = getIntent();
        final int position = intent.getIntExtra(QUESTION_POSITION, -1) + 1;
        QuestionResult questionResult = (QuestionResult) intent.getSerializableExtra(QUESTION);
        Log.d(TAG, "position = " + position);

        RequestOptions options = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE) //不做磁盘缓存
                .skipMemoryCache(true) //不做内存缓存
                .placeholder(R.drawable.question_user_img); //占位图
        if (questionResult == null) {
            Log.d("TAG", "QuestionResult is null");
        }
        String imgUrl = questionResult.getQuestion_user_img();
        if (imgUrl == null)
            imgUrl = "https://avatar.csdn.net/A/A/A/2_dc_2701.jpg";
        Glide.with(this).load(imgUrl).apply(options).into(questionUserImage);
        questionTitle.setText(questionResult.getTitle());
        List<String> tags = questionResult.getQuestion_tag();
        //通过代码动态的向LinearLayout中添加TextView
        questionTag.removeAllViews(); //防止在RecyclerView中重复加载
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(10, 10, 10, 0);
        TextView tagTv;
        Drawable drawable = getResources().getDrawable(R.drawable.article_tag_bg);
        int tagSize = tags.size();
        if (tagSize != 0) {
            for (int j = 0; j < tagSize; j++) {
                if (j >= 3) break;
                tagTv = new TextView(this);
                tagTv.setText(tags.get(j));
                tagTv.setPadding(10, 3, 10, 5);
                tagTv.setBackground(drawable);
                tagTv.setLayoutParams(layoutParams);
                questionTag.addView(tagTv);
            }
        }
        questionContext.setText(questionResult.getQuestion_content().trim());
        questionPushTime.setText(questionResult.getQuestion_push_time());
        questionViewNum.setText(questionResult.getQuestion_reader_num());
        questionAnswerNum.setText(questionResult.getQuestion_answer_num());

        getAnswerFromBmob(position);

    }

    private void getAnswerFromBmob(final int position) {

        if (position != 0 && position <= 50) {
            BmobQuery<Answer> query = new BmobQuery<>();
            query.addQueryKeys("answer");
            query.setLimit(300);
            query.findObjects(new FindListener<Answer>() {
                @Override
                public void done(List<Answer> list, BmobException e) {
                    if (e == null) {
                        int size = list.size();
                        List<String> answerUrl = new ArrayList<>();
                        String type;
                        String fileUrl;
                        BmobFile file;
                        for (int i = 0; i < size; i++) {
                            file = list.get(i).getAnswer();
                            type = file.getFilename().split("_")[0];
                            //Log.d(TAG, "type = " + type);
                            fileUrl = file.getFileUrl();
                            if (type.equals("" + position)) {
                                answerUrl.add(fileUrl);
                            }
                        }
                        //Log.d(TAG, "needAnswer size = " + answerUrl.size());
                        for (String url : answerUrl) {
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder().url(url).build();
                            client.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    String result = response.body().string();
                                    Gson gson = new Gson();
                                    AnswerResult answerResult = gson.fromJson(result, AnswerResult.class);
                                    answerResults.add(answerResult);
                                }
                            });
                        }

                        showAnswer();

                    } else {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    private void showAnswer() {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);

                    LinearLayoutManager manager = new LinearLayoutManager(mContext);
                    manager.setOrientation(LinearLayoutManager.VERTICAL);
                    answerRecyclerView.setLayoutManager(manager);
                    ShowQuestionAndAnswerAdapter adapter = new ShowQuestionAndAnswerAdapter(answerResults);
                    answerRecyclerView.setAdapter(adapter);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }


}
