package com.android.dcxiaolou.innervoice.mode;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/*
* 用于课程推荐（简介）页（用于BmobQuery，即从bmob查询CourseIntroduce数据库中相应的introduce）
* */

public class CourseIntroduce extends BmobObject {

    private BmobFile introduce;

    public BmobFile getIntroduce() {
        return introduce;
    }

    public void setIntroduce(BmobFile introduce) {
        this.introduce = introduce;
    }
}
