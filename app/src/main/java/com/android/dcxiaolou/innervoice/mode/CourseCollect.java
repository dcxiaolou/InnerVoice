package com.android.dcxiaolou.innervoice.mode;

/*
* 课程汇总 包含所有课程的基本信息
* */

import java.util.List;

public class CourseCollect {


    /**
     * code : 0
     * data : {"items":[{"id":"194","title":"每天3分钟，获得一个心理知识 | 3分钟心理学","cover":"https://ossimg.xinli001.com/20181008/5a5a33197f293767f5d1f13b78174733.jpg","yuanjia":"19900","xianjia":"0","viewnum":"375221","joinnum":"75720","tuijianyu":"每天更新1节","yijuhua":"每天更新3分钟","teacherName":"壹心理专业团队"},{"id":"237","title":"如何用心理学为成长加速？","cover":"https://ossimg.xinli001.com/20180502/88dc391d7e308fc0de2b80b3b52aa0d2.jpg","yuanjia":"9900","xianjia":"0","viewnum":"45690","joinnum":"10209","tuijianyu":"","yijuhua":"","teacherName":"王浩威"},{"id":"202","title":"申荷永对话周国平：如何过好这一生？","cover":"https://ossimg.xinli001.com/20181008/bce83f6af95e1981da77ae7ddfdb19ec.jpg","yuanjia":"999","xianjia":"0","viewnum":"22892","joinnum":"5309","tuijianyu":"","yijuhua":"","teacherName":"申荷永&周国平"},{"id":"1","title":"感谢自己的不完美 | 武志红","cover":"https://ossimg.xinli001.com/20160804/9aa004e9d89757161186107dc10f4612.jpg","yuanjia":"0","xianjia":"0","viewnum":"77951","joinnum":"18822","tuijianyu":"","yijuhua":"","teacherName":"武志红"},{"id":"228","title":"毕淑敏揭秘90后幸福答案 ：买房、结婚、工作、原生家庭！","cover":"https://ossimg.xinli001.com/20180904/a2f364e148dbb8747deada7d921e1de3.png","yuanjia":"0","xianjia":"0","viewnum":"14393","joinnum":"3303","tuijianyu":"","yijuhua":"","teacherName":"毕淑敏"},{"id":"192","title":"申荷永对话武志红：如何应对中国式焦虑","cover":"https://ossimg.xinli001.com/20180119/93782f97d86af9ce5efc6d0dd2d17f77.jpg","yuanjia":"9900","xianjia":"0","viewnum":"31984","joinnum":"6604","tuijianyu":"","yijuhua":"","teacherName":"武志红"},{"id":"244","title":"对话梁晓声：为什么我们对平凡的人生深怀恐惧？","cover":"https://ossimg.xinli001.com/20180612/a8255e8452272d6483abc407c485cb22.jpg","yuanjia":"1900","xianjia":"0","viewnum":"7964","joinnum":"2213","tuijianyu":"","yijuhua":"","teacherName":"梁晓声"},{"id":"136","title":"\u201c同性恋\u201d究竟是一种怎样的体验？","cover":"https://ossimg.xinli001.com/20170605/8e30a39aa7d2dd64417451483e9185c8.jpg","yuanjia":"999","xianjia":"0","viewnum":"41800","joinnum":"5978","tuijianyu":"免费","yijuhua":"","teacherName":"卢美妏"},{"id":"35","title":"在国内看心理咨询是一种什么体验？","cover":"https://ossimg.xinli001.com/20160921/5086b6b35f62806ecc123a5ef5b264a7.jpg","yuanjia":"666","xianjia":"0","viewnum":"12986","joinnum":"3042","tuijianyu":"","yijuhua":"","teacherName":"吴佩营"},{"id":"8","title":"心理咨询师教你如何摆脱强迫症","cover":"https://ossimg.xinli001.com/20160804/b5479d4b8f4ba83f30af216f4948307b.jpg","yuanjia":"0","xianjia":"0","viewnum":"24778","joinnum":"5903","tuijianyu":"","yijuhua":"","teacherName":"邱凤"}],"name":"free","tag_list":[{"id":"4","custome_tag_id":"222","name":"个人成长","depth":"1","pid":"4"},{"id":"5","custome_tag_id":"263","name":"两性情感","depth":"1","pid":"4"},{"id":"8","custome_tag_id":"375","name":"人际关系","depth":"1","pid":"4"},{"id":"6","custome_tag_id":"330","name":"家庭亲子","depth":"1","pid":"4"},{"id":"7","custome_tag_id":"346","name":"心理健康","depth":"1","pid":"4"}],"tagName":"","lessonType":"normal","backBtnUrl":"https://static.xinli001.com/msite/index.html#/lesson-index?type=normal"}
     */

