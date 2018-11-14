package com.android.dcxiaolou.innervoice.fragemnt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.dcxiaolou.innervoice.LoginOrSigninActivity;
import com.android.dcxiaolou.innervoice.R;

/*
* 个人中心页
* */

public class CenterFragment extends Fragment implements View.OnClickListener {



    private View mRootView;
    private Context mContext;

    private TextView logInOrSignIn;

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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.log_in_or_sign_in:
                Intent loginIntent = new Intent(mContext, LoginOrSigninActivity.class);
                mContext.startActivity(loginIntent);
                break;
        }
    }
}
