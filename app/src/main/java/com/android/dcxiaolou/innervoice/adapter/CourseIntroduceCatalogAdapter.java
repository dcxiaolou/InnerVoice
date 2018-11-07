package com.android.dcxiaolou.innervoice.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.dcxiaolou.innervoice.CoursePlayActivity;
import com.android.dcxiaolou.innervoice.R;
import com.android.dcxiaolou.innervoice.mode.CourseIntroduceCatalog;

import java.util.List;

/*
* 课程简介模块目录的适配器
* */

public class CourseIntroduceCatalogAdapter extends RecyclerView.Adapter<CourseIntroduceCatalogAdapter.ViewHolder> {

    private final static String TAG = "CourseAdapter";

    private Context mContext;

    private List<CourseIntroduceCatalog> courseIntroduceCatalogs;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView catalogCardView;
        TextView courseTitle, courseTime, courseIndex;
        RadioButton coursePlay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catalogCardView = (CardView) itemView.findViewById(R.id.course_item_card_view);
            courseTitle = (TextView) itemView.findViewById(R.id.course_title);
            coursePlay = (RadioButton) itemView.findViewById(R.id.course_play);
            courseTime = (TextView) itemView.findViewById(R.id.course_time);
            courseIndex = (TextView) itemView.findViewById(R.id.course_index);
        }
    }

    public CourseIntroduceCatalogAdapter(List<CourseIntroduceCatalog> courseIntroduceCatalogs) {
        this.courseIntroduceCatalogs = courseIntroduceCatalogs;
    }

    @NonNull
    @Override
    public CourseIntroduceCatalogAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mContext == null)
            mContext = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_introduce_catalog_item, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.catalogCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                holder.coursePlay.setChecked(true);
                Log.d(TAG, "position = " + position);
                CourseIntroduceCatalog catalog = courseIntroduceCatalogs.get(position);
                Intent intent = new Intent(mContext, CoursePlayActivity.class);
                intent.putExtra(CoursePlayActivity.COURSE_NO, position); // 从0开始
                mContext.startActivity(intent);
            }
        });
        holder.coursePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                holder.coursePlay.setChecked(true);
                Log.d(TAG, "position = " + position);
                CourseIntroduceCatalog catalog = courseIntroduceCatalogs.get(position);
                Intent intent = new Intent(mContext, CoursePlayActivity.class);
                intent.putExtra(CoursePlayActivity.COURSE_NO, position); // 从0开始
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseIntroduceCatalogAdapter.ViewHolder viewHolder, int i) {
        CourseIntroduceCatalog courseIntroduceCatalog = courseIntroduceCatalogs.get(i);
        viewHolder.courseTitle.setText(courseIntroduceCatalog.getTitle());
        viewHolder.courseTime.setText(courseIntroduceCatalog.getTime());
        viewHolder.courseIndex.setText(courseIntroduceCatalog.getIndex());
    }

    @Override
    public int getItemCount() {
        return courseIntroduceCatalogs.size();
    }
}
