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
import com.android.dcxiaolou.innervoice.adapter.ReadArticleAdapter;
import com.android.dcxiaolou.innervoice.mode.ReadArticle;
import com.android.dcxiaolou.innervoice.mode.ReadArticleResult;
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
* 阅读模块详情页的成长学习碎片 862
* */

public class ShowReadArticleFragment6 extends Fragment {

    private final static  String TAG = "Fragment6";

    private View rootView;
    private Context mContext;
    private Handler mHandler = new Handler();

    private List<ReadArticleResult> readArticleResults = new ArrayList<>();

    private RecyclerView showArticleItemRv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (View) inflater.inflate(R.layout.fragment_show_article_6, container, false);
        mContext = getContext();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //从bmob获取ArticleResult
        getArticleResultFromBmob();

        //将文章的简介显示出来（RecyclerView）
        showArticleItem();
    }

    private void getArticleResultFromBmob() {
        BmobQuery<ReadArticle> query = new BmobQuery<>();
        query.addQueryKeys("article");
        query.addWhereEqualTo("type", "862");
        query.findObjects(new FindListener<ReadArticle>() {
            @Override
            public void done(List<ReadArticle> list, BmobException e) {
                if (e == null) {
                    BmobFile file;
                    String fileUrl;
                    for (final ReadArticle article : list) {
                        file = article.getBmobFile();
                        //Log.d(TAG, file.getFilename());
                        fileUrl = file.getUrl();
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
                                //Log.d(TAG, result);
                                Gson gsno = new Gson();
                                ReadArticleResult articleResult = gsno.fromJson(result, ReadArticleResult.class);
                                readArticleResults.add(articleResult);
                            }
                        });
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showArticleItem() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000); //停顿1s，以便获取数据
                    showArticleItemRv = (RecyclerView) rootView.findViewById(R.id.show_article_item_rv);
                    LinearLayoutManager manager = new LinearLayoutManager(mContext);
                    showArticleItemRv.setLayoutManager(manager);
                    ReadArticleAdapter adapter = new ReadArticleAdapter(readArticleResults);
                    showArticleItemRv.setAdapter(adapter);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
