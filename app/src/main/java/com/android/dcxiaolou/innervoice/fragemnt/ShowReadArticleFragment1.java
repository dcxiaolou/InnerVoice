package com.android.dcxiaolou.innervoice.fragemnt;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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
import com.android.dcxiaolou.innervoice.adapter.ReadArticleAdapter;
import com.android.dcxiaolou.innervoice.mode.ReadArticle;
import com.android.dcxiaolou.innervoice.mode.ReadArticleResult;
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
 * 阅读模块详情页的心理科普碎片 -- 792
 * */

public class ShowReadArticleFragment1 extends Fragment {

    private final static String TAG = "Fragment1";

    //标记是否是第一次加载
    private boolean isFirstLoad = true;
    //bmob数据查询分页
    private int skipNum = 0;
    //标记是否还有数据可以加载
    private boolean haveMoreData = true;

    private View rootView;
    private Context mContext;
    private Handler mHandler = new Handler();

    private List<ReadArticleResult> readArticleResults = new ArrayList<>();

    private SmartRefreshLayout smartRefreshLayout;

    private RecyclerView showArticleItemRv;

    private ReadArticleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (View) inflater.inflate(R.layout.fragment_show_article_1, container, false);
        mContext = getContext();
        return rootView;
    }

    /*
    *解决ViewPager + fragment 页面切换后数据丢失，此处是让数据在相应的页面中从新加载
    * 注意：setUserVisibleHint方法先与onActivityCreate方法执行
    */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isFirstLoad = true;
        skipNum = 0;
        haveMoreData = true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        showArticleItemRv = (RecyclerView) rootView.findViewById(R.id.show_article_item_rv_1);
        smartRefreshLayout = (SmartRefreshLayout) rootView.findViewById(R.id.fragment_show_article_1_smart_refresh_layout);
        adapter = new ReadArticleAdapter(readArticleResults);

        if (isFirstLoad) {
            smartRefreshLayout.autoRefresh(); //首次加载自动刷新
            smartRefreshLayout.finishRefresh(); //结束刷新
            isFirstLoad = false;
        }

        //下拉刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if (haveMoreData) {
                    readArticleResults.clear();
                    //从bmob获取ArticleResult
                    getArticleResultFromBmob();
                } else {
                    Toast.makeText(mContext, "φ(>ω<*) 暂无更新", Toast.LENGTH_SHORT).show();
                }
                smartRefreshLayout.finishRefresh();
            }
        });
        //上拉加载更多
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (haveMoreData) {
                    getArticleResultFromBmob();
                } else {
                    Toast.makeText(mContext, "φ(>ω<*) 没有更多文章了", Toast.LENGTH_SHORT).show();
                }
                smartRefreshLayout.finishLoadMore();
            }
        });
    }

    private void getArticleResultFromBmob() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(0);

                    BmobQuery<ReadArticle> query = new BmobQuery<>();
                    query.addQueryKeys("article");
                    query.addWhereEqualTo("type", "792");
                    query.setLimit(5);
                    query.setSkip(skipNum);
                    query.findObjects(new FindListener<ReadArticle>() {
                        @Override
                        public void done(List<ReadArticle> list, BmobException e) {
                            if (e == null) {
                                BmobFile file;
                                String fileUrl;
                                Log.d("TAG", "fragment1 list.size() = " + list.size());
                                if (list.size() < 5) {
                                    haveMoreData = false;
                                }
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

                                //将文章的简介显示出来（RecyclerView）
                                showArticleItem();
                                skipNum += 5;

                                Log.d("TAG", "skipNum = " + skipNum);

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

    private void showArticleItem() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500); //停顿，以便获取数据
                    Log.d("TAG", "fragment1 readArticleResults.size() = " + readArticleResults.size());
                    LinearLayoutManager manager = new LinearLayoutManager(mContext);
                    showArticleItemRv.setLayoutManager(manager);
                    showArticleItemRv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();//数据发生改变
                    showArticleItemRv.scrollToPosition(readArticleResults.size() - 5 - 1);//刷新后从新定位
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
