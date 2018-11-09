package com.android.dcxiaolou.innervoice.mode;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/*
* 从bmob数据库中获取FM
* */

public class FM extends BmobObject {

    private String type;

    private String kind;

    private BmobFile fm;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public BmobFile getFm() {
        return fm;
    }

    public void setFm(BmobFile fm) {
        this.fm = fm;
    }
}
