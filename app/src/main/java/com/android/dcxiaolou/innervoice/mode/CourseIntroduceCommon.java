package com.android.dcxiaolou.innervoice.mode;

/*
* 课程简介模块的评论
* */

import java.util.List;

public class CourseIntroduceCommon {

    private String id;

    private String userId;

    private String userName;

    private String userImagePath;

    private String pushTime;

    private String likeNum; // 点赞人数

    private String replyNum; // 回复评论的人数

    private String content;

    private List<CourseIntroduceCommonResult.DataBean.CollegeRateReplyVosBean> ReplayCommons; //评论的回复

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImagePath() {
        return userImagePath;
    }

    public void setUserImagePath(String userImagePath) {
        this.userImagePath = userImagePath;
    }

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

    public String getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(String likeNum) {
        this.likeNum = likeNum;
    }

    public String getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(String replyNum) {
        this.replyNum = replyNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<CourseIntroduceCommonResult.DataBean.CollegeRateReplyVosBean> getReplayCommons() {
        return ReplayCommons;
    }

    public void setReplayCommons(List<CourseIntroduceCommonResult.DataBean.CollegeRateReplyVosBean> replayCommons) {
        ReplayCommons = replayCommons;
    }

}
