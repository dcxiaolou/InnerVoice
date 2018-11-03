package com.android.dcxiaolou.innervoice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.dcxiaolou.innervoice.R;
import com.android.dcxiaolou.innervoice.mode.ReadArticleResult;
import com.bumptech.glide.Glide;

import java.util.List;

public class DailyBestAdapter extends BaseAdapter {

    private List<ReadArticleResult> readArticleResults;
    private Context mContext;

    public DailyBestAdapter(List<ReadArticleResult> readArticleResults, Context mContext) {
        this.readArticleResults = readArticleResults;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return readArticleResults.size();
    }

    @Override
    public Object getItem(int position) {
        return readArticleResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // getView()方法每次都将布局重新加载一遍
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) { // 如果convertView为null，则重新加载布局 可以提高ListView的运行效率
            convertView = View.inflate(mContext, R.layout.daily_best_item, null);
            viewHolder = new ViewHolder();
            viewHolder.dailyBestImage = convertView.findViewById(R.id.daily_best_image);
            viewHolder.dailyBestTitle = convertView.findViewById(R.id.daily_best_title);
            viewHolder.dailyBestDescribe = convertView.findViewById(R.id.daily_best_describe);
            viewHolder.dailyBestPushTime = convertView.findViewById(R.id.daily_best_push_time);
            viewHolder.dailyBestDigg = convertView.findViewById(R.id.daily_best_digg);
            viewHolder.dailyBestView = convertView.findViewById(R.id.daily_best_view);
            convertView.setTag(viewHolder); // 将viewHolder存储到convertView中
        } else { // 不为空，直接对convertView进行重用
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ReadArticleResult readArticleResult = readArticleResults.get(position);
        Glide.with(convertView).load(readArticleResult.getImage()).into(viewHolder.dailyBestImage);
        viewHolder.dailyBestTitle.setText(readArticleResult.getTitle());
        viewHolder.dailyBestDescribe.setText(readArticleResult.getDescribe());
        viewHolder.dailyBestPushTime.setText(readArticleResult.getPush_time());
        viewHolder.dailyBestDigg.setText(readArticleResult.getLike().get(0));
        viewHolder.dailyBestView.setText(readArticleResult.getCount());

        return convertView;
    }

    public static class ViewHolder {
        public ImageView dailyBestImage;
        public TextView dailyBestTitle, dailyBestDescribe, dailyBestPushTime, dailyBestDigg, dailyBestView;
    }
}
