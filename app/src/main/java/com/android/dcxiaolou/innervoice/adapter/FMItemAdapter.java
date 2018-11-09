package com.android.dcxiaolou.innervoice.adapter;

/*
* FM模块推荐部分的适配器
* */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.dcxiaolou.innervoice.R;
import com.android.dcxiaolou.innervoice.defineUI.ProportionImageView;
import com.android.dcxiaolou.innervoice.mode.FMResult;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class FMItemAdapter extends RecyclerView.Adapter<FMItemAdapter.ViewHolder> {

    private Context mContext;

    private List<FMResult> fmResults = new ArrayList<>();

    static class ViewHolder extends RecyclerView.ViewHolder {

        ProportionImageView imageView;
        TextView titleTv, speakTv, viewTv, likeTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ProportionImageView) itemView.findViewById(R.id.fm_item_image);
            titleTv = (TextView) itemView.findViewById(R.id.fm_item_title);
            speakTv = (TextView) itemView.findViewById(R.id.fm_item_speak);
            viewTv = (TextView) itemView.findViewById(R.id.fm_item_view);
            likeTv = (TextView) itemView.findViewById(R.id.fm_item_like);

        }
    }

    public FMItemAdapter(List<FMResult> fmResults) {
        this.fmResults = fmResults;
    }

    @NonNull
    @Override
    public FMItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mContext == null) {
            mContext = viewGroup.getContext();
        }
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fm_menu_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FMItemAdapter.ViewHolder viewHolder, int i) {
        FMResult fmResult = fmResults.get(i);
        Glide.with(mContext).load(fmResult.getData().getCover()).into(viewHolder.imageView);
        viewHolder.titleTv.setText(fmResult.getData().getTitle());
        viewHolder.speakTv.setText(fmResult.getData().getSpeak());
        viewHolder.viewTv.setText(fmResult.getData().getViewnum());
        viewHolder.likeTv.setText(fmResult.getData().getFavnum());
    }

    @Override
    public int getItemCount() {
        return fmResults.size();
    }
}