    private int code;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * items : [{"id":"194","title":"每天3分钟，获得一个心理知识 | 3分钟心理学","cover":"https://ossimg.xinli001.com/20181008/5a5a33197f293767f5d1f13b78174733.jpg","yuanjia":"19900","xianjia":"0","viewnum":"375221","joinnum":"75720","tuijianyu":"每天更新1节","yijuhua":"每天更新3分钟","teacherName":"壹心理专业团队"},{"id":"237","title":"如何用心理学为成长加速？","cover":"https://ossimg.xinli001.com/20180502/88dc391d7e308fc0de2b80b3b52aa0d2.jpg","yuanjia":"9900","xianjia":"0","viewnum":"45690","joinnum":"10209","tuijianyu":"","yijuhua":"","teacherName":"王浩威"},{"id":"202","title":"申荷永对话周国平：如何过好这一生？","cover":"https://ossimg.xinli001.com/20181008/bce83f6af95e1981da77ae7ddfdb19ec.jpg","yuanjia":"999","xianjia":"0","viewnum":"22892","joinnum":"5309","tuijianyu":"","yijuhua":"","teacherName":"申荷永&周国平"},{"id":"1","title":"感谢自己的不完美 | 武志红","cover":"https://ossimg.xinli001.com/20160804/9aa004e9d89757161186107dc10f4612.jpg","yuanjia":"0","xianjia":"0","viewnum":"77951","joinnum":"18822","tuijianyu":"","yijuhua":"","teacherName":"武志红"},{"id":"228","title":"毕淑敏揭秘90后幸福答案 ：买房、结婚、工作、原生家庭！","cover":"https://ossimg.xinli001.com/20180904/a2f364e148dbb8747deada7d921e1de3.png","yuanjia":"0","xianjia":"0","viewnum":"14393","joinnum":"3303","tuijianyu":"","yijuhua":"","teacherName":"毕淑敏"},{"id":"192","title":"申荷永对话武志红：如何应对中国式焦虑","cover":"https://ossimg.xinli001.com/20180119/93782f97d86af9ce5efc6d0dd2d17f77.jpg","yuanjia":"9900","xianjia":"0","viewnum":"31984","joinnum":"6604","tuijianyu":"","yijuhua":"","teacherName":"武志红"},{"id":"244","title":"对话梁晓声：为什么我们对平凡的人生深怀恐惧？","cover":"https://ossimg.xinli001.com/20180612/a8255e8452272d6483abc407c485cb22.jpg","yuanjia":"1900","xianjia":"0","viewnum":"7964","joinnum":"2213","tuijianyu":"","yijuhua":"","teacherName":"梁晓声"},{"id":"136","title":"\u201c同性恋\u201d究竟是一种怎样的体验？","cover":"https://ossimg.xinli001.com/20170605/8e30a39aa7d2dd64417451483e9185c8.jpg","yuanjia":"999","xianjia":"0","viewnum":"41800","joinnum":"5978","tuijianyu":"免费","yijuhua":"","teacherName":"卢美妏"},{"id":"35","title":"在国内看心理咨询是一种什么体验？","cover":"https://ossimg.xinli001.com/20160921/5086b6b35f62806ecc123a5ef5b264a7.jpg","yuanjia":"666","xianjia":"0","viewnum":"12986","joinnum":"3042","tuijianyu":"","yijuhua":"","teacherName":"吴佩营"},{"id":"8","title":"心理咨询师教你如何摆脱强迫症","cover":"https://ossimg.xinli001.com/20160804/b5479d4b8f4ba83f30af216f4948307b.jpg","yuanjia":"0","xianjia":"0","viewnum":"24778","joinnum":"5903","tuijianyu":"","yijuhua":"","teacherName":"邱凤"}]
         * name : free
         * tag_list : [{"id":"4","custome_tag_id":"222","name":"个人成长","depth":"1","pid":"4"},{"id":"5","custome_tag_id":"263","name":"两性情感","depth":"1","pid":"4"},{"id":"8","custome_tag_id":"375","name":"人际关系","depth":"1","pid":"4"},{"id":"6","custome_tag_id":"330","name":"家庭亲子","depth":"1","pid":"4"},{"id":"7","custome_tag_id":"346","name":"心理健康","depth":"1","pid":"4"}]
         * tagName :
         * lessonType : normal
         * backBtnUrl : https://static.xinli001.com/msite/index.html#/lesson-index?type=normal
         */

