package com.android.dcxiaolou.innervoice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.dcxiaolou.innervoice.mode.User;

import java.util.List;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class LogInOrSignInActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,12}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    private Context mContext;

    private CircleImageView userImage;
    private EditText phoneNumberEt, passwordEt;
    private CheckBox rememberPassword;
    private TextView logIn, singIn;

    private boolean isRememberPassword = false;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_signin);

        mContext = getApplicationContext();

        userImage = (CircleImageView) findViewById(R.id.user_image);
        phoneNumberEt = (EditText) findViewById(R.id.phone_number);
        passwordEt = (EditText) findViewById(R.id.password);
        rememberPassword = (CheckBox) findViewById(R.id.remember_password);
        logIn = (TextView) findViewById(R.id.log_in_Tv);
        singIn = (TextView) findViewById(R.id.sing_in_tv);

        parseData();

        logIn.setOnClickListener(this);
        singIn.setOnClickListener(this);

    }

    private void parseData() {
        sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        isRememberPassword = sp.getBoolean("remember_password", false);
        String name = sp.getString("userName", null);
        Log.d("TAG", "isRememberPassword = " + isRememberPassword + " name = " + name);
        if (isRememberPassword) {
            String phoneNumber = sp.getString("phoneNumber", null);
            String password = sp.getString("password", null);
            phoneNumberEt.setText(phoneNumber);
            passwordEt.setText(password);
            rememberPassword.setChecked(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.log_in_Tv:
                final String phoneNumber = phoneNumberEt.getText().toString().trim();
                final String password = passwordEt.getText().toString().trim();
                Log.d("TAG", password);
                if (phoneNumber.equals("") || !Pattern.matches(REGEX_MOBILE, phoneNumber)) {
                    Toast.makeText(mContext, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                } else if (password.equals("") || !Pattern.matches(REGEX_PASSWORD, password)) {
                    Toast.makeText(mContext, "请输入正确的密码", Toast.LENGTH_SHORT).show();
                } else {
                    BmobQuery<User> query = new BmobQuery<>();
                    query.addWhereEqualTo("phone_number", phoneNumber);
                    query.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            if (e == null) {
                                Log.d("TAG", "list.size() = " + list.size());
                                if (list.size() == 0) {
                                    Toast.makeText(mContext, "手机号不存在，请重新输入", Toast.LENGTH_SHORT).show();
                                } else {
                                    String psw = list.get(0).getPassword();
                                    Log.d("TAG", "paw = " + psw);
                                    if (!password.equals(psw)) {
                                        Toast.makeText(mContext, "密码输入错误，请从新输入", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();
                                        String userName = list.get(0).getName();
                                        String imagePath = list.get(0).getHead_image();
                                        SharedPreferences sp = mContext.getSharedPreferences("info", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sp.edit();
                                        if (rememberPassword.isChecked()) {
                                            editor.putBoolean("remember_password", true);
                                        } else {
                                            editor.putBoolean("remember_password", false);
                                        }
                                        editor.putString("phoneNumber", phoneNumber);
                                        editor.putString("userName", userName);
                                        editor.putString("imagePath", imagePath);
                                        editor.putString("password", password);
                                        editor.putBoolean("isLogin", true);
                                        editor.apply();
                                        Intent intent = new Intent(LogInOrSignInActivity.this, MainActivity.class);
                                        intent.putExtra(MainActivity.LOGIN, true);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                    }
                                }

                            } else {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                break;

            case R.id.sing_in_tv:
                Intent singInIntent = new Intent(mContext, SingInActivity.class);
                mContext.startActivity(singInIntent);
                break;

        }
    }

}
