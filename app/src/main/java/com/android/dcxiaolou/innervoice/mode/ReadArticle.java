package com.android.dcxiaolou.innervoice.mode;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/*
* 用于文章模块（用于BmobQuery，即从bmob查询ReadArticle数据库中相应的type和article）
* */

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
