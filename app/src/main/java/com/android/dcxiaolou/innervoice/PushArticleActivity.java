package com.android.dcxiaolou.innervoice;
/*
 * 发布文章
 * */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.android.dcxiaolou.innervoice.util.ImageUtil;
import com.android.dcxiaolou.innervoice.util.ReWebChomeClient;
import com.android.dcxiaolou.innervoice.util.ReWebViewClient;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;

public class PushArticleActivity extends AppCompatActivity implements ReWebChomeClient.OpenFileChooserCallBack {

    private static final String TAG = "PushArticleActivity";
    private static final int REQUEST_CODE_PICK_IMAGE = 0;
    private static final int REQUEST_CODE_IMAGE_CAPTURE = 1;
    private WebView mWebView;
    private Intent mSourceIntent;
    private ValueCallback<Uri> mUploadMsg;
    private ValueCallback<Uri[]> mUploadMsg5Plus;

    private List<String> selectImgPath = new ArrayList<>();
    private List<String> bmobImgPath = new ArrayList<>();

    private ImageView pushArticleBackIv, pushArticleIv;

    private String path = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_article);

        pushArticleBackIv = (ImageView) findViewById(R.id.iv_push_article_back);
        pushArticleIv = (ImageView) findViewById(R.id.iv_push_article);

        mWebView = (WebView) findViewById(R.id.web_view_push_article);


        mWebView.setWebChromeClient(new ReWebChomeClient(this));
        mWebView.setWebViewClient(new ReWebViewClient());
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        fixDirPath();
        //这里加载自己部署的（也可加载本地资源）
        mWebView.loadUrl("file:///android_asset/Eleditor-master/demo/index.html");

        pushArticleIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 通过Handler发送消息
                mWebView.post(new Runnable() {
                    @Override
                    public void run() {

                        mWebView.evaluateJavascript("javascript:complete()", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue( String value) {

                                for (String img : selectImgPath) {
                                    Log.d(TAG, "onReceiveValue: " + img);
                                    final BmobFile imgFile = new BmobFile(new File(img));
                                    if (imgFile != null) {
                                        imgFile.upload(new UploadFileListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                String bmobImgUrl = imgFile.getFileUrl();
                                                Log.d(TAG, "done: " + bmobImgUrl);
                                                bmobImgPath.add(bmobImgUrl);
                                            }
                                        });
                                    }
                                }

                                final String htmlValue = value;

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(2000);

                                            String changeUnicodeHtmlValue = htmlValue.replaceAll("u003C", "<");
                                            String changeBrValue = changeUnicodeHtmlValue.replaceAll("\\\\", "");
                                            //Log.d(TAG, "onReceiveValue: " + value);
                                            int len = changeBrValue.length();
                                            String newValue = changeBrValue.substring(1, len - 1);
                                            newValue = newValue.replaceAll("\"", "'");
                                            for (String s : bmobImgPath) {
                                                newValue = newValue.replace("#", s);
                                            }
                                            Log.d(TAG, "onReceiveValue: " + newValue);
                                            path = getApplicationContext().getFilesDir().getAbsolutePath() + "/article.html";
                                            Log.d(TAG, "onReceiveValue: " + path);
                                            try {
                                                File file = new File(path);
                                                if (!file.exists()) {
                                                    file.createNewFile();
                                                }
                                                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                                                BufferedWriter bw = new BufferedWriter(fw);
                                                bw.write(newValue);
                                                bw.close();
                                                Log.d(TAG, "onReceiveValue: " + "Done");
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                            final BmobFile bmobFile = new BmobFile(new File(path));
                                            if (bmobFile != null) {
                                                bmobFile.upload(new UploadFileListener() {
                                                    @Override
                                                    public void done(BmobException e) {
                                                        String url = bmobFile.getFileUrl();
                                                        Log.d(TAG, "Bmob = " + url);
                                                    }
                                                });
                                            }

                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();


                            }
                        });

                    }
                });

            }
        });

        pushArticleBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //结束当前，返回主页
                finish();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_IMAGE_CAPTURE:
            case REQUEST_CODE_PICK_IMAGE: {
                try {
                    if (mUploadMsg == null && mUploadMsg5Plus == null) {
                        return;
                    }
                    String sourcePath = ImageUtil.retrievePath(this, mSourceIntent, data);
                    Log.d(TAG, "sourcePath: " + sourcePath);//图片选择返回的路径
                    selectImgPath.add(sourcePath);
                    if (TextUtils.isEmpty(sourcePath) || !new File(sourcePath).exists()) {
                        Log.w(TAG, "sourcePath empty or not exists.");
                        break;
                    }
                    Uri uri = Uri.fromFile(new File(sourcePath));
                    //Log.d(TAG, "uri: " + uri);
                    if (mUploadMsg != null) {
                        mUploadMsg.onReceiveValue(uri);
                        mUploadMsg = null;
                    } else {
                        mUploadMsg5Plus.onReceiveValue(new Uri[]{uri});
                        mUploadMsg5Plus = null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    @Override
    public void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType) {
        mUploadMsg = uploadMsg;
        showOptions();
    }

    @Override
    public void showFileChooserCallBack(ValueCallback<Uri[]> filePathCallback) {
        mUploadMsg5Plus = filePathCallback;
        showOptions();
    }

    public void showOptions() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setOnCancelListener(new ReOnCancelListener());
        alertDialog.setTitle(R.string.options);
        alertDialog.setItems(R.array.options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    mSourceIntent = ImageUtil.choosePicture();
                    startActivityForResult(mSourceIntent, REQUEST_CODE_PICK_IMAGE);
                } else {
                    mSourceIntent = ImageUtil.takeBigPicture(PushArticleActivity.this);
                    startActivityForResult(mSourceIntent, REQUEST_CODE_IMAGE_CAPTURE);
                }
            }
        });
        alertDialog.show();
    }

    private void fixDirPath() {
        String path = ImageUtil.getDirPath();
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private class ReOnCancelListener implements DialogInterface.OnCancelListener {

        @Override
        public void onCancel(DialogInterface dialogInterface) {
            if (mUploadMsg != null) {
                mUploadMsg.onReceiveValue(null);
                mUploadMsg = null;
            }
            if (mUploadMsg5Plus != null) {
                mUploadMsg5Plus.onReceiveValue(null);
                mUploadMsg5Plus = null;
            }
        }
    }

}
