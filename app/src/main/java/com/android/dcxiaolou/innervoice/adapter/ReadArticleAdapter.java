package com.android.dcxiaolou.innervoice.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.dcxiaolou.innervoice.R;
import com.android.dcxiaolou.innervoice.ShowArticleAndCommonActivity;
import com.android.dcxiaolou.innervoice.mode.ReadArticleResult;
import com.bumptech.glide.Glide;

import java.util.List;

/*
* 每日精选模块、阅读模块文章内容的适配器（基于RecyclerView）
* */

public class ReadArticleAdapter extends RecyclerView.Adapter<ReadArticleAdapter.ViewHolder> {

    private List<ReadArticleResult> readArticleResults;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView dailyBestCv;
        ImageView dailyBestImage;
        TextView dailyBestTitle, dailyBestDescribe, dailyBestPushTime, dailyBestDigg, dailyBestView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dailyBestCv = itemView.findViewById(R.id.daily_best_cv);
            dailyBestImage = itemView.findViewById(R.id.daily_best_image);
            dailyBestTitle = itemView.findViewById(R.id.daily_best_title);
            dailyBestDescribe = itemView.findViewById(R.id.daily_best_describe);
            dailyBestPushTime = itemView.findViewById(R.id.daily_best_push_time);
            dailyBestDigg = itemView.findViewById(R.id.daily_best_digg);
            dailyBestView = itemView.findViewById(R.id.daily_best_view);

        }
    }

    public ReadArticleAdapter(List<ReadArticleResult> readArticleResults) {
        this.readArticleResults = readArticleResults;
    }

    @NonNull
    @Override
    public ReadArticleAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        if (mContext == null)
            this.mContext = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.show_article_introduce_item, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        //添加点击事件，用于跳转到文章的详情页
        holder.dailyBestCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                ReadArticleResult articleResult = readArticleResults.get(position);
                Intent intent = new Intent(viewGroup.getContext(), ShowArticleAndCommonActivity.class);
                //用Intent传递对象，该对象要实现Serializable（序列化）接口，将该对象转换成可存储或可传输的状态
                intent.putExtra(ShowArticleAndCommonActivity.ARTICLE_DETAIL, articleResult);
                viewGroup.getContext().startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReadArticleAdapter.ViewHolder viewHolder, int i) {
        ReadArticleResult articleResult = readArticleResults.get(i);
        Glide.with(mContext).load(articleResult.getImage()).into(viewHolder.dailyBestImage);
        viewHolder.dailyBestTitle.setText(articleResult.getTitle());
        viewHolder.dailyBestDescribe.setText(articleResult.getDescribe());
        viewHolder.dailyBestPushTime.setText(articleResult.getPush_time());
        viewHolder.dailyBestDigg.setText(articleResult.getLike().get(0));
        viewHolder.dailyBestView.setText(articleResult.getCount());
    }

    @Override
    public int getItemCount() {
        return readArticleResults.size();
    }
}
