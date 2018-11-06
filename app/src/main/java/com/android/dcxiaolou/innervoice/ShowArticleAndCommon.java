package com.android.dcxiaolou.innervoice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.dcxiaolou.innervoice.mode.ReadArticleResult;

public class ShowArticleAndCommon extends AppCompatActivity {

    private final static String TAG = "ShowArticleAndCommon";
    public final static String ARTICLE_DETAIL = "article_detail";

    private ReadArticleResult articleResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_article_and_common);

        articleResult = (ReadArticleResult) getIntent().getSerializableExtra(ARTICLE_DETAIL);
        Log.d(TAG, "title = " + articleResult.getTitle());
    }
}
