package com.android.dcxiaolou.innervoice.adapter;

/*
* 主页每日精选模块的适配器
* */

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.dcxiaolou.innervoice.R;
import com.android.dcxiaolou.innervoice.mode.ReadArticleResult;
import com.bumptech.glide.Glide;

import java.util.List;

public class DailyBestAdapter extends RecyclerView.Adapter<DailyBestAdapter.ViewHolder> {

    private List<ReadArticleResult> readArticleResults;

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView dailyBestImage;
        TextView dailyBestTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dailyBestImage = (ImageView) itemView.findViewById(R.id.daily_best_img);
            dailyBestTitle = (TextView) itemView.findViewById(R.id.daily_best_title);
        }
    }

    public DailyBestAdapter(List<ReadArticleResult> readArticleResults) {
        this.readArticleResults = readArticleResults;
    }

    @NonNull
    @Override
    public DailyBestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.daily_best_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DailyBestAdapter.ViewHolder viewHolder, int i) {
        ReadArticleResult readArticleResult = readArticleResults.get(i);
        Glide.with(viewHolder.itemView).load(readArticleResult.getImage()).into(viewHolder.dailyBestImage);
        viewHolder.dailyBestTitle.setText(readArticleResult.getTitle());
        Log.d("Tag", readArticleResult.getImage() + readArticleResult.getTitle());
    }

    @Override
    public int getItemCount() {
        return readArticleResults.size();
    }
}
