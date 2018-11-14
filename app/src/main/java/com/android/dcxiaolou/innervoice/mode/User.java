package com.android.dcxiaolou.innervoice.mode;

import cn.bmob.v3.BmobObject;

/*
* 用户类，用来与bmob数据库中的User表对接
* */

public class User extends BmobObject {

    private String name;

    private String phone_number;

    private String password;

    private String head_image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHead_image() {
        return head_image;
    }

    public void setHead_image(String head_image) {
        this.head_image = head_image;
    }
}
