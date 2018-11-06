package com.android.dcxiaolou.innervoice.defineUI;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/*
* 解决ListView在ScrollView显示不完全（解决滑动冲突）问题（自定义ListView）
* */

public class ImplantListView extends ListView {

    public ImplantListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 重写onMeasure()方法
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { // 测量计算
        /*生成一个符合MeasureSpec的一个32位的包含测量模式和测量高度的int值
        *makeMeasureSpec 输出的是10011111111111111111111111111111
        *最高两位是10的时候表示”最大模式”，即MeasureSpec.AT_MOST
         */
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
