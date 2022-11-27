package com.example.demotmdt.Class;

public class ChatList {
    String mobile,name,messenger,date,time;

    public ChatList(String mobile, String name, String messenger, String date, String time) {
        this.mobile = mobile;
        this.name = name;
        this.messenger = messenger;
        this.date = date;
        this.time = time;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessenger() {
        return messenger;
    }

    public void setMessenger(String messenger) {
        this.messenger = messenger;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
