package com.android.dcxiaolou.innervoice.mode;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class ReadCommon extends BmobObject {

    private String type;

    private String no;

    private BmobFile common;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public BmobFile getCommon() {
        return common;
    }

    public void setCommon(BmobFile common) {
        this.common = common;
    }
}
