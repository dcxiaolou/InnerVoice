package com.android.dcxiaolou.innervoice.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.dcxiaolou.innervoice.R;
import com.android.dcxiaolou.innervoice.mode.CourseIntroduceCommon;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class CourseIntroduceCommonAdapter extends RecyclerView.Adapter<CourseIntroduceCommonAdapter.ViewHolder> {

    private Context mContext;

    private List<CourseIntroduceCommon> commons;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView commonUserImage;
        RadioButton commonLiekRb;
        TextView commonUserName, commonPushTime, commonContent, commonReply;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            commonUserImage = (ImageView) itemView.findViewById(R.id.common_user_image);
            commonLiekRb = (RadioButton) itemView.findViewById(R.id.common_like);
            commonUserName = (TextView) itemView.findViewById(R.id.common_user_name);
            commonPushTime = (TextView) itemView.findViewById(R.id.common_push_time);
            commonContent = (TextView) itemView.findViewById(R.id.common_content);
            commonReply = (TextView) itemView.findViewById(R.id.reply);
        }
    }

    public CourseIntroduceCommonAdapter(List<CourseIntroduceCommon> commons) {
        this.commons = commons;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_introduce_common_item, viewGroup, false);
        mContext = view.getContext();
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        CourseIntroduceCommon common = commons.get(i);
        //使用glide将图片显示为圆形
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                .skipMemoryCache(true);//不做内存缓存
        Glide.with(mContext).load(common.getUserImagePath()).apply(mRequestOptions).into(viewHolder.commonUserImage);
        viewHolder.commonUserName.setText(common.getUserName());
        viewHolder.commonLiekRb.setText(common.getLikeNum());
        viewHolder.commonPushTime.setText(common.getPushTime());
        viewHolder.commonContent.setText(common.getContent());
        viewHolder.commonReply.setText("" + common.getReplayCommons().size());
    }

    @Override
    public int getItemCount() {
        return commons.size();
    }

}
