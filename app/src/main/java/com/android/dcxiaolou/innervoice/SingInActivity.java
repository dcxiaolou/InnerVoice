package com.android.dcxiaolou.innervoice;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.dcxiaolou.innervoice.fragemnt.CenterFragment;
import com.android.dcxiaolou.innervoice.mode.User;

import java.util.regex.Pattern;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import de.hdodenhof.circleimageview.CircleImageView;

/*
 * 注册界面
 * */

public class SingInActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CHOOSE_PHOTO = 1;

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
    private EditText phoneNumberEt, userNameEt, passwordEt;
    private TextView singInTv, hintTv;

    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);

        mContext = getApplicationContext();

        userImage = (CircleImageView) findViewById(R.id.user_image);
        phoneNumberEt = (EditText) findViewById(R.id.phone_number);
        passwordEt = (EditText) findViewById(R.id.password);
        userNameEt = (EditText) findViewById(R.id.user_name);
        singInTv = (TextView) findViewById(R.id.sing_in);
        hintTv = (TextView) findViewById(R.id.hint);

        userImage.setOnClickListener(this);
        singInTv.setOnClickListener(this);

        initData();

    }


    private void initData() {

    }

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_image:
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SingInActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    //从相册中选择图片
                    openAlbum();
                    hintTv.setVisibility(View.GONE);
                }
                break;
            case R.id.sing_in:
                final String phoneNumber = phoneNumberEt.getText().toString();
                final String userName = userNameEt.getText().toString().trim();
                final String password = passwordEt.getText().toString().trim();
                if (!Pattern.matches(REGEX_MOBILE, phoneNumber)) {
                    Toast.makeText(mContext, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                } else if (!Pattern.matches(REGEX_PASSWORD, password)) {
                    Toast.makeText(mContext, "密码格式不正确，请输入6-12位的数字、字母哦", Toast.LENGTH_SHORT).show();
                } else if (imagePath == null) {
                    Toast.makeText(mContext, "头像还是空的哦", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User();
                    user.setName(userName);
                    user.setPhone_number(phoneNumber);
                    user.setHead_image(imagePath);
                    user.setPassword(password);
                    user.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                Toast.makeText(mContext, "注册成功", Toast.LENGTH_SHORT).show();
                                SharedPreferences sp = mContext.getSharedPreferences("info", MODE_PRIVATE);
                                sp.edit().putString("phoneNumber", phoneNumber).apply();
                                sp.edit().putString("userName", userName).apply();
                                sp.edit().putString("imagePath", imagePath).apply();
                                sp.edit().putString("password", password).apply();
                                sp.edit().putBoolean("isLogin", true).apply();
                                Intent intent = new Intent(SingInActivity.this, MainActivity.class);
                                intent.putExtra(MainActivity.LOGIN, true);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            break;
        }

    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }

    //SD卡读权限动态申请
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        //4.4以上版本使用这个方法处理图片
                        handleImageOkKitKat(data);
                    } else {
                        // 4.4 以下版本使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOkKitKat(Intent data) {
        imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方法处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径
            imagePath = uri.getPath();
        }
        displayImage(imagePath); // 根据图片路径显示图图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst())
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            userImage.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "Filed to get image", Toast.LENGTH_SHORT);
        }
    }

}
