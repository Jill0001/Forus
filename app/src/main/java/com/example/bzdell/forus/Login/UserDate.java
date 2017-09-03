package com.example.bzdell.forus.Login;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by Jack on 2017/8/26.
 */

public class UserDate extends BmobUser{
    private String name;
    private String qq;
    private String nickname;
    private String school;
    private String xueyuan;
    private String xuehao;
    private String zhuanye;
    private String phone;
    private String mingzu;
    private String shengri;
    private String uri;
    private String renzheng;
    private BmobRelation love;
    private  BmobFile icon;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getXueyuan() {
        return xueyuan;
    }

    public void setXueyuan(String xueyuan) {
        this.xueyuan = xueyuan;
    }

    public String getXuehao() {
        return xuehao;
    }

    public void setXuehao(String xuehao) {
        this.xuehao = xuehao;
    }

    public String getZhuanye() {
        return zhuanye;
    }

    public void setZhuanye(String zhuanye) {
        this.zhuanye = zhuanye;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getMingzu() {
        return mingzu;
    }

    public void setMingzu(String mingzu) {
        this.mingzu = mingzu;
    }

    public String getShengri() {
        return shengri;
    }

    public void setShengri(String shengri) {
        this.shengri = shengri;
    }

    public  BmobFile getIcon() {
        return icon;
    }

    public void setIcon(BmobFile icon) {
        this.icon = icon;
    }


    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getRenzheng() {
        return renzheng;
    }

    public void setRenzheng(String renzheng) {
        this.renzheng = renzheng;
    }


    public BmobRelation getLove() {
        return love;
    }

    public void setLove(BmobRelation love) {
        this.love = love;
    }
}
