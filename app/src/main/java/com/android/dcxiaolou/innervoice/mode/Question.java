package com.android.dcxiaolou.innervoice.mode;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
/*
* 问答模块Question的bmob模板
* */
public class Question extends BmobObject {

    private BmobFile question;

    public BmobFile getQuestion() {
        return question;
    }

    public void setQuestion(BmobFile question) {
        this.question = question;
    }
}
