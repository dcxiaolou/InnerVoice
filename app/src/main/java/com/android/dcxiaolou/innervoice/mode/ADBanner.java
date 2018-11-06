package com.android.dcxiaolou.innervoice.mode;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/*
* 主页banner
* */

public class ADBanner extends BmobObject {

    private BmobFile banner;

    public BmobFile getBmobFile() {
        return banner;
    }

    public void setBmobFile(BmobFile banner) {
        this.banner = banner;
    }
}
