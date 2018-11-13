package com.android.dcxiaolou.innervoice.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.dcxiaolou.innervoice.R;
import com.android.dcxiaolou.innervoice.ShowQuestionAndAnswerActivity;
import com.android.dcxiaolou.innervoice.mode.QuestionResult;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class ShowQuestionAdapter extends RecyclerView.Adapter<ShowQuestionAdapter.ViewHolder> {

    private Context mContext;

    private List<QuestionResult> results = new ArrayList<>();

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView questionImgIv;
        TextView questionTitleTv;
        LinearLayout questionTagLl;
        TextView questionContextTv, questionPushTimeTv, questionViewNumTv, questionAnswerNumTv;

        CardView questionCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            questionImgIv = (ImageView) itemView.findViewById(R.id.question_user_img);
            questionTitleTv = (TextView) itemView.findViewById(R.id.question_title);
            questionTagLl = (LinearLayout) itemView.findViewById(R.id.question_tag);
            questionContextTv = (TextView) itemView.findViewById(R.id.question_context);
            questionPushTimeTv = (TextView) itemView.findViewById(R.id.question_push_time);
            questionViewNumTv = (TextView) itemView.findViewById(R.id.question_view_num);
            questionAnswerNumTv = (TextView) itemView.findViewById(R.id.question_answer_num);

            questionCardView = (CardView) itemView.findViewById(R.id.question_item_card_view);

        }
    }

    public ShowQuestionAdapter(List<QuestionResult> results) {
        this.results = results;
    }

    @NonNull
    @Override
    public ShowQuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mContext == null)
            mContext = viewGroup.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.show_question_item, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.questionCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                QuestionResult questionResult = results.get(position);
                Intent intent = new Intent(mContext, ShowQuestionAndAnswerActivity.class);
                intent.putExtra(ShowQuestionAndAnswerActivity.QUESTION_POSITION, position);
                intent.putExtra(ShowQuestionAndAnswerActivity.QUESTION, questionResult);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //清除处于所要到达Activity之间的Activity
                mContext.startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowQuestionAdapter.ViewHolder viewHolder, int i) {
        QuestionResult result = results.get(i);
        if (result == null) {
            Log.d("TAG", "QuestionResult is null");
        }
        String imgUrl = result.getQuestion_user_img();
        if (imgUrl == null)
            imgUrl = "https://avatar.csdn.net/A/A/A/2_dc_2701.jpg";
        RequestOptions options = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE) //不做磁盘缓存
                .skipMemoryCache(true) //不做内存缓存
                .placeholder(R.drawable.question_user_img); //占位图
        Glide.with(mContext).load(imgUrl).apply(options).into(viewHolder.questionImgIv);
        viewHolder.questionTitleTv.setText(result.getTitle());
        List<String> tags = result.getQuestion_tag();
        //通过代码动态的向LinearLayout中添加TextView
        viewHolder.questionTagLl.removeAllViews(); //防止在RecyclerView中重复加载
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(10, 10, 10, 0);
        TextView tagTv;
        Drawable drawable = mContext.getResources().getDrawable(R.drawable.article_tag_bg);
        int tagSize = tags.size();
        if (tagSize != 0) {
            for (int j = 0; j < tagSize; j++) {
                if (j >= 3) break;
                tagTv = new TextView(mContext);
                tagTv.setText(tags.get(j));
                tagTv.setPadding(10, 3, 10, 5);
                tagTv.setBackground(drawable);
                tagTv.setLayoutParams(layoutParams);
                viewHolder.questionTagLl.addView(tagTv);
            }
        }
        viewHolder.questionContextTv.setText(result.getQuestion_content().trim());
        viewHolder.questionPushTimeTv.setText(result.getQuestion_push_time());
        viewHolder.questionViewNumTv.setText(result.getQuestion_reader_num());
        viewHolder.questionAnswerNumTv.setText(result.getQuestion_answer_num());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
