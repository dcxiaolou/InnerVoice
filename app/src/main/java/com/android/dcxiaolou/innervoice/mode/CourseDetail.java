package com.android.dcxiaolou.innervoice.mode;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class CourseDetail extends BmobObject {

    private BmobFile detail;

    public BmobFile getDetail() {
        return detail;
    }

    public void setDetail(BmobFile detail) {
        this.detail = detail;
    }
}