        private String name;
        private String tagName;
        private String lessonType;
        private String backBtnUrl;
        private List<ItemsBean> items;
        private List<TagListBean> tag_list;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTagName() {
            return tagName;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }

        public String getLessonType() {
            return lessonType;
        }

        public void setLessonType(String lessonType) {
            this.lessonType = lessonType;
        }

        public String getBackBtnUrl() {
            return backBtnUrl;
        }

        public void setBackBtnUrl(String backBtnUrl) {
            this.backBtnUrl = backBtnUrl;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public List<TagListBean> getTag_list() {
            return tag_list;
        }

        public void setTag_list(List<TagListBean> tag_list) {
            this.tag_list = tag_list;
        }

        public static class ItemsBean {
            /**
             * id : 194
             * title : 每天3分钟，获得一个心理知识 | 3分钟心理学
             * cover : https://ossimg.xinli001.com/20181008/5a5a33197f293767f5d1f13b78174733.jpg
             * yuanjia : 19900
             * xianjia : 0
             * viewnum : 375221
             * joinnum : 75720
             * tuijianyu : 每天更新1节
             * yijuhua : 每天更新3分钟
             * teacherName : 壹心理专业团队
             */

            private String id;
            private String title;
            private String cover;
            private String yuanjia;
            private String xianjia;
            private String viewnum;
            private String joinnum;
            private String tuijianyu;
            private String yijuhua;
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

            public String getYuanjia() {
                return yuanjia;
            }

            public void setYuanjia(String yuanjia) {
                this.yuanjia = yuanjia;
            }

            public String getXianjia() {
                return xianjia;
            }

            public void setXianjia(String xianjia) {
                this.xianjia = xianjia;
            }

            public String getViewnum() {
                return viewnum;
            }

            public void setViewnum(String viewnum) {
                this.viewnum = viewnum;
            }

            public String getJoinnum() {
                return joinnum;
            }

            public void setJoinnum(String joinnum) {
                this.joinnum = joinnum;
            }

            public String getTuijianyu() {
                return tuijianyu;
            }

            public void setTuijianyu(String tuijianyu) {
                this.tuijianyu = tuijianyu;
            }

            public String getYijuhua() {
                return yijuhua;
            }

            public void setYijuhua(String yijuhua) {
                this.yijuhua = yijuhua;
            }

            public String getTeacherName() {
                return teacherName;
            }

            public void setTeacherName(String teacherName) {
                this.teacherName = teacherName;
            }
        }

        public static class TagListBean {
            /**
             * id : 4
             * custome_tag_id : 222
             * name : 个人成长
             * depth : 1
             * pid : 4
             */

            private String id;
            private String custome_tag_id;
            private String name;
            private String depth;
            private String pid;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCustome_tag_id() {
                return custome_tag_id;
            }

            public void setCustome_tag_id(String custome_tag_id) {
                this.custome_tag_id = custome_tag_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDepth() {
                return depth;
            }

            public void setDepth(String depth) {
                this.depth = depth;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }
        }
    }
}
