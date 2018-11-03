package com.android.dcxiaolou.innervoice.mode;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class CourseIntroduce extends BmobObject {

    private BmobFile introduce;

    public BmobFile getIntroduce() {
        return introduce;
    }

    public void setIntroduce(BmobFile introduce) {
        this.introduce = introduce;
    }
}
