package com.example.learnfirebase.Model;

public class Messages {

    String uID,message;
    long timestamp;

    public Messages(String uID, String message, long timestamp) {
        this.uID = uID;
        this.message = message;
        this.timestamp = timestamp;
    }

    public Messages(String message,String uID) {
        this.message = message;
        this.uID=uID;
    }

    public Messages(){

    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
