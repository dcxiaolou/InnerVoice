package com.android.dcxiaolou.innervoice;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

/*
* 欢迎页，便于主界面加载资源，避免出现黑屏
* */

//使用android:theme="@android:style/Theme.NoTitleBar.Fullscreen"（全屏）需要继承Activity
public class WelcomeActivity extends Activity {

    private ImageView mWelcomeIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mWelcomeIv = (ImageView) findViewById(R.id.welcome_iv);

        //停顿5秒，便于主界面加载资源，避免出现黑屏
        //实现方法有Thread.sleep() handler 动画(AlphaAnimation ObjectAnimator)
        //mWelcomeIv 在哪里实现 alpha 动画效果透明 0.7f, 1.0f 透明度变化值
        ObjectAnimator animator = ObjectAnimator.ofFloat(mWelcomeIv, "alpha", 0.7f, 1.0f);
        animator.setDuration(2000); //动画执行时间
        animator.start();
        //给动画添加监听器 在动画结束的时候跳转到主界面
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                //跳转到主界面后 结束当前活动
                finish();

            }
        });

    }

}
