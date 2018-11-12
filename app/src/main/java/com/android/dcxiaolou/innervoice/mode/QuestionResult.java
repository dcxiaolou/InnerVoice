package com.android.dcxiaolou.innervoice.mode;

import java.util.List;

/*
* 问答模块的question，gson解析类
* */

public class QuestionResult {

    /**
     * question_user_img : http://image.xinli001.com/images/avatar.jpg!80
     * question_answer_num :  8个回答
     * question_push_time : 11小时前
     * question_reader_num : 201 阅读
     * question_content :
     你们当你们不觉得回答这么多问题很累吗？而且还要一个个分析，你们是因为什么当社工的呢？我经常看到西宝回答问题的时候会自我袒露看来西宝，现实生活中应该挺喜欢听人倾诉的吧?
     * question_tag : ["热点","互动反馈"]
     * title : 想问问西宝和吻风
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
