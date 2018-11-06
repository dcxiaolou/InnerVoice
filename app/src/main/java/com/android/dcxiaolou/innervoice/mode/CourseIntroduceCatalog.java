package com.android.dcxiaolou.innervoice.mode;

/*
* 课程简介的目录 标题 当前项的视频地址 视频时长 当前的视频是目录中的第几个
* */

public class CourseIntroduceCatalog {

    private int no; //当前课程中的第几个

    private String title, playPath, time, index;

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlayPath() {
        return playPath;
    }

    public void setPlayPath(String playPath) {
        this.playPath = playPath;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
