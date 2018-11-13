package com.android.dcxiaolou.innervoice.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.dcxiaolou.innervoice.R;
import com.android.dcxiaolou.innervoice.mode.AnswerResult;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class ShowQuestionAndAnswerAdapter extends RecyclerView.Adapter<ShowQuestionAndAnswerAdapter.ViewHolder> {

    private Context mContext;

    private List<AnswerResult> answerResults = new ArrayList<>();

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView answerUserImg;
        TextView answerUserName;
        TextView answerPushTime;
        TextView answerLikeNum;
        TextView answerContext;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            answerUserImg = (ImageView) itemView.findViewById(R.id.answer_user_img);
            answerUserName = (TextView) itemView.findViewById(R.id.answer_user_name);
            answerPushTime = (TextView) itemView.findViewById(R.id.answer_push_time);
            answerLikeNum = (TextView) itemView.findViewById(R.id.answer_like_num);
            answerContext = (TextView) itemView.findViewById(R.id.answer_context);

        }
    }

    public ShowQuestionAndAnswerAdapter(List<AnswerResult> answerResults) {
        this.answerResults = answerResults;
    }

    @NonNull
    @Override
    public ShowQuestionAndAnswerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mContext == null)
            mContext = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.show_answer_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowQuestionAndAnswerAdapter.ViewHolder viewHolder, int i) {
        AnswerResult answerResult = answerResults.get(i);
        RequestOptions options = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE) //不做磁盘缓存
                .skipMemoryCache(true) //不做内存缓存
                .placeholder(R.drawable.question_user_img); //占位图
        Glide.with(mContext).load(answerResult.getAnswer_user_img()).apply(options).into(viewHolder.answerUserImg);
        viewHolder.answerUserName.setText(answerResult.getAnswer_user_name());
        viewHolder.answerPushTime.setText(answerResult.getAnswer_push_time());
        viewHolder.answerLikeNum.setText(answerResult.getAnswer_digg());
        viewHolder.answerContext.setText(answerResult.getAnswer_content().trim());
    }

    @Override
    public int getItemCount() {
        return answerResults.size();
    }
}
