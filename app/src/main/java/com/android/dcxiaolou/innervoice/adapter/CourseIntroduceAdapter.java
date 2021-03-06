package com.android.dcxiaolou.innervoice.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.dcxiaolou.innervoice.ShowCourseIntroduceActivity;
import com.android.dcxiaolou.innervoice.R;
import com.android.dcxiaolou.innervoice.mode.CourseGuide;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/*
* 课程推荐模块的适配器
* */

public class CourseIntroduceAdapter extends RecyclerView.Adapter<CourseIntroduceAdapter.ViewHolder> {

    private List<CourseGuide> courseGuides = new ArrayList<>();

    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView courseItemCardView;
        ImageView courseCover;
        TextView courseText, courseJoinNum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseItemCardView = (CardView) itemView.findViewById(R.id.course_item_card_view);
            courseCover = (ImageView) itemView.findViewById(R.id.course_cover);
            courseText = (TextView) itemView.findViewById(R.id.course_title);
            courseJoinNum = (TextView) itemView.findViewById(R.id.course_join_num);

        }
    }

    public CourseIntroduceAdapter(List<CourseGuide> courseGuides) {
        this.courseGuides = courseGuides;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_guide_item, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.courseItemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                CourseGuide courseGuide = courseGuides.get(position);
                Intent intent = new Intent(viewGroup.getContext(), ShowCourseIntroduceActivity.class);
                intent.putExtra(ShowCourseIntroduceActivity.COURSE_ID, courseGuide.getId());
                intent.putExtra(ShowCourseIntroduceActivity.COVER_PATH, courseGuide.getCover());
                intent.putExtra(ShowCourseIntroduceActivity.COURSE_TITLE, courseGuide.getTitle());
                viewGroup.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        CourseGuide courseGuide = courseGuides.get(i);
        Glide.with(viewHolder.itemView).load(courseGuide.getCover()).into(viewHolder.courseCover);
        viewHolder.courseText.setText(courseGuide.getTitle());
        viewHolder.courseJoinNum.setText(courseGuide.getJoinnum());
    }

    @Override
    public int getItemCount() {
        return this.courseGuides.size();
    }
}
