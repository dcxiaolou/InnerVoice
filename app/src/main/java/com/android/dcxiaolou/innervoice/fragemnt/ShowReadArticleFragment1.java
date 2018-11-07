package com.android.dcxiaolou.innervoice.fragemnt;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.dcxiaolou.innervoice.R;
import com.android.dcxiaolou.innervoice.mode.ReadArticle;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/*
* 阅读模块详情页的心理科普碎片 -- 792
* */

public class ShowReadArticleFragment1 extends Fragment {

    private final static  String TAG = "Fragment1";

    private View rootView;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (View) inflater.inflate(R.layout.fragment_show_article_1, container, false);
        mContext = getContext();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        BmobQuery<ReadArticle> query = new BmobQuery<>();
        query.addWhereEqualTo("type", "792");
        query.findObjects(new FindListener<ReadArticle>() {
            @Override
            public void done(List<ReadArticle> list, BmobException e) {
                if (e == null) {
                    int count = list.size();
                    Log.d(TAG, "count = " + count);
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
