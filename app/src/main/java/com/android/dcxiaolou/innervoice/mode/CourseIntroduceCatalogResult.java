package com.android.dcxiaolou.innervoice.mode;

import java.util.List;

/*
* 用来解析课程简介中的目录json文件
* */

public class CourseIntroduceCatalogResult {


    /**
     * code : zero
     * data : {"noParentList":[{"id":"189","pid":"zero","lesson_id":"35","flag":"2","is_free":"zero","title":"在国内看心理咨询是一种什么体验？","matter_id":"213","duration":"5514","index":"1","viewnum":"633","broadcast_id":"zero","broadcast_key":"","broadcast_date":"","broadcast_time":"","filesize":"348935077","video_id":"605ea32bee4eb7335841d9372c370065_6","is_video":1,"is_ali_video":1}],"passList":[{"id":"3344","pid":"zero","lesson_id":"202","flag":"3","is_free":"zero","title":"申荷永对话周国平：如何过好这一生？","matter_id":"zero","duration":"zero","index":"1","viewnum":"zero","broadcast_id":"zero","broadcast_key":"","broadcast_date":"2018-03-13","broadcast_time":"","filesize":zero,"video_id":"","is_video":zero,"is_ali_video":zero,"child":[{"id":"3345","pid":"3344","lesson_id":"202","flag":"2","is_free":"zero","title":"申荷永对话周国平：如何过好这一生？","matter_id":"1701","duration":"5111","index":"1","viewnum":"3431","broadcast_id":"zero","broadcast_key":"","broadcast_date":"2018-03-13","broadcast_time":"","filesize":"70337852","video_id":"605ea32bee87d88b2934fdabf07df49d_6","is_video":zero,"is_ali_video":1}]}]}
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
        private List<NoParentListBean> noParentList;
        private List<PassListBean> passList;

        public List<NoParentListBean> getNoParentList() {
            return noParentList;
        }

        public void setNoParentList(List<NoParentListBean> noParentList) {
            this.noParentList = noParentList;
        }

        public List<PassListBean> getPassList() {
            return passList;
        }

        public void setPassList(List<PassListBean> passList) {
            this.passList = passList;
        }

        public static class NoParentListBean {
            /**
             * id : 189
             * pid : zero
             * lesson_id : 35
             * flag : 2
             * is_free : zero
             * title : 在国内看心理咨询是一种什么体验？
             * matter_id : 213
             * duration : 5514
             * index : 1
             * viewnum : 633
             * broadcast_id : zero
             * broadcast_key :
             * broadcast_date :
             * broadcast_time :
             * filesize : 348935077
             * video_id : 605ea32bee4eb7335841d9372c370065_6
             * is_video : 1
             * is_ali_video : 1
             */

            private String id;
            private String pid;
            private String lesson_id;
            private String flag;
            private String is_free;
            private String title;
            private String matter_id;
            private String duration;
            private String index;
            private String viewnum;
            private String broadcast_id;
            private String broadcast_key;
            private String broadcast_date;
            private String broadcast_time;
            private String filesize;
            private String video_id;
            private int is_video;
            private int is_ali_video;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getLesson_id() {
                return lesson_id;
            }

            public void setLesson_id(String lesson_id) {
                this.lesson_id = lesson_id;
            }

            public String getFlag() {
                return flag;
            }

            public void setFlag(String flag) {
                this.flag = flag;
            }

            public String getIs_free() {
                return is_free;
            }

