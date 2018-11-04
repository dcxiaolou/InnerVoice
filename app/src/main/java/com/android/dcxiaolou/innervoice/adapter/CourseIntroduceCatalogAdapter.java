package com.android.dcxiaolou.innervoice.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.dcxiaolou.innervoice.R;
import com.android.dcxiaolou.innervoice.mode.CourseIntroduceCatalog;

import java.util.List;

public class CourseIntroduceCatalogAdapter extends RecyclerView.Adapter<CourseIntroduceCatalogAdapter.ViewHolder> {

    private List<CourseIntroduceCatalog> courseIntroduceCatalogs;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView courseTitle, courseTime, courseIndex;
        RadioButton coursePlay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_introduce_catalog_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
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
