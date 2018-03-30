package com.example.user.oldchat.models;

import java.util.Date;

/**
 * Created by Mina on 25/11/2017.
 */

public class Model {
    public String userNamee = "";
    public String message = "";
    public int type;
    public long messageTime;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    // public String date;


    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public Model(String userNamee, String message, int type) {
        this.userNamee = userNamee;
        this.message = message;
        this.type = type;


        messageTime = new Date().getTime();
    }

    public String getUserNamee() {
        return userNamee;
    }

    public void setUserNamee(String userNamee) {
        this.userNamee = userNamee;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
