package com.android.dcxiaolou.innervoice.mode;

import java.io.Serializable;
import java.util.List;

/*
 * 对阅读模块下的文章返回的json文件进行处理，便于使用Gson进行解析
 * Serializable 是序列化的意思，表示将一个对象转换成可存储或可传输的状态。
 * 序列化后的对象可以在网络上进行传输，也可以存储到本地（可以实现Intent传递对象）
 * */

public class ReadArticleResult implements Serializable {


    /**
     * article_url : https://www.xinli001.com/info/100407276
     * image : https://ossimg.xinli001.com/20180816/d485df4a8b7b865fbcbb91492ede9016.jpeg!120x120
     * title : 和领导沟通前，你必须知道的4个原则
     * describe : 无论沟通的技巧多么纷繁复杂，万变不离其宗，真正有效果的沟通技巧，本质上都必然遵循着沟通的基本原则。
     * author : YouCore
     * push_time :  2018-08-16
     * count : 3987阅读
     * tag : ["工作技能","职场人际"]
     * like : ["6赞"]
     * article_detail : ["\n                    <div class=\"cover-con\">\n   "]
     */

    private String article_url;
    private String image;
    private String title;
    private String describe;
    private String author;
    private String push_time;
    private String count;
    private List<String> tag;
    private List<String> like;
    private List<String> article_detail;

    public String getArticle_url() {
        return article_url;
    }

    public void setArticle_url(String article_url) {
        this.article_url = article_url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPush_time() {
        return push_time;
    }

    public void setPush_time(String push_time) {
        this.push_time = push_time;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    public List<String> getLike() {
        return like;
    }

    public void setLike(List<String> like) {
        this.like = like;
    }

    public List<String> getArticle_detail() {
        return article_detail;
    }

    public void setArticle_detail(List<String> article_detail) {
        this.article_detail = article_detail;
    }
}