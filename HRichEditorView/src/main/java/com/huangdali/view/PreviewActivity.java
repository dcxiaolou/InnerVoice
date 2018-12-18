package com.huangdali.view;
/*
* 预览
* */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.hdl.hricheditorview.R;
import com.huangdali.base.EditorResultBean;
import com.huangdali.bean.EContent;

import java.util.List;

public class PreviewActivity extends AppCompatActivity {

    private WebView previewWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        previewWebview = (WebView) findViewById(R.id.webview_preview);

        Intent intent = getIntent();
        //拿到编辑内容对象
        EditorResultBean resultBean = (EditorResultBean) intent.getSerializableExtra("contents");
        String articleTitle = intent.getStringExtra("articleTitle");
        String bgUri = intent.getStringExtra("bgUri");
        if (articleTitle != null) {
            articleTitle = "<h1 style='text-align: center;'>" + articleTitle + "</h1>";
        }
        if (bgUri != null) {
            bgUri = "<img style='width:100%;display: inline-block;' src=" + bgUri + " /><br/>";
        }
        String htmlBody = articleTitle + bgUri;
        List<EContent> contents = resultBean.getContents();
        for (EContent content : contents) {
            htmlBody += content.getHtml();
        }
        Log.d("htmlBody", "最终编辑的结果：" + htmlBody);

        WebSettings webSettings = previewWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        previewWebview.loadData(htmlBody, "text/html", "utf-8");
    }
}
