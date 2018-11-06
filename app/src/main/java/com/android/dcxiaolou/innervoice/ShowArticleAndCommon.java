package com.android.dcxiaolou.innervoice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.dcxiaolou.innervoice.mode.ReadArticleResult;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

public class ShowArticleAndCommon extends AppCompatActivity {

    private final static String TAG = "ShowArticleAndCommon";
    public final static String ARTICLE_DETAIL = "article_detail";

    private ReadArticleResult articleResult;

    private TextView titleTv, pushTimeTv, readNumTv, likeNumTv, commonNumTv;

    private String title, pushTime, readNum, likeNum, commonNum, context;

    private HtmlTextView articleContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_article_and_common);

        //初始化界面
        initView();

        //初始化数据
        initData();
    }

    private void initView() {
        titleTv = (TextView) findViewById(R.id.article_title);
        pushTimeTv = (TextView) findViewById(R.id.article_push_time);
        readNumTv = (TextView) findViewById(R.id.article_read_num);
        likeNumTv = (TextView) findViewById(R.id.article_like_num);
        commonNumTv = (TextView) findViewById(R.id.article_common_num);

        articleContext = (HtmlTextView) findViewById(R.id.article_context);

    }

    private void initData() {
        articleResult = (ReadArticleResult) getIntent().getSerializableExtra(ARTICLE_DETAIL);
        //Log.d(TAG, "title = " + articleResult.getTitle());
        title = articleResult.getTitle();
        pushTime = articleResult.getPush_time();
        readNum = articleResult.getCount();
        likeNum = articleResult.getLike().get(0);
        context = articleResult.getArticle_detail().get(0);

        titleTv.setText(title);
        pushTimeTv.setText(pushTime);
        readNumTv.setText(readNum);
        likeNumTv.setText(likeNum);
        articleContext.setHtml(context, new HtmlHttpImageGetter(articleContext));
    }

}
