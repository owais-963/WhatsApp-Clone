package com.example.learnfirebase.Model;

import android.widget.ImageView;

public class Users {

    String profilepic, name,mail,password,userId,lastMessage;

    public Users(String profilepic, String name, String mail, String password, String userId, String lastMessage) {
        this.profilepic = profilepic;
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.userId = userId;
        this.lastMessage = lastMessage;
    }

    public Users(){

    }

    //sign up constructor


    public Users(String name, String mail, String password) {
        this.name = name;
        this.mail = mail;
        this.password = password;

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
