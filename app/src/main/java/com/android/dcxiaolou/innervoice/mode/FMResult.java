package com.android.dcxiaolou.innervoice.mode;

import java.util.List;

public class FMResult {


    /**
     * code : 0
     * data : {"id":"22","title":"得之我幸，失之我命","cover":"https://ossimg.xinli001.com/visioncn/600x600/VCG41585307326.jpg","url":"http://ossfm.xinli001.com/itings/record/file_play/Mg/NDUxOV83NDhfMTIwMTNfMg/L3dpenphcmRhdWRpbzEvMjAxMjA3L2QyMTY2ZmJjLTZjZTItNGQ0Ni1hZDM2LTVmNzRkZjYwYTllOC5tcDM.mp3","speak":"心理FM","favnum":"1655","viewnum":"160019","background":"http://image.xinli001.com/20140519/174819f39cb0a5b97f450b.jpg","is_teacher":false,"absolute_url":"http://fm.xinli001.com/#22","url_list":[],"commentnum":"68","speak_url":"","content":"得之我幸，不得我命。用这种态度去面对，一切怨艾也不过如是而已\u2026\u2026(来自：蔷薇岛屿)","word_url":"","jingxuan_id":"2349"}
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
         * id : 22
         * title : 得之我幸，失之我命
         * cover : https://ossimg.xinli001.com/visioncn/600x600/VCG41585307326.jpg
         * url : http://ossfm.xinli001.com/itings/record/file_play/Mg/NDUxOV83NDhfMTIwMTNfMg/L3dpenphcmRhdWRpbzEvMjAxMjA3L2QyMTY2ZmJjLTZjZTItNGQ0Ni1hZDM2LTVmNzRkZjYwYTllOC5tcDM.mp3
         * speak : 心理FM
         * favnum : 1655
         * viewnum : 160019
         * background : http://image.xinli001.com/20140519/174819f39cb0a5b97f450b.jpg
         * is_teacher : false
         * absolute_url : http://fm.xinli001.com/#22
         * url_list : []
         * commentnum : 68
         * speak_url :
         * content : 得之我幸，不得我命。用这种态度去面对，一切怨艾也不过如是而已……(来自：蔷薇岛屿)
         * word_url :
         * jingxuan_id : 2349
         */

        private String id;
        private String title;
        private String cover;
        private String url;
        private String speak;
        private String favnum;
        private String viewnum;
        private String background;
        private boolean is_teacher;
        private String absolute_url;
        private String commentnum;
        private String speak_url;
        private String content;
        private String word_url;
        private String jingxuan_id;
        private List<?> url_list;

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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getSpeak() {
            return speak;
        }

        public void setSpeak(String speak) {
            this.speak = speak;
        }

        public String getFavnum() {
            return favnum;
        }

        public void setFavnum(String favnum) {
            this.favnum = favnum;
        }

        public String getViewnum() {
            return viewnum;
        }

        public void setViewnum(String viewnum) {
            this.viewnum = viewnum;
        }

        public String getBackground() {
            return background;
        }

        public void setBackground(String background) {
            this.background = background;
        }

        public boolean isIs_teacher() {
            return is_teacher;
        }

        public void setIs_teacher(boolean is_teacher) {
            this.is_teacher = is_teacher;
        }

        public String getAbsolute_url() {
            return absolute_url;
        }

        public void setAbsolute_url(String absolute_url) {
            this.absolute_url = absolute_url;
        }

        public String getCommentnum() {
            return commentnum;
        }

        public void setCommentnum(String commentnum) {
            this.commentnum = commentnum;
        }

        public String getSpeak_url() {
            return speak_url;
        }

        public void setSpeak_url(String speak_url) {
            this.speak_url = speak_url;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getWord_url() {
            return word_url;
        }

        public void setWord_url(String word_url) {
            this.word_url = word_url;
        }

        public String getJingxuan_id() {
            return jingxuan_id;
        }

        public void setJingxuan_id(String jingxuan_id) {
            this.jingxuan_id = jingxuan_id;
        }

        public List<?> getUrl_list() {
            return url_list;
        }

        public void setUrl_list(List<?> url_list) {
            this.url_list = url_list;
        }
    }
}
