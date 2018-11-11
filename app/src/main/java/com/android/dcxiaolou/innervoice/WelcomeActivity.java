package com.android.dcxiaolou.innervoice;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.dcxiaolou.innervoice.defineUI.CountDownProgressBar;

/*
* 欢迎页，便于主界面加载资源，避免出现黑屏
* */

//使用android:theme="@android:style/Theme.NoTitleBar.Fullscreen"（全屏）需要继承Activity
public class WelcomeActivity extends Activity {

    private ImageView mWelcomeIv;

    // 倒计时进度条
    private CountDownProgressBar cpb_countdown;
    //标记是否跳过欢迎页，解决跳过欢迎页后主页加载两次的bug
    private boolean isPass = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mWelcomeIv = (ImageView) findViewById(R.id.welcome_iv);

        cpb_countdown = (CountDownProgressBar) findViewById(R.id.cpb_countdown);

        cpb_countdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                isPass = true;
                //跳转到主界面后 结束当前活动
                finish();
            }
        });

        cpb_countdown.setDuration(3000, new CountDownProgressBar.OnFinishListener() {
            @Override
            public void onFinish() {

            }
        });

        //停顿5秒，便于主界面加载资源，避免出现黑屏
        //实现方法有Thread.sleep() handler 动画(AlphaAnimation ObjectAnimator)
        //mWelcomeIv 在哪里实现 alpha 动画效果透明 0.7f, 1.0f 透明度变化值
        ObjectAnimator animator = ObjectAnimator.ofFloat(mWelcomeIv, "alpha", 0.7f, 1.0f);
        animator.setDuration(3000); //动画执行时间
        animator.start();
        //给动画添加监听器 在动画结束的时候跳转到主界面
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!isPass) {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                //跳转到主界面后 结束当前活动
                finish();
            }
        });

    }

}
