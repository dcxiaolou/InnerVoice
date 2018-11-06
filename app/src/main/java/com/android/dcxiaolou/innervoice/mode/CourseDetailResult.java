package com.android.dcxiaolou.innervoice.mode;

import cn.bmob.v3.BmobObject;

/*
* 课程推荐页的课程播放（用于Gson解析json数据）
* */

public class CourseDetailResult extends BmobObject {


    /**
     * media : https://plvod01.videocc.net/605ea32bee/4/605ea32beed982311b79eda1e6355274_1.mp3
     */

    private String media;

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }
}
