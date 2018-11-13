package com.android.dcxiaolou.innervoice.mode;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/*
* bmob查询模板 Answer表
* */

public class Answer extends BmobObject {

    private String type;

    private BmobFile answer;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BmobFile getAnswer() {
        return answer;
    }

    public void setAnswer(BmobFile answer) {
        this.answer = answer;
    }
}
