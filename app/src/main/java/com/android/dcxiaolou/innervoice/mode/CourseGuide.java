package com.android.dcxiaolou.innervoice.mode;

/*
* 课程推荐模块的课程引导类 配合RecyclerView使用
* 提取内容： id：课程编号  title：标题  cover：封面  joinnum：加入课程的人数  teacherName：老师名称
* */

public class CourseGuide {


    /**
     * id : 237
     * title : 如何用心理学为成长加速？
     * cover : https://ossimg.xinli001.com/20180502/88dc391d7e308fc0de2b80b3b52aa0d2.jpg
     * joinnum : 10209
     * teacherName : 王浩威
     */

    private String id;
    private String title;
    private String cover;
    private String joinnum;
    private String teacherName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getJoinnum() {
        return joinnum;
    }

    public void setJoinnum(String joinnum) {
        this.joinnum = joinnum;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
