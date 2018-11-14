package com.android.dcxiaolou.innervoice.fragemnt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.dcxiaolou.innervoice.LogInOrSignInActivity;
import com.android.dcxiaolou.innervoice.R;

import de.hdodenhof.circleimageview.CircleImageView;

/*
* 个人中心页
* */

public class CenterFragment extends Fragment implements View.OnClickListener {

    private boolean isLogin = false;

    private View mRootView;
    private Context mContext;

    private TextView logInOrSignIn;

    private RelativeLayout LagInRl;

    private SharedPreferences sp;

    private CircleImageView headImageCi;
    private TextView userNameTv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_center, null);
        mContext = getContext();
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        logInOrSignIn = (TextView) mRootView.findViewById(R.id.log_in_or_sign_in);

        logInOrSignIn.setOnClickListener(this);

        LagInRl = (RelativeLayout) mRootView.findViewById(R.id.log_in_rl);

        headImageCi = (CircleImageView) mRootView.findViewById(R.id.user_default_img);
        userNameTv = (TextView) mRootView.findViewById(R.id.log_out);

        sp = mContext.getSharedPreferences("info", Context.MODE_PRIVATE);
        isLogin = sp.getBoolean("isLogin", false);
        if (isLogin) {
            String userName = sp.getString("userName", null);
            String headImage = sp.getString("imagePath", null);
            Bitmap bitmap = BitmapFactory.decodeFile(headImage);
            headImageCi.setImageBitmap(bitmap);
            userNameTv.setText(userName);
            logInOrSignIn.setText("退出登录");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.log_in_or_sign_in:
                if (!isLogin) {
                    Intent loginIntent = new Intent(mContext, LogInOrSignInActivity.class);
                    mContext.startActivity(loginIntent);
                } else {
                    headImageCi.setImageResource(R.drawable.user_default_img);
                    userNameTv.setText("未登录");
                    logInOrSignIn.setText("点击登录/注册");
                    isLogin = false;
                    sp.edit().putBoolean("isLogin", false).apply();
                }
                break;
        }
    }
}
