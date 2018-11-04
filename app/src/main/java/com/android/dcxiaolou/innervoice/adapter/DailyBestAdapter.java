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

    /*getView()方法每次都将布局重新加载一遍
    * 优化：第一个是优化加载布局view，第二个是优化加载控件viewHolder。
    * */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) { // 如果convertView为null，则重新加载布局 可以提高ListView的运行效率
            view = View.inflate(mContext, R.layout.daily_best_item, null);
            viewHolder = new ViewHolder();
            viewHolder.dailyBestImage = view.findViewById(R.id.daily_best_image);
            viewHolder.dailyBestTitle = view.findViewById(R.id.daily_best_title);
            viewHolder.dailyBestDescribe = view.findViewById(R.id.daily_best_describe);
            viewHolder.dailyBestPushTime = view.findViewById(R.id.daily_best_push_time);
            viewHolder.dailyBestDigg = view.findViewById(R.id.daily_best_digg);
            viewHolder.dailyBestView = view.findViewById(R.id.daily_best_view);
            view.setTag(viewHolder); // 将viewHolder存储到convertView中
        } else { // 不为空，直接对convertView进行重用
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 从新获取viewHolder
        }

        ReadArticleResult readArticleResult = readArticleResults.get(position);
        Glide.with(view).load(readArticleResult.getImage()).into(viewHolder.dailyBestImage);
        viewHolder.dailyBestTitle.setText(readArticleResult.getTitle());
        viewHolder.dailyBestDescribe.setText(readArticleResult.getDescribe());
        viewHolder.dailyBestPushTime.setText(readArticleResult.getPush_time());
        viewHolder.dailyBestDigg.setText(readArticleResult.getLike().get(0));
        viewHolder.dailyBestView.setText(readArticleResult.getCount());

        return view;
    }

    public static class ViewHolder {
        ImageView dailyBestImage;
        TextView dailyBestTitle, dailyBestDescribe, dailyBestPushTime, dailyBestDigg, dailyBestView;
    }
}
