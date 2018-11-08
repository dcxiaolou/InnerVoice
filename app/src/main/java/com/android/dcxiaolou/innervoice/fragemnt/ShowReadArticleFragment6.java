package com.android.dcxiaolou.innervoice.fragemnt;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.dcxiaolou.innervoice.R;

/*
* 阅读模块详情页的成长学习碎片
* */

public class ShowReadArticleFragment6 extends Fragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (View) inflater.inflate(R.layout.fragment_show_article_6, container, false);

        return rootView;
    }
}