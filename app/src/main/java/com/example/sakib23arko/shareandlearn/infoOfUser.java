package com.example.sakib23arko.shareandlearn;

public class infoOfUser {

    String title;
    String description;
    String tag;
    String dateTime;

    public infoOfUser() {
        title = description = tag = dateTime = "";
    }

    public infoOfUser(String title, String description, String tag, String dateTime) {

        this.title = title;
        this.description = description;
        this.tag = tag;
        this.dateTime = dateTime;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTag() {
        return tag;
    }

    public String getDateTime() {
        return dateTime;
    }
}
