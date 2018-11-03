package com.android.dcxiaolou.innervoice.defineUI;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/*
* 解决ListView在ScrollView显示不完全问题
* */

public class ImplantListView extends ListView {

    public ImplantListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 重写onMeasure()方法
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { // 测量计算
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
