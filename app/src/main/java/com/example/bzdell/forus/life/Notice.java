package com.example.bzdell.forus.life;

import com.example.bzdell.forus.Login.UserDate;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by Jack on 2017/8/29.
 */

public class Notice extends BmobObject {

    private String title;//帖子标题

    private String content;// 帖子内容

    public UserDate author;//帖子的发布者

    private BmobFile image;//帖子图片

    private BmobFile file;//帖子的文件

    private BmobRelation see;//看过该帖子的所有用户

    private BmobRelation love ;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserDate getAuthor() {
        return author;
    }

    public void setAuthor(UserDate author) {
        this.author = author;
    }

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    public BmobFile getFile() {
        return file;
    }

    public void setFile(BmobFile file) {
        this.file = file;
    }

    public BmobRelation getSee() {
        return see;
    }

    public void setSee(BmobRelation see) {
        this.see = see;
    }

    public BmobRelation getLove() {
        return love;
    }

    public void setLove(BmobRelation love) {
        this.love = love;
    }

    //自行实现getter和setter方法

}
