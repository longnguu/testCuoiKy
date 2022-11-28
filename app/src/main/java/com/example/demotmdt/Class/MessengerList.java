package com.example.demotmdt.Class;

public class MessengerList {
    String name,email,lastMessenger,profilePic,chatKey;
    int unseenMessenger,pos;

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public MessengerList(String name, String email, String lastMessenger, String profilePic, String chatKey, int unseenMessenger) {
        this.name = name;
        this.email = email;
        this.lastMessenger = lastMessenger;
        this.profilePic = profilePic;
        this.chatKey = chatKey;
        this.unseenMessenger = unseenMessenger;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastMessenger() {
        return lastMessenger;
    }

    public void setLastMessenger(String lastMessenger) {
        this.lastMessenger = lastMessenger;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getChatKey() {
        return chatKey;
    }

    public void setChatKey(String chatKey) {
        this.chatKey = chatKey;
    }

    public int getUnseenMessenger() {
        return unseenMessenger;
    }

    public void setUnseenMessenger(int unseenMessenger) {
        this.unseenMessenger = unseenMessenger;
    }
}
