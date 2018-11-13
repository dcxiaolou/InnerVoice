package com.android.dcxiaolou.innervoice.mode;

import java.io.Serializable;
import java.util.List;

/*
* 问答模块的question，gson解析类，并序列化，便于用intent传递
* */

public class QuestionResult implements Serializable {


    /**
     * question_user_img : https://ossimg.xinli001.com/20160608/2f2777fb05cd22f45619aa6310af09e4.jpg!80
     * question_answer_num :  2个回答
     * question_push_time : 4小时前
     * question_reader_num : 52 阅读
     * question_content :
     我是一名学生，有一天我和女友在教室走廊聊天一群学校的小混混 向我们走来，走到我们面前的时候停下了 最前面的一个人拿出一个避孕套摆在我女友的面前 然后我当时的心情很复杂 我当时伸手想夺下那避孕套 然后他马上把手伸回去了 然后就走了 走了四五米远之后扭头对我吆喝：你要我还不给你呢。当时我没有回头 跟女友在说话 女朋友也听见那句话了 他什么都没有 感觉心情不是很好了。事后我回到宿舍心情一直很郁闷 我感觉自己的尊严受到了打击 在女朋友面前表现的很懦弱 很没有面子。过了一天 中午的时候我送女友回教室的路上 在前面又碰到了那个人 当时他们是二三个人 我们从他们身边走过去 他们嘲笑 大声吆喝 女朋友很反感拽着我就走了 从这俩件事以后 我感觉抬不起头来了 不愿意去学校了 更害怕碰见他们 不知道如何应对 就算嘲笑我一个人也没有关系也不想守着女朋友让我丢脸
     * question_tag : ["人际","社交恐惧","矛盾冲突","人际边界"]
     * title : 遭受校园欺辱嘲笑，无法正常生活不知如何是好
     */

    private String question_user_img;
    private String question_answer_num;
    private String question_push_time;
    private String question_reader_num;
    private String question_content;
    private String title;
    private List<String> question_tag;

    public String getQuestion_user_img() {
        return question_user_img;
    }

    public void setQuestion_user_img(String question_user_img) {
        this.question_user_img = question_user_img;
    }

    public String getQuestion_answer_num() {
        return question_answer_num;
    }

    public void setQuestion_answer_num(String question_answer_num) {
        this.question_answer_num = question_answer_num;
    }

    public String getQuestion_push_time() {
        return question_push_time;
    }

    public void setQuestion_push_time(String question_push_time) {
        this.question_push_time = question_push_time;
    }

    public String getQuestion_reader_num() {
        return question_reader_num;
    }

    public void setQuestion_reader_num(String question_reader_num) {
        this.question_reader_num = question_reader_num;
    }

    public String getQuestion_content() {
        return question_content;
    }

    public void setQuestion_content(String question_content) {
        this.question_content = question_content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getQuestion_tag() {
        return question_tag;
    }

    public void setQuestion_tag(List<String> question_tag) {
        this.question_tag = question_tag;
    }
}
