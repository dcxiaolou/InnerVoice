package com.android.dcxiaolou.innervoice.util;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;

import com.zyao89.view.zloading.ZLoadingDialog;

import static com.zyao89.view.zloading.Z_TYPE.LEAF_ROTATE;

/*
* 加载过程显示动画
* */
public class LoadingAnimation {

    private Context mContext;

    public ZLoadingDialog Create(Context mContext) {
        this.mContext = mContext;
        //加载动画
        ZLoadingDialog dialog = new ZLoadingDialog(mContext);
        dialog.setLoadingBuilder(LEAF_ROTATE)//设置类型
                .setLoadingColor(Color.GREEN)//颜色
                .setHintText("Loading...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.GRAY)  // 设置字体颜色
                .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
                .setDialogBackgroundColor(Color.parseColor("#CC111111")) // 设置背景色，默认白色
                .setCanceledOnTouchOutside(false);
        return dialog;
    }

}
