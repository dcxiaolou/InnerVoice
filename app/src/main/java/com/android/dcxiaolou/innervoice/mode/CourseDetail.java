package com.android.dcxiaolou.innervoice.mode;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/*
* 课程详情（用于BmobQuery，即从bmob查询CourseDetail数据库中相应的detail）
* */

public class CourseDetail extends BmobObject {

    private BmobFile detail;

    public BmobFile getDetail() {
        return detail;
    }

    public void setDetail(BmobFile detail) {
        this.detail = detail;
    }
}
