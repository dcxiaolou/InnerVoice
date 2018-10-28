package com.android.dcxiaolou.innervoice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.bmob.v3.Bmob;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化BmobSDK
        Bmob.initialize(this, "bb6b2f6c06b678c60419c29dd24ef1c4");

    }
}
