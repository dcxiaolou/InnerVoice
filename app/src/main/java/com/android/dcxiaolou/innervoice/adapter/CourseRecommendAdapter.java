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

import com.android.dcxiaolou.innervoice.ShowCourseIntroduce;
import com.android.dcxiaolou.innervoice.R;
import com.android.dcxiaolou.innervoice.mode.CourseGuide;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CourseRecommendAdapter extends RecyclerView.Adapter<CourseRecommendAdapter.ViewHolder> {

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

    public CourseRecommendAdapter(List<CourseGuide> courseGuides) {
        this.courseGuides = courseGuides;
        this.courseGuides = courseGuides;
    }

    @NonNull
    @Override
    public CourseRecommendAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_guide_item, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.courseItemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                CourseGuide courseGuide = courseGuides.get(position);
                Intent intent = new Intent(viewGroup.getContext(), ShowCourseIntroduce.class);
                intent.putExtra(ShowCourseIntroduce.COURCE_ID, courseGuide.getId());
                viewGroup.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseRecommendAdapter.ViewHolder viewHolder, int i) {
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
