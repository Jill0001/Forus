package com.example.bzdell.forus.me.Settings;

import cn.bmob.v3.BmobObject;

public class Feedback extends BmobObject {
    public String name;
   public String feedback;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }


}
