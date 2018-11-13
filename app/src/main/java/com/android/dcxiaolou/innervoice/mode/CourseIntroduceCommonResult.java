package com.android.dcxiaolou.innervoice.mode;

import java.util.List;

/*
* 用于课程简介模块的评论
* */

public class CourseIntroduceCommonResult {


    /**
     * code : zero
     * data : [{"id":6363,"userId":1005642223,"userKey":"159682177783865344104","content":"留出整块的时间做深度阅读学习，很重要的是做思维导图和复盘。一个知识点反复嘴嚼和思考，才会被吸收，如同吃饭反复嘴嚼会品出味道，胃肠也吸收的好。","score":5,"zanNum":8,"isZan":zero,"created":"2018-07-15","userNickname":"xinli_lesson_8gzGwL","userAvatar":"https://ossimg.xinli001.com/20180715/917d9d24a45151cbb88726bbdf70179c.jpg","collegeRateReplyVos":[{"replyId":127,"rateId":6363,"replyNickname":"壹心理学院","content":"加油，我们和你一起进步~~","status":1,"created":"2018-07-17"}]}]
     */

    private int code;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 6363
         * userId : 1005642223
         * userKey : 159682177783865344104
         * content : 留出整块的时间做深度阅读学习，很重要的是做思维导图和复盘。一个知识点反复嘴嚼和思考，才会被吸收，如同吃饭反复嘴嚼会品出味道，胃肠也吸收的好。
         * score : 5
         * zanNum : 8
         * isZan : zero
         * created : 2018-07-15
         * userNickname : xinli_lesson_8gzGwL
         * userAvatar : https://ossimg.xinli001.com/20180715/917d9d24a45151cbb88726bbdf70179c.jpg
         * collegeRateReplyVos : [{"replyId":127,"rateId":6363,"replyNickname":"壹心理学院","content":"加油，我们和你一起进步~~","status":1,"created":"2018-07-17"}]
         */

        private int id;
        private int userId;
        private String userKey;
        private String content;
        private int score;
        private int zanNum;
        private int isZan;
        private String created;
        private String userNickname;
        private String userAvatar;
        private List<CollegeRateReplyVosBean> collegeRateReplyVos;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserKey() {
            return userKey;
        }

        public void setUserKey(String userKey) {
            this.userKey = userKey;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getZanNum() {
            return zanNum;
        }

        public void setZanNum(int zanNum) {
            this.zanNum = zanNum;
        }

        public int getIsZan() {
            return isZan;
        }

        public void setIsZan(int isZan) {
            this.isZan = isZan;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getUserNickname() {
            return userNickname;
        }

        public void setUserNickname(String userNickname) {
            this.userNickname = userNickname;
        }

        public String getUserAvatar() {
            return userAvatar;
        }

        public void setUserAvatar(String userAvatar) {
            this.userAvatar = userAvatar;
        }

        public List<CollegeRateReplyVosBean> getCollegeRateReplyVos() {
            return collegeRateReplyVos;
        }

        public void setCollegeRateReplyVos(List<CollegeRateReplyVosBean> collegeRateReplyVos) {
            this.collegeRateReplyVos = collegeRateReplyVos;
        }

        public static class CollegeRateReplyVosBean {
            /**
             * replyId : 127
             * rateId : 6363
             * replyNickname : 壹心理学院
             * content : 加油，我们和你一起进步~~
             * status : 1
             * created : 2018-07-17
             */

            private int replyId;
            private int rateId;
            private String replyNickname;
            private String content;
            private int status;
            private String created;

            public int getReplyId() {
                return replyId;
            }

            public void setReplyId(int replyId) {
                this.replyId = replyId;
            }

            public int getRateId() {
                return rateId;
            }

            public void setRateId(int rateId) {
                this.rateId = rateId;
            }

            public String getReplyNickname() {
                return replyNickname;
            }

            public void setReplyNickname(String replyNickname) {
                this.replyNickname = replyNickname;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }
        }
    }
}