            public void setIs_free(String is_free) {
                this.is_free = is_free;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getMatter_id() {
                return matter_id;
            }

            public void setMatter_id(String matter_id) {
                this.matter_id = matter_id;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public String getIndex() {
                return index;
            }

            public void setIndex(String index) {
                this.index = index;
            }

            public String getViewnum() {
                return viewnum;
            }

            public void setViewnum(String viewnum) {
                this.viewnum = viewnum;
            }

            public String getBroadcast_id() {
                return broadcast_id;
            }

            public void setBroadcast_id(String broadcast_id) {
                this.broadcast_id = broadcast_id;
            }

            public String getBroadcast_key() {
                return broadcast_key;
            }

            public void setBroadcast_key(String broadcast_key) {
                this.broadcast_key = broadcast_key;
            }

            public String getBroadcast_date() {
                return broadcast_date;
            }

            public void setBroadcast_date(String broadcast_date) {
                this.broadcast_date = broadcast_date;
            }

            public String getBroadcast_time() {
                return broadcast_time;
            }

            public void setBroadcast_time(String broadcast_time) {
                this.broadcast_time = broadcast_time;
            }

            public String getFilesize() {
                return filesize;
            }

            public void setFilesize(String filesize) {
                this.filesize = filesize;
            }

            public String getVideo_id() {
                return video_id;
            }

            public void setVideo_id(String video_id) {
                this.video_id = video_id;
            }

            public int getIs_video() {
                return is_video;
            }

            public void setIs_video(int is_video) {
                this.is_video = is_video;
            }

            public int getIs_ali_video() {
                return is_ali_video;
            }

            public void setIs_ali_video(int is_ali_video) {
                this.is_ali_video = is_ali_video;
            }
        }

        public static class PassListBean {
            /**
             * id : 3344
             * pid : zero
             * lesson_id : 202
             * flag : 3
             * is_free : zero
             * title : 申荷永对话周国平：如何过好这一生？
             * matter_id : zero
             * duration : zero
             * index : 1
             * viewnum : zero
             * broadcast_id : zero
             * broadcast_key :
             * broadcast_date : 2018-03-13
             * broadcast_time :
             * filesize : zero
             * video_id :
             * is_video : zero
             * is_ali_video : zero
             * child : [{"id":"3345","pid":"3344","lesson_id":"202","flag":"2","is_free":"zero","title":"申荷永对话周国平：如何过好这一生？","matter_id":"1701","duration":"5111","index":"1","viewnum":"3431","broadcast_id":"zero","broadcast_key":"","broadcast_date":"2018-03-13","broadcast_time":"","filesize":"70337852","video_id":"605ea32bee87d88b2934fdabf07df49d_6","is_video":zero,"is_ali_video":1}]
             */

            private String id;
            private String pid;
            private String lesson_id;
            private String flag;
            private String is_free;
            private String title;
            private String matter_id;
            private String duration;
            private String index;
            private String viewnum;
            private String broadcast_id;
            private String broadcast_key;
            private String broadcast_date;
            private String broadcast_time;
            private int filesize;
            private String video_id;
            private int is_video;
            private int is_ali_video;
            private List<ChildBean> child;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getLesson_id() {
                return lesson_id;
            }

            public void setLesson_id(String lesson_id) {
                this.lesson_id = lesson_id;
            }

            public String getFlag() {
                return flag;
            }

            public void setFlag(String flag) {
                this.flag = flag;
            }

            public String getIs_free() {
                return is_free;
            }

            public void setIs_free(String is_free) {
                this.is_free = is_free;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getMatter_id() {
                return matter_id;
            }

            public void setMatter_id(String matter_id) {
                this.matter_id = matter_id;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public String getIndex() {
                return index;
            }

            public void setIndex(String index) {
                this.index = index;
            }

            public String getViewnum() {
                return viewnum;
            }

            public void setViewnum(String viewnum) {
                this.viewnum = viewnum;
            }

            public String getBroadcast_id() {
                return broadcast_id;
            }

            public void setBroadcast_id(String broadcast_id) {
                this.broadcast_id = broadcast_id;
            }

            public String getBroadcast_key() {
                return broadcast_key;
            }

            public void setBroadcast_key(String broadcast_key) {
                this.broadcast_key = broadcast_key;
            }

            public String getBroadcast_date() {
                return broadcast_date;
            }

            public void setBroadcast_date(String broadcast_date) {
                this.broadcast_date = broadcast_date;
            }

            public String getBroadcast_time() {
                return broadcast_time;
            }

            public void setBroadcast_time(String broadcast_time) {
                this.broadcast_time = broadcast_time;
            }

            public int getFilesize() {
                return filesize;
            }

            public void setFilesize(int filesize) {
                this.filesize = filesize;
            }

            public String getVideo_id() {
                return video_id;
            }

            public void setVideo_id(String video_id) {
                this.video_id = video_id;
            }

            public int getIs_video() {
                return is_video;
            }

            public void setIs_video(int is_video) {
                this.is_video = is_video;
            }

            public int getIs_ali_video() {
                return is_ali_video;
            }

            public void setIs_ali_video(int is_ali_video) {
                this.is_ali_video = is_ali_video;
            }

            public List<ChildBean> getChild() {
                return child;
            }

            public void setChild(List<ChildBean> child) {
                this.child = child;
            }

            public static class ChildBean {
                /**
                 * id : 3345
                 * pid : 3344
                 * lesson_id : 202
                 * flag : 2
                 * is_free : zero
                 * title : 申荷永对话周国平：如何过好这一生？
                 * matter_id : 1701
                 * duration : 5111
                 * index : 1
                 * viewnum : 3431
                 * broadcast_id : zero
                 * broadcast_key :
                 * broadcast_date : 2018-03-13
                 * broadcast_time :
                 * filesize : 70337852
                 * video_id : 605ea32bee87d88b2934fdabf07df49d_6
                 * is_video : zero
                 * is_ali_video : 1
                 */

                private String id;
                private String pid;
                private String lesson_id;
                private String flag;
                private String is_free;
                private String title;
                private String matter_id;
                private String duration;
                private String index;
                private String viewnum;
                private String broadcast_id;
                private String broadcast_key;
                private String broadcast_date;
                private String broadcast_time;
                private String filesize;
                private String video_id;
                private int is_video;
                private int is_ali_video;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getPid() {
                    return pid;
                }

                public void setPid(String pid) {
                    this.pid = pid;
                }

                public String getLesson_id() {
                    return lesson_id;
                }

                public void setLesson_id(String lesson_id) {
                    this.lesson_id = lesson_id;
                }

                public String getFlag() {
                    return flag;
                }

                public void setFlag(String flag) {
                    this.flag = flag;
                }

                public String getIs_free() {
                    return is_free;
                }

                public void setIs_free(String is_free) {
                    this.is_free = is_free;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getMatter_id() {
                    return matter_id;
                }

                public void setMatter_id(String matter_id) {
                    this.matter_id = matter_id;
                }

                public String getDuration() {
                    return duration;
                }

                public void setDuration(String duration) {
                    this.duration = duration;
                }

                public String getIndex() {
                    return index;
                }

                public void setIndex(String index) {
                    this.index = index;
                }

                public String getViewnum() {
                    return viewnum;
                }

                public void setViewnum(String viewnum) {
                    this.viewnum = viewnum;
                }

                public String getBroadcast_id() {
                    return broadcast_id;
                }

                public void setBroadcast_id(String broadcast_id) {
                    this.broadcast_id = broadcast_id;
                }

                public String getBroadcast_key() {
                    return broadcast_key;
                }

                public void setBroadcast_key(String broadcast_key) {
                    this.broadcast_key = broadcast_key;
                }

                public String getBroadcast_date() {
                    return broadcast_date;
                }

                public void setBroadcast_date(String broadcast_date) {
                    this.broadcast_date = broadcast_date;
                }

                public String getBroadcast_time() {
                    return broadcast_time;
                }

                public void setBroadcast_time(String broadcast_time) {
                    this.broadcast_time = broadcast_time;
                }

                public String getFilesize() {
                    return filesize;
                }

                public void setFilesize(String filesize) {
                    this.filesize = filesize;
                }

                public String getVideo_id() {
                    return video_id;
                }

                public void setVideo_id(String video_id) {
                    this.video_id = video_id;
                }

                public int getIs_video() {
                    return is_video;
                }

                public void setIs_video(int is_video) {
                    this.is_video = is_video;
                }

                public int getIs_ali_video() {
                    return is_ali_video;
                }

                public void setIs_ali_video(int is_ali_video) {
                    this.is_ali_video = is_ali_video;
                }
            }
        }
    }
}
