package com.android.dcxiaolou.innervoice.mode;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class ReadArticle extends BmobObject {

    private String type;
    private BmobFile article;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BmobFile getBmobFile() {
        return article;
    }

    public void setBmobFile(BmobFile article) {
        this.article = article;
    }
}
