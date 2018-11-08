package com.android.dcxiaolou.innervoice;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.dcxiaolou.innervoice.mode.ReadArticleResult;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;

/*
* 阅读模块文章及评论的详细内容展示
* */

public class ShowArticleAndCommonActivity extends AppCompatActivity {

    private final static String TAG = "ShowArticleAndCommonActivity";
    public final static String ARTICLE_DETAIL = "article_detail";

    private Handler mHandler = new Handler();
    private Context mContext;

    private ReadArticleResult articleResult;

    private TextView titleTv, pushTimeTv, readNumTv, likeNumTv, commonNumTv;

    private String title, pushTime, readNum, likeNum, context;

    private LinearLayout articleTagLayout;
    private List<String> tags = new ArrayList<>();

    private HtmlTextView articleContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_article_and_common);

        mContext = getBaseContext();

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
        articleTagLayout = (LinearLayout) findViewById(R.id.article_tag);
        articleContext = (HtmlTextView) findViewById(R.id.article_context);

    }

    private void initData() {
        articleResult = (ReadArticleResult) getIntent().getSerializableExtra(ARTICLE_DETAIL);
        //Log.d(TAG, "title = " + articleResult.getTitle());
        title = articleResult.getTitle();
        tags = articleResult.getTag();
        pushTime = articleResult.getPush_time();
        readNum = articleResult.getCount();
        likeNum = articleResult.getLike().get(0);
        context = articleResult.getArticle_detail().get(0);

        titleTv.setText(title);

        //通过代码动态的向LinearLayout中添加TextView
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(20, 10, 10, 0);
        TextView tagTv;
        Drawable drawable = getResources().getDrawable(R.drawable.article_tag_bg);
        for (String tag : tags) {
            tagTv = new TextView(mContext);
            tagTv.setText(tag);
            tagTv.setPadding(10, 3, 10, 5);
            tagTv.setBackground(drawable);
            tagTv.setLayoutParams(layoutParams);
            articleTagLayout.addView(tagTv);
        }

        pushTimeTv.setText(pushTime);
        readNumTv.setText(readNum);
        likeNumTv.setText(likeNum);
        articleContext.setHtml(context, new HtmlHttpImageGetter(articleContext));
    }

}
