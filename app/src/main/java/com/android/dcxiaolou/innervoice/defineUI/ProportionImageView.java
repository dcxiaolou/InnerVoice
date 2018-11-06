package com.android.dcxiaolou.innervoice.defineUI;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.android.dcxiaolou.innervoice.R;

/*
* 自定义ImageView用来解决图片显示变形或不全，根据图片的宽高比显示图片
* */

public class ProportionImageView extends ImageView {

    private final static String TAG = "ProportionImageView";

    private float mWidthProportion, mHeightProportion;

    public ProportionImageView(Context context) { // 直接在代码里new的时候调用
        this(context, null);
    }

    public ProportionImageView(Context context, @Nullable AttributeSet attrs) { // 在布局文件layout里声明的时候会调用
        this(context, attrs, 0);
    }

    public ProportionImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) { //布局文件里 定义了style时调用
        super(context, attrs, defStyleAttr);
        // 初始化
        initAttribute(context, attrs);
    }

    private void initAttribute(Context context, AttributeSet attrs) {
        // 获取属性的数组
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ProportionImageView);
        // 获取单个属性值
        mWidthProportion = array.getFloat(R.styleable.ProportionImageView_widthProportion, 0);
        mHeightProportion = array.getFloat(R.styleable.ProportionImageView_heightProportion, 0);

        Log.d(TAG, mWidthProportion + "   " + mHeightProportion);

        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 把之前的图片改变高度显示
        // 获取宽度
        int width = MeasureSpec.getSize(widthMeasureSpec);
        // 计算高度
        int height = (int) (width * mHeightProportion / mWidthProportion);
        // 设置控件的宽高
        setMeasuredDimension(width, height);
    }
}
